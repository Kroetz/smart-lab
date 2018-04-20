package de.qaware.smartlabcore.person.repository;

import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcore.data.sample.provider.ISampleDataProvider;
import lombok.extern.slf4j.Slf4j;
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
    public boolean createPerson(IPerson person) {
        return !exists(person.getId()) && persons.add(person);
    }

    @Override
    public boolean deletePerson(String personId) {
        return persons.removeAll(persons.stream()
                .filter(person -> person.getId().equals(personId))
                .collect(Collectors.toList()));
    }

    private void sortPersonsById() {
        persons.sort(Comparator.comparing(IPerson::getId));
    }
}
