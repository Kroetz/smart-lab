package de.qaware.smartlabcore.person.business;

import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcore.generic.business.AbstractEntityManagementBusinessLogic;
import de.qaware.smartlabcore.person.repository.IPersonManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PersonManagementBusinessLogic extends AbstractEntityManagementBusinessLogic<IPerson> implements IPersonManagementBusinessLogic {

    private final IPersonManagementRepository personManagementRepository;

    public PersonManagementBusinessLogic(IPersonManagementRepository personManagementRepository) {
        super(personManagementRepository);
        this.personManagementRepository = personManagementRepository;
    }
}
