package de.qaware.smartlabcore.person.controller;

import de.qaware.smartlabcommons.api.PersonManagementApiConstants;
import de.qaware.smartlabcommons.data.person.Person;
import de.qaware.smartlabcore.person.service.IPersonManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(PersonManagementApiConstants.MAPPING_BASE)
@Slf4j
public class PersonManagementController {



    private final IPersonManagementService personManagementService;

    public PersonManagementController(@Qualifier("mock") IPersonManagementService personManagementService) {
        this.personManagementService = personManagementService;
    }

    @GetMapping(PersonManagementApiConstants.MAPPING_GET_PERSONS)
    public List<Person> getPersons() {
        return personManagementService.getPersons();
    }

    @GetMapping(PersonManagementApiConstants.MAPPING_GET_PERSON)
    public Optional<Person> getPerson(@PathVariable("personId") long personId) {
        return personManagementService.getPerson(personId);
    }

    @PostMapping(value = PersonManagementApiConstants.MAPPING_CREATE_PERSON, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean createPerson(@RequestBody Person person) {
        return personManagementService.createPerson(person);
    }

    @DeleteMapping(PersonManagementApiConstants.MAPPING_DELETE_PERSON)
    public void deletePerson(@PathVariable("personId") long personId) {
        personManagementService.deletePerson(personId);
    }
}
