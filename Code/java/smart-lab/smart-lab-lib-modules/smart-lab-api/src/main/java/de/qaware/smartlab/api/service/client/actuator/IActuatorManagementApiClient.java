package de.qaware.smartlab.api.service.client.actuator;

import de.qaware.smartlab.api.service.client.generic.IBasicEntityManagementApiClient;
import de.qaware.smartlab.core.data.actuator.ActuatorDto;
import de.qaware.smartlab.core.data.actuator.IActuator;
import de.qaware.smartlab.api.service.constant.actuator.ActuatorManagementApiConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.Set;

@FeignClient(name = ActuatorManagementApiConstants.FEIGN_CLIENT_NAME, path = ActuatorManagementApiConstants.MAPPING_BASE)
public interface IActuatorManagementApiClient extends IBasicEntityManagementApiClient<IActuator, ActuatorDto> {

    @Override
    @GetMapping(ActuatorManagementApiConstants.MAPPING_FIND_ALL)
    @ResponseBody
    Set<ActuatorDto> findAll();

    @Override
    @GetMapping(ActuatorManagementApiConstants.MAPPING_FIND_ONE)
    @ResponseBody
    ResponseEntity<ActuatorDto> findOne(@PathVariable(ActuatorManagementApiConstants.PARAMETER_NAME_ACTUATOR_ID) String actuatorId);

    @Override
    @GetMapping(ActuatorManagementApiConstants.MAPPING_FIND_MULTIPLE)
    @ResponseBody
    ResponseEntity<Set<ActuatorDto>> findMultiple(@RequestParam(ActuatorManagementApiConstants.PARAMETER_NAME_ACTUATOR_IDS) String[] actuatorIds);

    @Override
    @PostMapping(
            value = ActuatorManagementApiConstants.MAPPING_CREATE_SINGLE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<ActuatorDto> create(@RequestBody ActuatorDto actuator);

    @Override
    @PostMapping(
            value = ActuatorManagementApiConstants.MAPPING_CREATE_MULTIPLE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<Set<ActuatorDto>> create(@RequestBody Set<ActuatorDto> actuators);

    @Override
    @DeleteMapping(ActuatorManagementApiConstants.MAPPING_DELETE)
    @ResponseBody
    ResponseEntity<Void> delete(@PathVariable(ActuatorManagementApiConstants.PARAMETER_NAME_ACTUATOR_ID) String actuatorId);

    @GetMapping(ActuatorManagementApiConstants.MAPPING_GET_BASE_URL)
    ResponseEntity<URL> getBaseUrl();
}
