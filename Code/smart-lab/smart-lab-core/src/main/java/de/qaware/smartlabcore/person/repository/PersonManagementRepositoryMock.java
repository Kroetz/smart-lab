package de.qaware.smartlabcore.person.repository;

import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcore.data.sample.provider.ISampleDataProvider;
import de.qaware.smartlabcore.generic.result.CreationResult;
import de.qaware.smartlabcore.generic.result.DeletionResult;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class PersonManagementRepositoryMock implements IPersonManagementRepository {

    private List<IPerson> persons;

    public PersonManagementRepositoryMock(ISampleDataProvider sampleDataProvider) {
        this.persons = new ArrayList<>(sampleDataProvider.getWorkgroupMembers());
        sortPersonsById();
    }

    private boolean exists(String personId) {
        return persons.stream()
                .anyMatch(person -> person.getId().equals(personId));
    }

    @Override
    public List<IPerson> getPersons() {
        return this.persons;
    }

    @Override
    public Optional<IPerson> getPerson(String personId) {
        return persons.stream()
                .filter(person -> person.getId().equals(personId))
                .findFirst();
    }

    @Override
    public CreationResult createPerson(IPerson person) {
        if (exists(person.getId())) {
            return CreationResult.CONFLICT;
        }
        if(persons.add(person)) {
            return CreationResult.SUCCESS;
        }
        return CreationResult.ERROR;
    }

    @Override
    public DeletionResult deletePerson(String personId) {
        val deleted = persons.removeAll(persons.stream()
                .filter(person -> person.getId().equals(personId))
                .collect(Collectors.toList()));
        if(deleted) {
            return DeletionResult.SUCCESS;
        }
        return DeletionResult.ERROR;
    }

    private void sortPersonsById() {
        persons.sort(Comparator.comparing(IPerson::getId));
    }
}
