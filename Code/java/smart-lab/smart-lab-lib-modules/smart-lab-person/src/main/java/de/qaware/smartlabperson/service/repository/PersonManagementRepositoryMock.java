package de.qaware.smartlabperson.service.repository;

import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.Person;
import de.qaware.smartlabcore.data.person.PersonId;
import de.qaware.smartlabcore.service.repository.AbstractBasicEntityManagementRepositoryMock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
@Slf4j
public class PersonManagementRepositoryMock extends AbstractBasicEntityManagementRepositoryMock<IPerson, PersonId> implements IPersonManagementRepository {

    public PersonManagementRepositoryMock(Set<Person> initialPersons) {
        super(initialPersons);
        this.entities = new HashSet<>();
    }
}
