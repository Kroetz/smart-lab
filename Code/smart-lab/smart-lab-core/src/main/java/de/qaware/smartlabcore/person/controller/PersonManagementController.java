package de.qaware.smartlabcore.person.controller;

import de.qaware.smartlabcommons.data.person.Person;
import de.qaware.smartlabcore.person.service.IPersonManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(PersonManagementController.MAPPING_BASE)
@Slf4j
public class PersonManagementController {

    public static final String MAPPING_BASE = "/smart-lab/api/person";
    public static final String MAPPING_GET_PERSONS = "";
    public static final String MAPPING_GET_PERSON = "/{personId}";
    public static final String MAPPING_CREATE_PERSON = "";
    public static final String MAPPING_DELETE_PERSON = "/{personId}";

    private final IPersonManagementService personManagementService;

    public PersonManagementController(@Qualifier("mock") IPersonManagementService personManagementService) {
        this.personManagementService = personManagementService;
    }

    @GetMapping(MAPPING_GET_PERSONS)
    public List<Person> getPersons() {
        return personManagementService.getPersons();
    }

    @GetMapping(MAPPING_GET_PERSON)
    public Optional<Person> getPerson(@PathVariable("personId") long personId) {
        return personManagementService.getPerson(personId);
    }

    @PostMapping(value = MAPPING_CREATE_PERSON, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean createPerson(@RequestBody Person person) {
        return personManagementService.createPerson(person);
    }

    @DeleteMapping(MAPPING_DELETE_PERSON)
    public void deletePerson(@PathVariable("personId") long personId) {
        personManagementService.deletePerson(personId);
    }
}
