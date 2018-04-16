package de.qaware.smartlabcommons.api.configprovidermock.client;

import de.qaware.smartlabcommons.api.configprovidermock.PersonConfigProviderMockApiConstants;
import de.qaware.smartlabcommons.data.person.Person;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "person-config-provider", url = "http://localhost:8084")
public interface IPersonConfigProviderMockClient {

    @GetMapping(PersonConfigProviderMockApiConstants.MAPPING_BASE + PersonConfigProviderMockApiConstants.MAPPING_GET_PERSONS)
    List<Person> getPersons();

    @GetMapping(PersonConfigProviderMockApiConstants.MAPPING_BASE + PersonConfigProviderMockApiConstants.MAPPING_GET_PERSON)
    ResponseEntity<Person> getPerson(@PathVariable("personId") long personId);

    @PostMapping(
            value = PersonConfigProviderMockApiConstants.MAPPING_BASE + PersonConfigProviderMockApiConstants.MAPPING_CREATE_PERSON,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean createPerson(@RequestBody Person person);

    @DeleteMapping(PersonConfigProviderMockApiConstants.MAPPING_BASE + PersonConfigProviderMockApiConstants.MAPPING_DELETE_PERSON)
    boolean deletePerson(@PathVariable("personId") long personId);
}
