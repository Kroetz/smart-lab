package de.qaware.smartlab.monolith.service.connector.person;

import de.qaware.smartlab.api.service.connector.person.IPersonManagementService;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.data.person.IPerson;
import de.qaware.smartlab.core.data.person.PersonId;
import de.qaware.smartlab.core.data.person.PersonDto;
import de.qaware.smartlab.core.miscellaneous.Property;
import de.qaware.smartlab.core.service.url.IServiceBaseUrlGetter;
import de.qaware.smartlab.monolith.service.url.AbstractMonolithicBaseUrlGetter;
import de.qaware.smartlab.monolith.service.connector.generic.AbstractBasicEntityManagementMonolithicServiceConnector;
import de.qaware.smartlab.person.management.service.controller.PersonManagementController;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class PersonManagementMonolithicServiceConnector extends AbstractBasicEntityManagementMonolithicServiceConnector<IPerson, PersonId, PersonDto> implements IPersonManagementService {

    private final PersonManagementController personManagementController;

    public PersonManagementMonolithicServiceConnector(
            PersonManagementController personManagementController,
            IDtoConverter<IPerson, PersonDto> personConverter) {
        super(personManagementController, personConverter);
        this.personManagementController = personManagementController;
    }

    @Component
    @Qualifier(IServiceBaseUrlGetter.QUALIFIER_PERSON_MANAGEMENT_SERVICE_BASE_URL_GETTER)
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
