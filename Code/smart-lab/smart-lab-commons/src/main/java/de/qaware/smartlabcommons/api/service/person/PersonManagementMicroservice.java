package de.qaware.smartlabcommons.api.service.person;

import de.qaware.smartlabcommons.api.client.IPersonManagementApiClient;
import de.qaware.smartlabcommons.api.service.generic.AbstractEntityManagementService;
import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcommons.miscellaneous.Constants;
import de.qaware.smartlabcommons.miscellaneous.ProfileNames;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(ProfileNames.MICROSERVICE)
public class PersonManagementMicroservice extends AbstractEntityManagementService<IPerson> implements IPersonManagementService {

    private final IPersonManagementApiClient personManagementApiClient;

    public PersonManagementMicroservice(IPersonManagementApiClient personManagementApiClient) {
        super(personManagementApiClient);
        this.personManagementApiClient = personManagementApiClient;
    }
}
