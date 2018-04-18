package de.qaware.smartlabcore.person.repository;

import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcore.data.sample.ISampleDataFactory;
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

    public PersonManagementRepositoryMock(
            ISampleDataFactory coastGuardDataFactory,
            ISampleDataFactory forestRangersDataFactory,
            ISampleDataFactory fireFightersDataFactory) {
        this.persons = new ArrayList<>();
        this.persons.addAll(new ArrayList<>(coastGuardDataFactory.createWorkgroupMembers().values()));
        this.persons.addAll(new ArrayList<>(forestRangersDataFactory.createWorkgroupMembers().values()));
        this.persons.addAll(new ArrayList<>(fireFightersDataFactory.createWorkgroupMembers().values()));
        sortPersonsById();
    }

    private boolean exists(long personId) {
        return persons.stream()
                .anyMatch(person -> person.getId() == personId);
    }

    @Override
    public List<IPerson> getPersons() {
        return this.persons;
    }

    @Override
    public Optional<IPerson> getPerson(long personId) {
        return persons.stream()
                .filter(person -> person.getId() == personId)
                .findFirst();
    }

    @Override
    public boolean createPerson(IPerson person) {
        return !exists(person.getId()) && persons.add(person);
    }

    @Override
    public boolean deletePerson(long personId) {
        return persons.removeAll(persons.stream()
                .filter(person -> person.getId() == personId)
                .collect(Collectors.toList()));
    }

    private void sortPersonsById() {
        persons.sort(Comparator.comparingLong(IPerson::getId));
    }
}
