package de.qaware.smartlabapi.service.client.person;

import de.qaware.smartlabapi.service.constant.person.PersonManagementApiConstants;
import de.qaware.smartlabapi.service.client.generic.IBasicEntityManagementApiClient;
import de.qaware.smartlabcore.data.person.IPerson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.Set;

@FeignClient(name = PersonManagementApiConstants.FEIGN_CLIENT_NAME, path = PersonManagementApiConstants.MAPPING_BASE)
public interface IPersonManagementApiClient extends IBasicEntityManagementApiClient<IPerson> {

    @Override
    @GetMapping(PersonManagementApiConstants.MAPPING_FIND_ALL)
    @ResponseBody
    Set<IPerson> findAll();

    @Override
    @GetMapping(PersonManagementApiConstants.MAPPING_FIND_ONE)
    @ResponseBody
    ResponseEntity<IPerson> findOne(@PathVariable(PersonManagementApiConstants.PARAMETER_NAME_PERSON_ID) String personId);

    @Override
    @GetMapping(PersonManagementApiConstants.MAPPING_FIND_MULTIPLE)
    @ResponseBody
    ResponseEntity<Set<IPerson>> findMultiple(@RequestParam(PersonManagementApiConstants.PARAMETER_NAME_PERSON_IDS) String[] personIds);

    @Override
    @PostMapping(value = PersonManagementApiConstants.MAPPING_CREATE_SINGLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<IPerson> create(@RequestBody IPerson person);

    @Override
    @PostMapping(value = PersonManagementApiConstants.MAPPING_CREATE_MULTIPLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<Set<IPerson>> create(@RequestBody Set<IPerson> persons);

    @Override
    @DeleteMapping(PersonManagementApiConstants.MAPPING_DELETE)
    @ResponseBody
    ResponseEntity<Void> delete(@PathVariable(PersonManagementApiConstants.PARAMETER_NAME_PERSON_ID) String personId);

    @GetMapping(PersonManagementApiConstants.MAPPING_GET_BASE_URL)
    ResponseEntity<URL> getBaseUrl();
}
