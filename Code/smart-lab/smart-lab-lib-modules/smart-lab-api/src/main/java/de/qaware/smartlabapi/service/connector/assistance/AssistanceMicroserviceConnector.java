package de.qaware.smartlabapi.service.connector.assistance;

import de.qaware.smartlabapi.service.client.assistance.IAssistanceApiClient;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabcore.service.url.IServiceBaseUrlGetter;
import feign.FeignException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
public class AssistanceMicroserviceConnector implements IAssistanceService {

    private final IAssistanceApiClient assistanceApiClient;

    public AssistanceMicroserviceConnector(IAssistanceApiClient assistanceApiClient) {
        this.assistanceApiClient = assistanceApiClient;
    }

    @Override
    public void beginAssistance(String assistanceId, IAssistanceContext context) {
        try {
            this.assistanceApiClient.beginAssistance(assistanceId, context);
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public void endAssistance(String assistanceId, IAssistanceContext context) {
        try {
            this.assistanceApiClient.endAssistance(assistanceId, context);
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public void updateAssistance(String assistanceId, IAssistanceContext context) {
        try {
            this.assistanceApiClient.updateAssistance(assistanceId, context);
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Component
    // TODO: String literal
    @Qualifier("assistanceServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MICROSERVICE)
    @Slf4j
    public static class BaseUrlGetter implements IServiceBaseUrlGetter {

        private final IAssistanceApiClient assistanceApiClient;

        public BaseUrlGetter(IAssistanceApiClient assistanceApiClient) {
            this.assistanceApiClient = assistanceApiClient;
        }

        @Override
        public URL getBaseUrl() {
            // TODO: Exceptions
            try {
                return this.assistanceApiClient.getBaseUrl().getBody();
            }
            catch(RetryableException e) {
                throw e;
            }
            catch(FeignException e) {
                throw new UnknownErrorException();
            }
        }
    }
}
