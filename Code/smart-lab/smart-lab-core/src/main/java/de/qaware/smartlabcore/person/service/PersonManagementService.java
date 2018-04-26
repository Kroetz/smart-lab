package de.qaware.smartlabcore.person.service;

import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcore.generic.result.CreationResult;
import de.qaware.smartlabcore.generic.result.DeletionResult;
import de.qaware.smartlabcore.person.repository.IPersonManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class PersonManagementService implements IPersonManagementService {

    private final IPersonManagementRepository personManagementRepository;

    public PersonManagementService(IPersonManagementRepository personManagementRepository) {
        this.personManagementRepository = personManagementRepository;
    }

    @Override
    public Set<IPerson> getPersons() {
        return personManagementRepository.findAll();
    }

    @Override
    public Optional<IPerson> getPerson(String personId) {
        return personManagementRepository.findOne(personId);
    }

    @Override
    public CreationResult createPerson(IPerson person) {
        return personManagementRepository.create(person);
    }

    @Override
    public DeletionResult deletePerson(String personId) {
        return personManagementRepository.delete(personId);
    }
}
