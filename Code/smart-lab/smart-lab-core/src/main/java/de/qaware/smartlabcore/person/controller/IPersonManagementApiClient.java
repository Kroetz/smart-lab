package de.qaware.smartlabcore.person.controller;

import de.qaware.smartlabcommons.data.person.Person;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "person-management", url = "http://localhost:8080")
public interface IPersonManagementApiClient {

    @GetMapping(PersonManagementController.MAPPING_BASE + PersonManagementController.MAPPING_GET_PERSONS)
    List<Person> getPersons();

    @GetMapping(PersonManagementController.MAPPING_BASE + PersonManagementController.MAPPING_GET_PERSON)
    Optional<Person> getPerson(@PathVariable("personId") long personId);

    @PostMapping(value = PersonManagementController.MAPPING_BASE + PersonManagementController.MAPPING_CREATE_PERSON, consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean createPerson(@RequestBody Person person);

    @DeleteMapping(PersonManagementController.MAPPING_BASE + PersonManagementController.MAPPING_DELETE_PERSON)
    void deletePerson(@PathVariable("personId") long personId);
}
