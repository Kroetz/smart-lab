package de.qaware.smartlab.microservice.service.connector.person;

import de.qaware.smartlab.api.service.client.person.IPersonManagementApiClient;
import de.qaware.smartlab.api.service.connector.person.IPersonManagementService;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.data.person.IPerson;
import de.qaware.smartlab.core.data.person.PersonDto;
import de.qaware.smartlab.core.data.person.PersonId;
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
public class PersonManagementMicroserviceConnector extends AbstractBasicEntityManagementMicroserviceConnector<IPerson, PersonId, PersonDto> implements IPersonManagementService {

    private final IPersonManagementApiClient personManagementApiClient;

    public PersonManagementMicroserviceConnector(
            IPersonManagementApiClient personManagementApiClient,
            IDtoConverter<IPerson, PersonDto> personConverter) {
        super(personManagementApiClient, personConverter);
        this.personManagementApiClient = personManagementApiClient;
    }

    @Component
    // TODO: String literal
    @Qualifier("personManagementServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MICROSERVICE)
    @Slf4j
    public static class BaseUrlGetter implements IServiceBaseUrlGetter {

        private final IPersonManagementApiClient personManagementApiClient;

        public BaseUrlGetter(IPersonManagementApiClient personManagementApiClient) {
            this.personManagementApiClient = personManagementApiClient;
        }

        @Override
        public URL getBaseUrl() {
            // TODO: Exceptions
            try {
                return this.personManagementApiClient.getBaseUrl().getBody();
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
