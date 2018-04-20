package de.qaware.smartlabcore.person.repository;

import de.qaware.smartlabcommons.data.person.IPerson;

import java.util.List;
import java.util.Optional;

public interface IPersonManagementRepository {

    List<IPerson> getPersons();
    Optional<IPerson> getPerson(String personId);

    boolean createPerson(IPerson person);

    boolean deletePerson(String personId);
}
