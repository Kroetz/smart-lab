package de.qaware.smartlabcore.person.controller;

import de.qaware.smartlabcommons.data.person.Person;
import de.qaware.smartlabcore.person.service.IPersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/smart-lab/api/person")
@Slf4j
public class PersonController {

    private final IPersonService personService;

    @Autowired
    public PersonController(@Qualifier("mock") IPersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getPersons() {
        return personService.getPersons();
    }

    @GetMapping("/{personId}")
    public Optional<Person> getPerson(@PathVariable("personId") long personId) {
        return personService.getPerson(personId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @DeleteMapping("/{personId}")
    public void deletePerson(@PathVariable("personId") long personId) {
        personService.deletePerson(personId);
    }
}
