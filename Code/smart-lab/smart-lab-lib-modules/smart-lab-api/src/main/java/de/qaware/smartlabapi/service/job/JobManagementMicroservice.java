package de.qaware.smartlabapi.service.job;

import de.qaware.smartlabapi.client.IJobManagementApiClient;
import de.qaware.smartlabcore.data.job.IJobInfo;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import de.qaware.smartlabcore.miscellaneous.Property;
import feign.FeignException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.nonNull;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
public class JobManagementMicroservice implements IJobManagementService {

    private final IJobManagementApiClient jobManagementApiClient;

    public JobManagementMicroservice(IJobManagementApiClient jobManagementApiClient) {
        this.jobManagementApiClient = jobManagementApiClient;
    }

    @Override
    public Set<IJobInfo> findAll() {
        try {
            return this.jobManagementApiClient.findAll();
        }
        catch(FeignException e) {
            throw new UnknownErrorException(e);
        }
    }

    @Override
    public IJobInfo findOne(Long jobId) {
        try {
            return this.jobManagementApiClient.findOne(jobId).getBody();
        }
        catch(FeignException e) {
            throw new UnknownErrorException(e);
        }
    }

    @Override
    public IJobInfo recordNewJob(@Nullable URL callbackUrl) {
        try {
            return this.jobManagementApiClient.recordNewJob(
                    nonNull(callbackUrl) ? callbackUrl.toString() : null).getBody();
        }
        catch(FeignException e) {
            throw new UnknownErrorException(e);
        }
    }

    @Override
    public void markJobAsProcessing(Long jobId) {
        try {
            this.jobManagementApiClient.markJobAsProcessing(jobId);
        }
        catch(FeignException e) {
            throw new UnknownErrorException(e);
        }
    }

    @Override
    public void markJobAsFinished(Long jobId) {
        try {
            this.jobManagementApiClient.markJobAsFinished(jobId);
        }
        catch(FeignException e) {
            throw new UnknownErrorException(e);
        }
    }

    @Override
    public void markJobAsFailed(Long jobId, String errorMessage) {
        try {
            this.jobManagementApiClient.markJobAsFailed(jobId, errorMessage);
        }
        catch(FeignException e) {
            throw new UnknownErrorException(e);
        }
    }
}
