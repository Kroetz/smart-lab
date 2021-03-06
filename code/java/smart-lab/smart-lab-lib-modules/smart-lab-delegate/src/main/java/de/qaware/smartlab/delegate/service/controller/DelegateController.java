package de.qaware.smartlab.delegate.service.controller;

import de.qaware.smartlab.api.service.constant.delegate.DelegateApiConstants;
import de.qaware.smartlab.core.action.generic.IActionArgs;
import de.qaware.smartlab.core.action.generic.IActionResult;
import de.qaware.smartlab.delegate.service.business.IDelegateBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(DelegateApiConstants.MAPPING_BASE)
@Slf4j
public class DelegateController {

    private final IDelegateBusinessLogic delegateBusinessLogic;

    public DelegateController(IDelegateBusinessLogic delegateBusinessLogic) {
        this.delegateBusinessLogic = delegateBusinessLogic;
    }

    @PostMapping(
            value = DelegateApiConstants.MAPPING_EXECUTE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IActionResult> executeAction(
            @PathVariable(DelegateApiConstants.PARAMETER_NAME_ACTION_ID) String actionId,
            @PathVariable(DelegateApiConstants.PARAMETER_NAME_ACTUATOR_TYPE) String actuatorType,
            @RequestBody IActionArgs actionArgs) {
        // TODO: A DTO for the action args must be introduced in order to make this this REST endpoint work when called from outside the system (either when the system was started as monolith or as microservices)
        log.info("Received call to execute action \"{}\"", actionId);
        IActionResult actionResult = this.delegateBusinessLogic.executeAction(actionId, actuatorType, actionArgs);
        ResponseEntity<IActionResult> response = ResponseEntity.ok(actionResult);
        log.info("Returning response with HTTP status code {}", response.getStatusCodeValue());
        return response;
    }
}
