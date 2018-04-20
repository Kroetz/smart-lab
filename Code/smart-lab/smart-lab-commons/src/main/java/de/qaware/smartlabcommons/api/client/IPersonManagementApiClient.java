package de.qaware.smartlabcommons.api.client;

import de.qaware.smartlabcommons.api.PersonManagementApiConstants;
import de.qaware.smartlabcommons.data.person.IPerson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "person-management", url = "http://localhost:8080")
@Component
public interface IPersonManagementApiClient {

    @GetMapping(PersonManagementApiConstants.MAPPING_BASE + PersonManagementApiConstants.MAPPING_GET_PERSONS)
    List<IPerson> getPersons();

    @GetMapping(PersonManagementApiConstants.MAPPING_BASE + PersonManagementApiConstants.MAPPING_GET_PERSON)
    ResponseEntity<IPerson> getPerson(@PathVariable("personId") String personId);

    @PostMapping(value = PersonManagementApiConstants.MAPPING_BASE + PersonManagementApiConstants.MAPPING_CREATE_PERSON, consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean createPerson(@RequestBody IPerson person);

    @DeleteMapping(PersonManagementApiConstants.MAPPING_BASE + PersonManagementApiConstants.MAPPING_DELETE_PERSON)
    boolean deletePerson(@PathVariable("personId") String personId);
}
