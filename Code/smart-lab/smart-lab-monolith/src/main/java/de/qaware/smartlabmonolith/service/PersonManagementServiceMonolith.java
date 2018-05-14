package de.qaware.smartlabmonolith.service;

import de.qaware.smartlabcommons.api.service.person.IPersonManagementService;
import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcommons.miscellaneous.Constants;
import de.qaware.smartlabperson.controller.PersonManagementController;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(Constants.PROFILE_NAME_MONOLITH)
public class PersonManagementServiceMonolith extends AbstractEntityManagementServiceMonolith<IPerson> implements IPersonManagementService {

    private final PersonManagementController personManagementController;

    public PersonManagementServiceMonolith(PersonManagementController personManagementController) {
        super(personManagementController);
        this.personManagementController = personManagementController;
    }
}
