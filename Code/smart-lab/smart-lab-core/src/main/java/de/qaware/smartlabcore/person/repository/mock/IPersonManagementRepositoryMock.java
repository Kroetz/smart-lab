package de.qaware.smartlabcore.person.repository.mock;

import de.qaware.smartlabcommons.data.person.Person;
import de.qaware.smartlabcore.person.repository.IPersonManagementRepository;
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
    public List<Person> getPersons() {
        return personConfigProviderMockClient.getPersons();
    }

    @Override
    public Optional<Person> getPerson(long personId) {
        return personConfigProviderMockClient.getPerson(personId);
    }

    @Override
    public boolean createPerson(Person person) {
        return personConfigProviderMockClient.createPerson(person);
    }

    @Override
    public void deletePerson(long personId) {
        personConfigProviderMockClient.deletePerson(personId);
    }
}
