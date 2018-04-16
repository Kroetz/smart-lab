package de.qaware.smartlabpersonconfigprovidermock.controller;

import de.qaware.smartlabcommons.api.configprovidermock.PersonConfigProviderMockApiConstants;
import de.qaware.smartlabcommons.data.person.Person;
import de.qaware.smartlabpersonconfigprovidermock.service.IPersonConfigProviderMockService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(PersonConfigProviderMockApiConstants.MAPPING_BASE)
public class PersonConfigProviderMockController {

    private final IPersonConfigProviderMockService personConfigProviderService;

    public PersonConfigProviderMockController(IPersonConfigProviderMockService personConfigProviderService) {
        this.personConfigProviderService = personConfigProviderService;
    }

    @GetMapping(PersonConfigProviderMockApiConstants.MAPPING_GET_PERSONS)
    public List<Person> getPersons() {
        return personConfigProviderService.getPersons();
    }

    @GetMapping(PersonConfigProviderMockApiConstants.MAPPING_GET_PERSON)
    public Optional<Person> getPerson(@PathVariable("personId") long personId) {
        return personConfigProviderService.getPerson(personId);
    }

    @PostMapping(
            value = PersonConfigProviderMockApiConstants.MAPPING_CREATE_PERSON,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean createPerson(@RequestBody Person person) {
        return personConfigProviderService.createPerson(person);
    }

    @DeleteMapping(PersonConfigProviderMockApiConstants.MAPPING_DELETE_PERSON)
    public boolean deletePerson(@PathVariable("personId") long personId) {
        return personConfigProviderService.deletePerson(personId);
    }
}
