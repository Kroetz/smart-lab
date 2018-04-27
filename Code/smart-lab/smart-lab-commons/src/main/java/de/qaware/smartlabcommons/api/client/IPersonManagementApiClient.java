package de.qaware.smartlabcommons.api.client;

import de.qaware.smartlabcommons.api.PersonManagementApiConstants;
import de.qaware.smartlabcommons.api.client.generic.ICrudApiClient;
import de.qaware.smartlabcommons.data.person.IPerson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@FeignClient(
        value = PersonManagementApiConstants.FEIGN_CLIENT_VALUE,
        url = PersonManagementApiConstants.FEIGN_CLIENT_URL)
@Component
public interface IPersonManagementApiClient extends ICrudApiClient<IPerson> {

    @Override
    @GetMapping(PersonManagementApiConstants.MAPPING_BASE + PersonManagementApiConstants.MAPPING_FIND_ALL)
    Set<IPerson> findAll();

    @Override
    @GetMapping(PersonManagementApiConstants.MAPPING_BASE + PersonManagementApiConstants.MAPPING_FIND_ONE)
    ResponseEntity<IPerson> findOne(@PathVariable(PersonManagementApiConstants.PARAMETER_NAME_PERSON_ID) String personId);

    @Override
    @PostMapping(value = PersonManagementApiConstants.MAPPING_BASE + PersonManagementApiConstants.MAPPING_CREATE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> create(@RequestBody IPerson person);

    @Override
    @DeleteMapping(PersonManagementApiConstants.MAPPING_BASE + PersonManagementApiConstants.MAPPING_DELETE)
    ResponseEntity<Void> delete(@PathVariable(PersonManagementApiConstants.PARAMETER_NAME_PERSON_ID) String personId);
}
