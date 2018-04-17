package de.qaware.smartlabcore.person.repository;

import de.qaware.smartlabcommons.api.configprovidermock.client.IPersonConfigProviderMockClient;
import de.qaware.smartlabcommons.data.person.IPerson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class IPersonManagementRepositoryMock implements IPersonManagementRepository {

    private final IPersonConfigProviderMockClient personConfigProviderMockClient;

    public IPersonManagementRepositoryMock(IPersonConfigProviderMockClient personConfigProviderMockClient) {
        this.personConfigProviderMockClient = personConfigProviderMockClient;
    }

    @Override
    public List<IPerson> getPersons() {
        return personConfigProviderMockClient.getPersons();
    }

    @Override
    public Optional<IPerson> getPerson(long personId) {
        return Optional.ofNullable(personConfigProviderMockClient.getPerson(personId).getBody());
    }

    @Override
    public boolean createPerson(IPerson person) {
        return personConfigProviderMockClient.createPerson(person);
    }

    @Override
    public void deletePerson(long personId) {
        personConfigProviderMockClient.deletePerson(personId);
    }
}
