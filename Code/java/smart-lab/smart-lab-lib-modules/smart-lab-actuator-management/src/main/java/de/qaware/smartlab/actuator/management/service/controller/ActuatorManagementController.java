package de.qaware.smartlab.actuator.management.service.controller;

import de.qaware.smartlab.actuator.management.service.business.IActuatorManagementBusinessLogic;
import de.qaware.smartlab.api.service.constant.actuator.ActuatorManagementApiConstants;
import de.qaware.smartlab.core.data.actuator.ActuatorDto;
import de.qaware.smartlab.core.data.actuator.ActuatorId;
import de.qaware.smartlab.core.data.actuator.IActuator;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.exception.EntityNotFoundException;
import de.qaware.smartlab.core.service.controller.AbstractSmartLabController;
import de.qaware.smartlab.core.service.controller.IBasicEntityManagementController;
import de.qaware.smartlab.core.service.controller.url.AbstractBaseUrlController;
import de.qaware.smartlab.core.service.url.IBaseUrlDetector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping(ActuatorManagementApiConstants.MAPPING_BASE)
@Slf4j
public class ActuatorManagementController extends AbstractSmartLabController implements IBasicEntityManagementController<IActuator, ActuatorDto> {

    private final IActuatorManagementBusinessLogic actuatorManagementBusinessLogic;
    private final IDtoConverter<IActuator, ActuatorDto> actuatorConverter;

    public ActuatorManagementController(
            IActuatorManagementBusinessLogic actuatorManagementBusinessLogic,
            IDtoConverter<IActuator, ActuatorDto> actuatorConverter) {
        this.actuatorManagementBusinessLogic = actuatorManagementBusinessLogic;
        this.actuatorConverter = actuatorConverter;
    }

    @Override
    @GetMapping(ActuatorManagementApiConstants.MAPPING_FIND_ALL)
    @ResponseBody
    public Set<ActuatorDto> findAll() {
        return this.actuatorManagementBusinessLogic.findAll().stream()
                .map(this.actuatorConverter::toDto)
                .collect(toSet());
    }

    @Override
    @GetMapping(ActuatorManagementApiConstants.MAPPING_FIND_ONE)
    @ResponseBody
    public ResponseEntity<ActuatorDto> findOne(@PathVariable(ActuatorManagementApiConstants.PARAMETER_NAME_ACTUATOR_ID) String actuatorId) {
        return responseFromEntityOptional(
                this.actuatorManagementBusinessLogic.findOne(ActuatorId.of(actuatorId)),
                this.actuatorConverter);
    }

    @Override
    @GetMapping(ActuatorManagementApiConstants.MAPPING_FIND_MULTIPLE)
    @ResponseBody
    public ResponseEntity<Set<ActuatorDto>> findMultiple(@RequestParam(ActuatorManagementApiConstants.PARAMETER_NAME_ACTUATOR_IDS) String[] actuatorIds) {
        return responseFromEntityOptionals(this.actuatorManagementBusinessLogic.findMultiple(stream(actuatorIds)
                        .map(ActuatorId::of)
                        .collect(toSet())),
                this.actuatorConverter);
    }

    @Override
    @PostMapping(value = ActuatorManagementApiConstants.MAPPING_CREATE_SINGLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ActuatorDto> create(@RequestBody ActuatorDto actuator) {
        IActuator actuatorToCreate = this.actuatorConverter.toEntity(actuator);
        ActuatorDto createdActuator = this.actuatorConverter.toDto(this.actuatorManagementBusinessLogic.create(actuatorToCreate));
        return ResponseEntity.ok(createdActuator);
    }

    @Override
    @PostMapping(value = ActuatorManagementApiConstants.MAPPING_CREATE_MULTIPLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Set<ActuatorDto>> create(@RequestBody Set<ActuatorDto> actuators) {
        return ResponseEntity.ok(this.actuatorManagementBusinessLogic.create(actuators
                .stream()
                .map(this.actuatorConverter::toEntity)
                .collect(toSet()))
                .stream()
                .map(this.actuatorConverter::toDto)
                .collect(toSet()));
    }

    @Override
    @DeleteMapping(ActuatorManagementApiConstants.MAPPING_DELETE)
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable(ActuatorManagementApiConstants.PARAMETER_NAME_ACTUATOR_ID) String actuatorId) {
        this.actuatorManagementBusinessLogic.delete(ActuatorId.of(actuatorId));
        return ResponseEntity.ok().build();
    }

    @RestController
    @RequestMapping(ActuatorManagementApiConstants.MAPPING_BASE)
    @Slf4j
    public static class BaseUrlController extends AbstractBaseUrlController {

        public BaseUrlController(IBaseUrlDetector baseUrlDetector) {
            super(baseUrlDetector);
        }

        @Override
        @GetMapping(ActuatorManagementApiConstants.MAPPING_GET_BASE_URL)
        public ResponseEntity<URL> getBaseUrl() {
            return super.getBaseUrl();
        }
    }
}
