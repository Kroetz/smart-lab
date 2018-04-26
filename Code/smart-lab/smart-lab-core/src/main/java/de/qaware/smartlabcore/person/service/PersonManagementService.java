package de.qaware.smartlabcore.person.service;

import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcore.generic.service.AbstractEntityManagementService;
import de.qaware.smartlabcore.person.repository.IPersonManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PersonManagementService extends AbstractEntityManagementService<IPerson> implements IPersonManagementService {

    private final IPersonManagementRepository personManagementRepository;

    public PersonManagementService(IPersonManagementRepository personManagementRepository) {
        super(personManagementRepository);
        this.personManagementRepository = personManagementRepository;
    }
}
