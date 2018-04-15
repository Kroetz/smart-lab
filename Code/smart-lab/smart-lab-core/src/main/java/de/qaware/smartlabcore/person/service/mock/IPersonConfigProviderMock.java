package de.qaware.smartlabcore.person.service.mock;

import de.qaware.smartlabcommons.data.person.Person;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "person-config-provider", url = "http://localhost:8084")
@ApiIgnore
@RestController
@RequestMapping("/smart-lab/person-config-provider")
@Qualifier("mock")
public interface IPersonConfigProviderMock {

    @PostMapping(value = "/{personId}/exists")
    boolean exists(@PathVariable("personId") long personId);

    @GetMapping
    List<Person> getPersons();

    @GetMapping("/{personId}")
    Optional<Person> getPerson(@PathVariable("personId") long personId);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean createPerson(@RequestBody Person person);

    @DeleteMapping("/{personId}")
    boolean deletePerson(@PathVariable("personId") long personId);
}
