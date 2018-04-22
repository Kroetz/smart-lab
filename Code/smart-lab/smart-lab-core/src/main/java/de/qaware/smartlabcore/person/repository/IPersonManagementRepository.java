package de.qaware.smartlabcore.person.repository;

import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcore.generic.result.CreationResult;
import de.qaware.smartlabcore.generic.result.DeletionResult;

import java.util.List;
import java.util.Optional;

public interface IPersonManagementRepository {

    List<IPerson> getPersons();
    Optional<IPerson> getPerson(String personId);

    CreationResult createPerson(IPerson person);

    DeletionResult deletePerson(String personId);
}
