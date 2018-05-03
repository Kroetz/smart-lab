package de.qaware.smartlabcommons.api.service.person;

import de.qaware.smartlabcommons.api.client.IPersonManagementApiClient;
import de.qaware.smartlabcommons.api.service.generic.AbstractEntityManagementService;
import de.qaware.smartlabcommons.data.person.IPerson;
import org.springframework.stereotype.Component;

@Component
public class PersonManagementService extends AbstractEntityManagementService<IPerson> implements IPersonManagementService {

    private final IPersonManagementApiClient personManagementApiClient;

    public PersonManagementService(IPersonManagementApiClient personManagementApiClient) {
        super(personManagementApiClient);
        this.personManagementApiClient = personManagementApiClient;
    }
}
