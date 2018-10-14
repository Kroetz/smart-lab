package de.qaware.smartlab.job.service.business;

import de.qaware.smartlab.core.data.job.IJobInfo;
import de.qaware.smartlab.core.data.job.JobStatus;
import de.qaware.smartlab.job.data.JobInfo;
import de.qaware.smartlab.core.exception.data.DataException;
import de.qaware.smartlab.core.exception.data.NotFoundException;
import de.qaware.smartlab.job.service.repository.IJobManagementRepository;
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

import static java.lang.String.format;

@Service
@Slf4j
public class JobManagementBusinessLogic implements IJobManagementBusinessLogic {

    // TODO: Currently there is no mechanism that cleans old jobs. So this service will eventually run out of storage space.
    
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
                    requirePendingStatus(jobInfo);
                    return jobInfo
                            .withStatus(JobStatus.PROCESSING)
                            .withProcessingStart(Instant.now());
                })
                .orElseThrow(() -> onJobNotFound(jobId));
        updateJob(newJobInfo);
    }

    @Override
    public synchronized void markJobAsFinished(Long jobId) {
        JobInfo newJobInfo = this.jobManagementRepository.findById(jobId)
                .map(jobInfo -> {
                    requireProcessingStatus(jobInfo);
                    return jobInfo
                            .withStatus(JobStatus.FINISHED)
                            .withProcessingEnd(Instant.now());
                })
                .orElseThrow(() -> onJobNotFound(jobId));
        updateJob(newJobInfo);
        tryExecuteCallback(jobId);
    }

    @Override
    public synchronized void markJobAsFailed(Long jobId, String jobErrorMessage) {
        JobInfo newJobInfo = this.jobManagementRepository.findById(jobId)
                .map(jobInfo -> {
                    requireProcessingStatus(jobInfo);
                    return jobInfo
                            .withStatus(JobStatus.FAILED)
                            .withProcessingEnd(Instant.now())
                            .withErrorMessage(jobErrorMessage);
                })
                .orElseThrow(() -> onJobNotFound(jobId));
        updateJob(newJobInfo);
        tryExecuteCallback(jobId);
    }

    private NotFoundException onJobNotFound(Long jobId) {
        String errorMessage = format("Could not find job %d", jobId);
        log.error(errorMessage);
        return new NotFoundException(errorMessage);
    }

    private void requirePendingStatus(IJobInfo jobInfo) throws DataException {
        requireJobStatus(jobInfo, JobStatus.PENDING);
    }

    private void requireProcessingStatus(IJobInfo jobInfo) throws DataException {
        requireJobStatus(jobInfo, JobStatus.PROCESSING);
    }

    private void requireJobStatus(IJobInfo jobInfo, JobStatus jobStatus) throws DataException {
        if(jobInfo.getStatus() != jobStatus) {
            String errorMessage = format("The status of the job %s must be %s", jobInfo, jobStatus);
            log.error(errorMessage);
            throw new DataException(errorMessage);
        }
    }

    private synchronized JobInfo updateJob(JobInfo jobInfo) {
        requireExists(jobInfo.getId());
        return this.jobManagementRepository.save(jobInfo);
    }

    private void requireExists(Long jobId) {
        if(!this.jobManagementRepository.existsById(jobId)) {
            throw onJobNotFound(jobId);
        }
    }

    private boolean tryExecuteCallback(Long jobId) {
        IJobInfo jobInfo = findOne(jobId).orElseThrow(() -> onJobNotFound(jobId));
        return jobInfo.getCallbackUrl()
                .map(callbackUrl -> {
                    HttpEntity<IJobInfo> request = new HttpEntity<>(jobInfo);
                    restTemplate.postForEntity(callbackUrl.toString(), request , Void.class);
                    return true;
                })
                .orElse(false);
    }
}
