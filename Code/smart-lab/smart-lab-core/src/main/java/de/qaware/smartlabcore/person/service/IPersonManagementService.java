package de.qaware.smartlabcore.person.service;

import de.qaware.smartlabcommons.data.person.Person;

import java.util.List;
import java.util.Optional;

public interface IPersonManagementService {

    List<Person> getPersons();
    Optional<Person> getPerson(long personId);

    boolean createPerson(Person person);

    void deletePerson(long personId);
}
