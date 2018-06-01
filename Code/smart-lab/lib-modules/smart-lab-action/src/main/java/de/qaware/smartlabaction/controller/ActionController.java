package de.qaware.smartlabaction.controller;

import de.qaware.smartlabaction.business.IActionBusinessLogic;
import de.qaware.smartlabapi.ActionApiConstants;
import de.qaware.smartlabcommons.data.action.generic.IActionArgs;
import de.qaware.smartlabcommons.data.action.generic.result.IActionResult;
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
    public ResponseEntity<IActionResult> executeAction(
            @PathVariable(ActionApiConstants.PARAMETER_NAME_ACTION_ID) String actionId,
            @RequestBody IActionArgs actionArgs) {
        log.info("Received call to execute action with ID \"{}\"", actionId);
        IActionResult actionResult = this.actionBusinessLogic.executeAction(actionId, actionArgs);
        ResponseEntity<IActionResult> response = ResponseEntity.ok(actionResult);
        log.info("Returning response with HTTP status code {}", response.getStatusCodeValue());
        // TODO: Proper response
        return response;
    }
}
