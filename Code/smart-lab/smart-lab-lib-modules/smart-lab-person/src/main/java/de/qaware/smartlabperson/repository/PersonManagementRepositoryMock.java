package de.qaware.smartlabperson.repository;

import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.PersonId;
import de.qaware.smartlabcore.generic.repository.AbstractEntityManagementRepositoryMock;
import de.qaware.smartlabsampledata.provider.ISampleDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Repository
@Slf4j
public class PersonManagementRepositoryMock extends AbstractEntityManagementRepositoryMock<IPerson, PersonId> implements IPersonManagementRepository {

    public PersonManagementRepositoryMock(ISampleDataProvider sampleDataProvider) {
        this.entities = new HashSet<>(sampleDataProvider.getWorkgroupMembers());
    }
}
