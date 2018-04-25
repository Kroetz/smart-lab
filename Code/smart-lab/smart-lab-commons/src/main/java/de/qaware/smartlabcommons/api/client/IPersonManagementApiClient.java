package de.qaware.smartlabcommons.api.client;

import de.qaware.smartlabcommons.api.PersonManagementApiConstants;
import de.qaware.smartlabcommons.api.client.generic.ICrudApiClient;
import de.qaware.smartlabcommons.data.person.IPerson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        value = PersonManagementApiConstants.FEIGN_CLIENT_VALUE,
        url = PersonManagementApiConstants.FEIGN_CLIENT_URL)
@Component
public interface IPersonManagementApiClient extends ICrudApiClient<IPerson> {

    @Override
    @GetMapping(PersonManagementApiConstants.MAPPING_BASE + PersonManagementApiConstants.MAPPING_GET_PERSONS)
    List<IPerson> findAll();

    @Override
    @GetMapping(PersonManagementApiConstants.MAPPING_BASE + PersonManagementApiConstants.MAPPING_GET_PERSON)
    ResponseEntity<IPerson> findOne(@PathVariable(PersonManagementApiConstants.PARAMETER_NAME_PERSON_ID) String personId);

    @Override
    @PostMapping(value = PersonManagementApiConstants.MAPPING_BASE + PersonManagementApiConstants.MAPPING_CREATE_PERSON, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> create(@RequestBody IPerson person);

    @Override
    @DeleteMapping(PersonManagementApiConstants.MAPPING_BASE + PersonManagementApiConstants.MAPPING_DELETE_PERSON)
    ResponseEntity<Void> delete(@PathVariable(PersonManagementApiConstants.PARAMETER_NAME_PERSON_ID) String personId);
}
