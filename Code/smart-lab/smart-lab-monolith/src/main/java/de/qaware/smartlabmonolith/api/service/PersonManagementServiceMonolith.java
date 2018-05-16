package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabcommons.api.service.person.IPersonManagementService;
import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcommons.miscellaneous.Constants;
import de.qaware.smartlabcommons.miscellaneous.ProfileNames;
import de.qaware.smartlabperson.controller.PersonManagementController;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(ProfileNames.MONOLITH)
public class PersonManagementServiceMonolith extends AbstractEntityManagementServiceMonolith<IPerson> implements IPersonManagementService {

    private final PersonManagementController personManagementController;

    public PersonManagementServiceMonolith(PersonManagementController personManagementController) {
        super(personManagementController);
        this.personManagementController = personManagementController;
    }
}
