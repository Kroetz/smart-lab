package de.qaware.smartlabpersonconfigprovidermock.service;

import de.qaware.smartlabcommons.data.person.Person;
import de.qaware.smartlabcommons.data.person.Person;
import lombok.val;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonConfigProviderMockService implements IPersonConfigProviderMockService {

    private List<Person> persons;

    public PersonConfigProviderMockService() throws MalformedURLException {
        this.persons = new ArrayList<>();

        this.persons.add(Person.builder()
                .id(0)
                .name("Coast Guard Alice")
                .email("alice@coast-guard.com")
                .build());
        this.persons.add(Person.builder()
                .id(1)
                .name("Coast Guard Ben")
                .email("ben@coast-guard.com")
                .build());
        this.persons.add(Person.builder()
                .id(2)
                .name("Coast Guard Charlie")
                .email("charlie@coast-guard.com")
                .build());
        this.persons.add(Person.builder()
                .id(3)
                .name("Forest Ranger Anna")
                .email("anna@forest-rangers.com")
                .build());
        this.persons.add(Person.builder()
                .id(4)
                .name("Forest Ranger Barry")
                .email("barry@forest-rangers.com")
                .build());
        this.persons.add(Person.builder()
                .id(5)
                .name("Forest Ranger Caroline")
                .email("caroline@forest-rangers.com")
                .build());
        this.persons.add(Person.builder()
                .id(6)
                .name("Fire Fighter Anthony")
                .email("anthony@fire-fighters.com")
                .build());
        this.persons.add(Person.builder()
                .id(7)
                .name("Fire Fighter Bruce")
                .email("bruce@fire-fighters.com")
                .build());
        this.persons.add(Person.builder()
                .id(8)
                .name("Fire Fighter Carlos")
                .email("carlos@fire-fighters.com")
                .build());

        sortPersonsById();
    }

    @Override
    public boolean exists(long personId) {
        return persons.stream()
                .anyMatch(person -> person.getId() == personId);
    }

    @Override
    public List<Person> getPersons() {
        return this.persons;
    }

    @Override
    public Optional<Person> getPerson(long personId) {
        return persons.stream()
                .filter(person -> person.getId() == personId)
                .findFirst();
    }

    @Override
    public boolean createPerson(Person person) {
        return !exists(person.getId()) && persons.add(person);
    }

    @Override
    public boolean deletePerson(long personId) {
        return persons.removeAll(persons.stream()
                .filter(person -> person.getId() == personId)
                .collect(Collectors.toList()));
    }

    private void sortPersonsById() {
        persons.sort(Comparator.comparingLong(Person::getId));
    }
}
