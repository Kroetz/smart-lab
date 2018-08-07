package de.qaware.smartlabjob.service.business;

import de.qaware.smartlab.core.data.job.IJobInfo;
import de.qaware.smartlab.core.data.job.JobStatus;
import de.qaware.smartlab.core.data.job.JobInfo;
import de.qaware.smartlabjob.service.repository.IJobManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class JobManagementBusinessLogic implements IJobManagementBusinessLogic {

    private final IJobManagementRepository jobManagementRepository;
    private final RestTemplate restTemplate;

    public JobManagementBusinessLogic(
            IJobManagementRepository jobManagementRepository,
            RestTemplateBuilder restTemplateBuilder) {
        this.jobManagementRepository = jobManagementRepository;
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Set<IJobInfo> findAll() {
        Set<IJobInfo> jobInfos = new HashSet<>();
        this.jobManagementRepository.findAll().forEach(jobInfos::add);
        return jobInfos;
    }

    @Override
    public Optional<IJobInfo> findOne(Long jobId) {
        return this.jobManagementRepository.findById(jobId).map(jobInfo -> (IJobInfo) jobInfo);
    }

    @Override
    public synchronized IJobInfo recordNewJob() {
        return recordNewJob(null);
    }

    @Override
    public synchronized IJobInfo recordNewJob(@Nullable URL callbackUrl) {
        return this.jobManagementRepository.save(JobInfo.of(callbackUrl));
    }

    @Override
    public synchronized void markJobAsProcessing(Long jobId) {
        JobInfo newJobInfo = this.jobManagementRepository.findById(jobId)
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
    public synchronized void markJobAsFinished(Long jobId) {
        JobInfo newJobInfo = this.jobManagementRepository.findById(jobId)
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
        JobInfo newJobInfo = this.jobManagementRepository.findById(jobId)
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

    private synchronized JobInfo updateJob(JobInfo jobInfo) {
        if(!this.jobManagementRepository.existsById(jobInfo.getId())) {
            // TODO: Better exception and message
            throw new RuntimeException();
        }
        return this.jobManagementRepository.save(jobInfo);
    }

    private boolean tryExecuteCallback(Long jobId) {
        // TODO: Better exception and message
        IJobInfo jobInfo = findOne(jobId).orElseThrow(RuntimeException::new);
        return jobInfo.getCallbackUrl()
                .map(callbackUrl -> {
                    HttpEntity<IJobInfo> request = new HttpEntity<>(jobInfo);
                    restTemplate.postForEntity(callbackUrl.toString(), request , Void.class);
                    return true;
                })
                .orElse(false);
    }
}
