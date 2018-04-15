package de.qaware.smartlabpersonconfigprovidermock.controller;

import de.qaware.smartlabcommons.data.person.Person;
import de.qaware.smartlabpersonconfigprovidermock.service.IPersonConfigProviderMockService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/smart-lab/person-config-provider")
public class PersonConfigProviderMockController {

    private final IPersonConfigProviderMockService personConfigProviderService;

    public PersonConfigProviderMockController(IPersonConfigProviderMockService personConfigProviderService) {
        this.personConfigProviderService = personConfigProviderService;
    }

    @PostMapping("/{personId}/exists")
    public boolean exists(@PathVariable("personId") long personId) {
        return personConfigProviderService.exists(personId);
    }

    @GetMapping
    public List<Person> getPersons() {
        return personConfigProviderService.getPersons();
    }

    @GetMapping("/{personId}")
    public Optional<Person> getPerson(@PathVariable("personId") long personId) {
        return personConfigProviderService.getPerson(personId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean createPerson(@RequestBody Person person) {
        return personConfigProviderService.createPerson(person);
    }

    @DeleteMapping("/{personId}")
    public boolean deletePerson(@PathVariable("personId") long personId) {
        return personConfigProviderService.deletePerson(personId);
    }
}
