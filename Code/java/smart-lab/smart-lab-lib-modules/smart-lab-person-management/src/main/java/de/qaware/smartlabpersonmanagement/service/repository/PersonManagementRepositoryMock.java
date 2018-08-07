package de.qaware.smartlabpersonmanagement.service.repository;

import de.qaware.smartlab.core.data.person.IPerson;
import de.qaware.smartlab.core.data.person.PersonId;
import de.qaware.smartlab.core.service.repository.AbstractBasicEntityManagementRepositoryMock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@Slf4j
public class PersonManagementRepositoryMock extends AbstractBasicEntityManagementRepositoryMock<IPerson, PersonId> implements IPersonManagementRepository {

    public PersonManagementRepositoryMock(Set<IPerson> initialPersons) {
        super(initialPersons);
    }
}
