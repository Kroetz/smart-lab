package de.qaware.smartlabmicroservice.service.connector.person;

import de.qaware.smartlabapi.service.client.person.IPersonManagementApiClient;
import de.qaware.smartlabapi.service.connector.person.IPersonManagementService;
import de.qaware.smartlabcore.data.generic.IDtoConverter;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.PersonDto;
import de.qaware.smartlabcore.data.person.PersonId;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabcore.service.url.IServiceBaseUrlGetter;
import de.qaware.smartlabmicroservice.service.connector.generic.AbstractBasicEntityManagementMicroserviceConnector;
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
