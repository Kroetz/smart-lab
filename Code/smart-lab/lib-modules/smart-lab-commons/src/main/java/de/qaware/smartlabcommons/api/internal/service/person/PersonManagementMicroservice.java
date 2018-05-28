package de.qaware.smartlabcommons.api.internal.service.person;

import de.qaware.smartlabcommons.api.internal.client.IPersonManagementApiClient;
import de.qaware.smartlabcommons.api.internal.service.generic.AbstractEntityManagementService;
import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcommons.miscellaneous.Property;
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
