package de.qaware.smartlabapi.service.person;

import de.qaware.smartlabapi.client.IPersonManagementApiClient;
import de.qaware.smartlabapi.service.generic.AbstractEntityManagementService;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.miscellaneous.Property;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
public class PersonManagementMicroservice extends AbstractEntityManagementService<IPerson> implements IPersonManagementService {

    private final IPersonManagementApiClient personManagementApiClient;

    public PersonManagementMicroservice(IPersonManagementApiClient personManagementApiClient) {
        super(personManagementApiClient);
        this.personManagementApiClient = personManagementApiClient;
    }
}
