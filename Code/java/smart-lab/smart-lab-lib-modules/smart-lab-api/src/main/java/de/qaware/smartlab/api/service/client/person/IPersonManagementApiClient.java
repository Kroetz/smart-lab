package de.qaware.smartlab.api.service.client.person;

import de.qaware.smartlab.api.service.client.generic.IBasicEntityManagementApiClient;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.PersonDto;
import de.qaware.smartlab.api.service.constant.person.PersonManagementApiConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.Set;

@FeignClient(name = PersonManagementApiConstants.FEIGN_CLIENT_NAME, path = PersonManagementApiConstants.MAPPING_BASE)
public interface IPersonManagementApiClient extends IBasicEntityManagementApiClient<IPerson, PersonDto> {

    @Override
    @GetMapping(PersonManagementApiConstants.MAPPING_FIND_ALL)
    @ResponseBody
    Set<PersonDto> findAll();

    @Override
    @GetMapping(PersonManagementApiConstants.MAPPING_FIND_ONE)
    @ResponseBody
    ResponseEntity<PersonDto> findOne(@PathVariable(PersonManagementApiConstants.PARAMETER_NAME_PERSON_ID) String personId);

    @Override
    @GetMapping(PersonManagementApiConstants.MAPPING_FIND_MULTIPLE)
    @ResponseBody
    ResponseEntity<Set<PersonDto>> findMultiple(@RequestParam(PersonManagementApiConstants.PARAMETER_NAME_PERSON_IDS) String[] personIds);

    @Override
    @PostMapping(value = PersonManagementApiConstants.MAPPING_CREATE_SINGLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<PersonDto> create(@RequestBody PersonDto person);

    @Override
    @PostMapping(value = PersonManagementApiConstants.MAPPING_CREATE_MULTIPLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<Set<PersonDto>> create(@RequestBody Set<PersonDto> persons);

    @Override
    @DeleteMapping(PersonManagementApiConstants.MAPPING_DELETE)
    @ResponseBody
    ResponseEntity<Void> delete(@PathVariable(PersonManagementApiConstants.PARAMETER_NAME_PERSON_ID) String personId);

    @GetMapping(PersonManagementApiConstants.MAPPING_GET_BASE_URL)
    ResponseEntity<URL> getBaseUrl();
}
