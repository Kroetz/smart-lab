package de.qaware.smartlabmonolith.service.connector.person;

import de.qaware.smartlabapi.service.connector.person.IPersonManagementService;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.PersonId;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabcore.service.url.AbstractMonolithicBaseUrlGetter;
import de.qaware.smartlabmonolith.service.connector.generic.AbstractBasicEntityManagementMonolithicServiceConnector;
import de.qaware.smartlabperson.service.controller.PersonManagementController;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class PersonManagementMonolithicServiceConnector extends AbstractBasicEntityManagementMonolithicServiceConnector<IPerson, PersonId> implements IPersonManagementService {

    private final PersonManagementController personManagementController;

    public PersonManagementMonolithicServiceConnector(PersonManagementController personManagementController) {
        super(personManagementController);
        this.personManagementController = personManagementController;
    }

    @Component
    // TODO: String literal
    @Qualifier("personManagementServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MONOLITH)
    public static class BaseUrlGetter extends AbstractMonolithicBaseUrlGetter {

        public BaseUrlGetter(PersonManagementController.BaseUrlController baseUrlController) {
            super(baseUrlController);
        }
    }
}
