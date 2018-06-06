package de.qaware.smartlabtrigger.business;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Optional;

@Component
@Slf4j
public class JobInfoBookKeeper implements IJobInfoBookKeeper {

    private final IJobInfoRepository jobInfoRepository;
    private final RestTemplate restTemplate;

    public JobInfoBookKeeper(IJobInfoRepository jobInfoRepository, RestTemplateBuilder restTemplateBuilder) {
        this.jobInfoRepository = jobInfoRepository;
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public synchronized JobInfo recordNewJob(JobInfo jobInfo) {
        return this.jobInfoRepository.save(jobInfo);
    }

    @Override
    public synchronized void startJobProcessing(Long jobId) {
        JobInfo newJobInfo = this.jobInfoRepository.findById(jobId)
                .map(jobInfo -> {
                    if(jobInfo.getStatus() != JobStatus.PENDING) throw new RuntimeException();   // TODO: Better exception and message
                    return jobInfo
                            .withStatus(JobStatus.PROCESSING)
                            .withProcessingStart(Instant.now());
                })
                // TODO: Better exception and message
                .orElseThrow(RuntimeException::new);
        updateJob(newJobInfo);
    }

    @Override
    public synchronized void finishJobProcessing(Long jobId) {
        JobInfo newJobInfo = this.jobInfoRepository.findById(jobId)
                .map(jobInfo -> {
                    if(jobInfo.getStatus() != JobStatus.PROCESSING) throw new RuntimeException();   // TODO: Better exception and message
                    return jobInfo
                            .withStatus(JobStatus.FINISHED)
                            .withProcessingEnd(Instant.now());
                })
                // TODO: Better exception and message
                .orElseThrow(RuntimeException::new);
        updateJob(newJobInfo);
        tryExecuteCallback(jobId);
    }

    @Override
    public synchronized void markJobAsFailed(Long jobId, String errorMessage) {
        JobInfo newJobInfo = this.jobInfoRepository.findById(jobId)
                .map(jobInfo -> {
                    if(jobInfo.getStatus() != JobStatus.PROCESSING) throw new RuntimeException();   // TODO: Better exception and message
                    return jobInfo
                            .withStatus(JobStatus.FAILED)
                            .withProcessingEnd(Instant.now())
                            .withErrorMessage(errorMessage);
                })
                // TODO: Better exception and message
                .orElseThrow(RuntimeException::new);
        updateJob(newJobInfo);
        tryExecuteCallback(jobId);
    }

    @Override
    public Optional<JobInfo> getJobInfo(Long jobId) {
        return this.jobInfoRepository.findById(jobId);
    }

    private JobInfo updateJob(JobInfo jobInfo) {
        if(!this.jobInfoRepository.existsById(jobInfo.getId())) {
            // TODO: Better exception and message
            throw new RuntimeException();
        }
        return this.jobInfoRepository.save(jobInfo);
    }

    private boolean tryExecuteCallback(Long jobId) {
        // TODO: Better exception and message
        JobInfo jobInfo = getJobInfo(jobId).orElseThrow(RuntimeException::new);
        return jobInfo.getCallbackUrl().map(callbackUrl -> {
            HttpEntity<JobInfo> request = new HttpEntity<>(jobInfo);
            restTemplate.postForEntity(callbackUrl.toString(), request , Void.class);
            return true;
        })
        .orElse(false);
    }
}
