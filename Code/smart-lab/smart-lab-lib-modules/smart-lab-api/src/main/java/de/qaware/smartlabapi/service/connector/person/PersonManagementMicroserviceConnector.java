package de.qaware.smartlabapi.service.connector.person;

import de.qaware.smartlabapi.service.client.person.IPersonManagementApiClient;
import de.qaware.smartlabapi.service.connector.generic.AbstractBasicEntityManagementMicroserviceConnector;
import de.qaware.smartlabapi.service.url.AbstractMicroserviceBaseUrlGetter;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.PersonId;
import de.qaware.smartlabcore.miscellaneous.Property;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
public class PersonManagementMicroserviceConnector extends AbstractBasicEntityManagementMicroserviceConnector<IPerson, PersonId> implements IPersonManagementService {

    private final IPersonManagementApiClient personManagementApiClient;

    public PersonManagementMicroserviceConnector(IPersonManagementApiClient personManagementApiClient) {
        super(personManagementApiClient);
        this.personManagementApiClient = personManagementApiClient;
    }

    @Component
    // TODO: String literal
    @Qualifier("personManagementServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MICROSERVICE)
    public static class BaseUrlGetter extends AbstractMicroserviceBaseUrlGetter {

        public BaseUrlGetter(IPersonManagementApiClient personManagementApiClient) {
            super(personManagementApiClient);
        }
    }
}
