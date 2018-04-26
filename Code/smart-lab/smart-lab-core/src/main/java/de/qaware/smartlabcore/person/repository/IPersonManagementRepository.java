package de.qaware.smartlabcore.person.repository;

import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcore.generic.result.CreationResult;
import de.qaware.smartlabcore.generic.result.DeletionResult;

import java.util.Optional;
import java.util.Set;

public interface IPersonManagementRepository {

    Set<IPerson> getPersons();
    Optional<IPerson> getPerson(String personId);

    CreationResult createPerson(IPerson person);

    DeletionResult deletePerson(String personId);
}
