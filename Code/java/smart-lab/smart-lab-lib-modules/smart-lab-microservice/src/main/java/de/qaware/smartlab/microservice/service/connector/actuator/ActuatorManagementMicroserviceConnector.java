package de.qaware.smartlab.microservice.service.connector.actuator;

import de.qaware.smartlab.api.service.client.actuator.IActuatorManagementApiClient;
import de.qaware.smartlab.api.service.connector.actuator.IActuatorManagementService;
import de.qaware.smartlab.core.data.actuator.ActuatorId;
import de.qaware.smartlab.core.data.actuator.ActuatorDto;
import de.qaware.smartlab.core.data.actuator.IActuator;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.exception.UnknownErrorException;
import de.qaware.smartlab.core.miscellaneous.Property;
import de.qaware.smartlab.core.service.url.IServiceBaseUrlGetter;
import de.qaware.smartlab.microservice.service.connector.generic.AbstractBasicEntityManagementMicroserviceConnector;
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
public class ActuatorManagementMicroserviceConnector extends AbstractBasicEntityManagementMicroserviceConnector<IActuator, ActuatorId, ActuatorDto> implements IActuatorManagementService {

    private final IActuatorManagementApiClient actuatorManagementApiClient;

    public ActuatorManagementMicroserviceConnector(
            IActuatorManagementApiClient actuatorManagementApiClient,
            IDtoConverter<IActuator, ActuatorDto> actuatorConverter) {
        super(actuatorManagementApiClient, actuatorConverter);
        this.actuatorManagementApiClient = actuatorManagementApiClient;
    }

    @Component
    // TODO: String literal
    @Qualifier("actuatorManagementServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MICROSERVICE)
    @Slf4j
    public static class BaseUrlGetter implements IServiceBaseUrlGetter {

        private final IActuatorManagementApiClient actuatorManagementApiClient;

        public BaseUrlGetter(IActuatorManagementApiClient actuatorManagementApiClient) {
            this.actuatorManagementApiClient = actuatorManagementApiClient;
        }

        @Override
        public URL getBaseUrl() {
            // TODO: Exceptions
            try {
                return this.actuatorManagementApiClient.getBaseUrl().getBody();
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
