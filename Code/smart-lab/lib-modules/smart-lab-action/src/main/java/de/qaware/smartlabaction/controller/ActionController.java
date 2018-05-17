package de.qaware.smartlabaction.controller;

import de.qaware.smartlabaction.business.IActionBusinessLogic;
import de.qaware.smartlabcommons.api.ActionApiConstants;
import de.qaware.smartlabcommons.data.action.IActionArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ActionApiConstants.MAPPING_BASE)
@Slf4j
public class ActionController {

    private final IActionBusinessLogic actionBusinessLogic;

    public ActionController(IActionBusinessLogic actionBusinessLogic) {
        this.actionBusinessLogic = actionBusinessLogic;
    }

    @PostMapping(
            value = ActionApiConstants.MAPPING_EXECUTE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> executeAction(
            @PathVariable(ActionApiConstants.PARAMETER_NAME_ACTION_ID) String actionId,
            @RequestBody IActionArgs actionArgs) {
        log.info("Received call to execute action with ID \"{}\"", actionId);
        this.actionBusinessLogic.executeAction(actionId, actionArgs);
        ResponseEntity<Void> response = ResponseEntity.ok().build();
        log.info("Returning response with HTTP status code {}", response.getStatusCodeValue());
        // TODO: Proper response
        return response;
    }
}