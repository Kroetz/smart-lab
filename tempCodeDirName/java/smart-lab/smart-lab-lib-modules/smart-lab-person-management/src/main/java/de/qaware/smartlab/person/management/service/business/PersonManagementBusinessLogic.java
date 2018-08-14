package de.qaware.smartlab.person.management.service.business;

import de.qaware.smartlab.core.data.person.IPerson;
import de.qaware.smartlab.core.data.person.PersonId;
import de.qaware.smartlab.core.service.business.AbstractBasicEntityManagementBusinessLogic;
import de.qaware.smartlab.person.management.service.repository.IPersonManagementRepository;
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
