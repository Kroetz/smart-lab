package de.qaware.smartlabcore.person.service.mock;

import de.qaware.smartlabcommons.data.person.Person;
import de.qaware.smartlabcore.person.service.IPersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("mock")
@Slf4j
public class PersonServiceMock implements IPersonService {

    private final IPersonConfigProviderMock personConfigProvider;

    @Autowired
    public PersonServiceMock(@Qualifier("mock") IPersonConfigProviderMock personConfigProvider) {
        this.personConfigProvider = personConfigProvider;
    }

    @Override
    public List<Person> getPersons() {
        return personConfigProvider.getPersons();
    }

    @Override
    public Optional<Person> getPerson(long personId) {
        return personConfigProvider.getPerson(personId);
    }

    @Override
    public boolean createPerson(Person person) {
        return personConfigProvider.createPerson(person);
    }

    @Override
    public void deletePerson(long personId) {
        personConfigProvider.deletePerson(personId);
    }
}
