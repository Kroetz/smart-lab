package de.qaware.smartlab.microservice.service.connector.job;

import de.qaware.smartlab.api.service.client.job.IJobManagementApiClient;
import de.qaware.smartlab.api.service.connector.job.IJobManagementService;
import de.qaware.smartlab.core.data.job.IJobInfo;
import de.qaware.smartlab.core.exception.SmartLabException;
import de.qaware.smartlab.core.miscellaneous.Property;
import de.qaware.smartlab.core.service.url.IServiceBaseUrlGetter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Set;

import static java.util.Objects.nonNull;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
public class JobManagementMicroserviceConnector implements IJobManagementService {

    private final IJobManagementApiClient jobManagementApiClient;

    public JobManagementMicroserviceConnector(IJobManagementApiClient jobManagementApiClient) {
        this.jobManagementApiClient = jobManagementApiClient;
    }

    @Override
    public Set<IJobInfo> findAll() {
        try {
            return this.jobManagementApiClient.findAll();
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public IJobInfo findOne(Long jobId) {
        try {
            return this.jobManagementApiClient.findOne(jobId).getBody();
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public IJobInfo recordNewJob(@Nullable URL callbackUrl) {
        try {
            return this.jobManagementApiClient.recordNewJob(
                    nonNull(callbackUrl) ? callbackUrl.toString() : null).getBody();
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public void markJobAsProcessing(Long jobId) {
        try {
            this.jobManagementApiClient.markJobAsProcessing(jobId);
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public void markJobAsFinished(Long jobId) {
        try {
            this.jobManagementApiClient.markJobAsFinished(jobId);
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public void markJobAsFailed(Long jobId, String errorMessage) {
        try {
            this.jobManagementApiClient.markJobAsFailed(jobId, errorMessage);
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Component
    // TODO: String literal
    @Qualifier("jobManagementServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MICROSERVICE)
    @Slf4j
    public static class BaseUrlGetter implements IServiceBaseUrlGetter {

        private final IJobManagementApiClient jobManagementApiClient;

        public BaseUrlGetter(IJobManagementApiClient jobManagementApiClient) {
            this.jobManagementApiClient = jobManagementApiClient;
        }

        @Override
        public URL getBaseUrl() {
            try {
                return this.jobManagementApiClient.getBaseUrl().getBody();
            }
            // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
            catch (Exception e) {
                throw new SmartLabException(e);
            }
        }
    }
}
