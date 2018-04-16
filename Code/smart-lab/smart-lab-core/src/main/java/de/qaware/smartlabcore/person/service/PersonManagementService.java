package de.qaware.smartlabcore.person.service;

import de.qaware.smartlabcommons.data.person.Person;
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
    public List<Person> getPersons() {
        return personManagementRepository.getPersons();
    }

    @Override
    public Optional<Person> getPerson(long personId) {
        return personManagementRepository.getPerson(personId);
    }

    @Override
    public boolean createPerson(Person person) {
        return personManagementRepository.createPerson(person);
    }

    @Override
    public void deletePerson(long personId) {
        personManagementRepository.deletePerson(personId);
    }
}
