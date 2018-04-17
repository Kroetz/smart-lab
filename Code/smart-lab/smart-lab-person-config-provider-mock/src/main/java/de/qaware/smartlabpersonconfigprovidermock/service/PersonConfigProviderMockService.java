package de.qaware.smartlabpersonconfigprovidermock.service;

import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcore.data.sample.ISampleDataFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonConfigProviderMockService implements IPersonConfigProviderMockService {

    private List<IPerson> persons;

    public PersonConfigProviderMockService(
            ISampleDataFactory coastGuardDataFactory,
            ISampleDataFactory forestRangersDataFactory,
            ISampleDataFactory fireFightersDataFactory) {
        this.persons = new ArrayList<>();
        this.persons.addAll(coastGuardDataFactory.createWorkgroupMembers());
        this.persons.addAll(forestRangersDataFactory.createWorkgroupMembers());
        this.persons.addAll(fireFightersDataFactory.createWorkgroupMembers());
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
