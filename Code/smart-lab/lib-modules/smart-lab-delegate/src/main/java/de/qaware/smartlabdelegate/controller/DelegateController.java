package de.qaware.smartlabdelegate.controller;

import de.qaware.smartlabcommons.api.DelegateApiConstants;
import de.qaware.smartlabcommons.data.action.IActionArgs;
import de.qaware.smartlabdelegate.business.IDelegateBusinessLogic;
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
    public ResponseEntity<Void> executeAction(
            @PathVariable(DelegateApiConstants.PARAMETER_NAME_ACTION_ID) String actionId,
            @RequestBody IActionArgs actionArgs) {
        log.info("Received call to execute action with ID \"{}\"", actionId);
        this.delegateBusinessLogic.executeAction(actionId, actionArgs);
        ResponseEntity<Void> response = ResponseEntity.ok().build();
        log.info("Returning response with HTTP status code {}", response.getStatusCodeValue());
        // TODO: Proper response
        return response;
    }
}
