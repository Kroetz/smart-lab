package de.qaware.smartlabpersonconfigprovidermock.service;

import de.qaware.smartlabcommons.data.person.Person;

import java.util.List;
import java.util.Optional;

public interface IPersonConfigProviderMockService {

    boolean exists(long personId);
    List<Person> getPersons();
    Optional<Person> getPerson(long personId);
    boolean createPerson(Person person);
    boolean deletePerson(long personId);
}
