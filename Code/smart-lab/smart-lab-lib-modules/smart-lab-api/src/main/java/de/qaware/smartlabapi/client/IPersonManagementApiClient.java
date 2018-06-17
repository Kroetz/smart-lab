package de.qaware.smartlabapi.client;

import de.qaware.smartlabapi.PersonManagementApiConstants;
import de.qaware.smartlabapi.client.generic.IBasicEntityManagementApiClient;
import de.qaware.smartlabcore.data.person.IPerson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@FeignClient(name = PersonManagementApiConstants.FEIGN_CLIENT_VALUE, path = PersonManagementApiConstants.MAPPING_BASE)
public interface IPersonManagementApiClient extends IBasicEntityManagementApiClient<IPerson> {

    @Override
    @GetMapping(PersonManagementApiConstants.MAPPING_FIND_ALL)
    Set<IPerson> findAll();

    @Override
    @GetMapping(PersonManagementApiConstants.MAPPING_FIND_ONE)
    ResponseEntity<IPerson> findOne(@PathVariable(PersonManagementApiConstants.PARAMETER_NAME_PERSON_ID) String personId);

    @Override
    @GetMapping(PersonManagementApiConstants.MAPPING_FIND_MULTIPLE)
    ResponseEntity<Set<IPerson>> findMultiple(@RequestParam(PersonManagementApiConstants.PARAMETER_NAME_PERSON_IDS) String[] personIds);

    @Override
    @PostMapping(value = PersonManagementApiConstants.MAPPING_CREATE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> create(@RequestBody IPerson person);

    @Override
    @DeleteMapping(PersonManagementApiConstants.MAPPING_DELETE)
    ResponseEntity<Void> delete(@PathVariable(PersonManagementApiConstants.PARAMETER_NAME_PERSON_ID) String personId);
}
