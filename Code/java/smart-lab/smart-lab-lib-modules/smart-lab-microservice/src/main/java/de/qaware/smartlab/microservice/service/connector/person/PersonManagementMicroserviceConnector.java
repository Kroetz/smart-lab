package de.qaware.smartlab.microservice.service.connector.person;

import de.qaware.smartlab.api.service.client.person.IPersonManagementApiClient;
import de.qaware.smartlab.api.service.connector.person.IPersonManagementService;
import de.qaware.smartlab.core.configuration.ModularityConfiguration;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.data.person.IPerson;
import de.qaware.smartlab.core.data.person.PersonDto;
import de.qaware.smartlab.core.data.person.PersonId;
import de.qaware.smartlab.core.exception.SmartLabException;
import de.qaware.smartlab.core.constant.Property;
import de.qaware.smartlab.core.service.url.IServiceBaseUrlGetter;
import de.qaware.smartlab.microservice.service.connector.generic.AbstractBasicEntityManagementMicroserviceConnector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@ConditionalOnProperty(
        prefix = ModularityConfiguration.Properties.PREFIX,
        name = ModularityConfiguration.Properties.MODULARITY,
        havingValue = ModularityConfiguration.Properties.MICROSERVICE)
public class PersonManagementMicroserviceConnector extends AbstractBasicEntityManagementMicroserviceConnector<IPerson, PersonId, PersonDto> implements IPersonManagementService {

    private final IPersonManagementApiClient personManagementApiClient;

    public PersonManagementMicroserviceConnector(
            IPersonManagementApiClient personManagementApiClient,
            IDtoConverter<IPerson, PersonDto> personConverter) {
        super(personManagementApiClient, personConverter);
        this.personManagementApiClient = personManagementApiClient;
    }

    @Component
    @Qualifier(IServiceBaseUrlGetter.QUALIFIER_PERSON_MANAGEMENT_SERVICE_BASE_URL_GETTER)
    @ConditionalOnProperty(
            prefix = ModularityConfiguration.Properties.PREFIX,
            name = ModularityConfiguration.Properties.MODULARITY,
            havingValue = ModularityConfiguration.Properties.MICROSERVICE)
    @Slf4j
    public static class BaseUrlGetter implements IServiceBaseUrlGetter {

        private final IPersonManagementApiClient personManagementApiClient;

        public BaseUrlGetter(IPersonManagementApiClient personManagementApiClient) {
            this.personManagementApiClient = personManagementApiClient;
        }

        @Override
        public URL getBaseUrl() {
            try {
                return this.personManagementApiClient.getBaseUrl().getBody();
            }
            // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
            catch (Exception e) {
                throw new SmartLabException(e);
            }
        }
    }
}
