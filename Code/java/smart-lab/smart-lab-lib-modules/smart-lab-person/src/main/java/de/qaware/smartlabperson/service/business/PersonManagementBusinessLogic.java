package de.qaware.smartlabperson.service.business;

import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.PersonId;
import de.qaware.smartlabcore.service.business.AbstractBasicEntityManagementBusinessLogic;
import de.qaware.smartlabperson.service.repository.IPersonManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PersonManagementBusinessLogic extends AbstractBasicEntityManagementBusinessLogic<IPerson, PersonId> implements IPersonManagementBusinessLogic {

    private final IPersonManagementRepository personManagementRepository;

    public PersonManagementBusinessLogic(IPersonManagementRepository personManagementRepository) {
        super(personManagementRepository);
        this.personManagementRepository = personManagementRepository;
    }
}
