package de.qaware.smartlabcommons.api.management.client;

import de.qaware.smartlabcommons.api.management.PersonManagementApiConstants;
import de.qaware.smartlabcommons.data.person.Person;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "person-management", url = "http://localhost:8080")
public interface IPersonManagementApiClient {

    @GetMapping(PersonManagementApiConstants.MAPPING_BASE + PersonManagementApiConstants.MAPPING_GET_PERSONS)
    List<Person> getPersons();

    @GetMapping(PersonManagementApiConstants.MAPPING_BASE + PersonManagementApiConstants.MAPPING_GET_PERSON)
    Optional<Person> getPerson(@PathVariable("personId") long personId);

    @PostMapping(value = PersonManagementApiConstants.MAPPING_BASE + PersonManagementApiConstants.MAPPING_CREATE_PERSON, consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean createPerson(@RequestBody Person person);

    @DeleteMapping(PersonManagementApiConstants.MAPPING_BASE + PersonManagementApiConstants.MAPPING_DELETE_PERSON)
    void deletePerson(@PathVariable("personId") long personId);
}
