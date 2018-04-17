package de.qaware.smartlabpersonconfigprovidermock.service;

import de.qaware.smartlabcommons.data.person.IPerson;

import java.util.List;
import java.util.Optional;

public interface IPersonConfigProviderMockService {

    List<IPerson> getPersons();
    Optional<IPerson> getPerson(long personId);
    boolean createPerson(IPerson person);
    boolean deletePerson(long personId);
}
