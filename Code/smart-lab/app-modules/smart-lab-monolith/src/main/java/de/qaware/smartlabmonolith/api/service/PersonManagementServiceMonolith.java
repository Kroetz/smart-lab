package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabcommons.api.internal.service.person.IPersonManagementService;
import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcommons.miscellaneous.Property;
import de.qaware.smartlabperson.controller.PersonManagementController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class PersonManagementServiceMonolith extends AbstractEntityManagementServiceMonolith<IPerson> implements IPersonManagementService {

    private final PersonManagementController personManagementController;

    public PersonManagementServiceMonolith(PersonManagementController personManagementController) {
        super(personManagementController);
        this.personManagementController = personManagementController;
    }
}
