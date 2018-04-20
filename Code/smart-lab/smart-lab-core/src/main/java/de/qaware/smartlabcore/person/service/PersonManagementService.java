package de.qaware.smartlabcore.person.service;

import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcore.person.repository.IPersonManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PersonManagementService implements IPersonManagementService {

    private final IPersonManagementRepository personManagementRepository;

    public PersonManagementService(IPersonManagementRepository personManagementRepository) {
        this.personManagementRepository = personManagementRepository;
    }

    @Override
    public List<IPerson> getPersons() {
        return personManagementRepository.getPersons();
    }

    @Override
    public Optional<IPerson> getPerson(String personId) {
        return personManagementRepository.getPerson(personId);
    }

    @Override
    public boolean createPerson(IPerson person) {
        return personManagementRepository.createPerson(person);
    }

    @Override
    public boolean deletePerson(String personId) {
        return personManagementRepository.deletePerson(personId);
    }
}