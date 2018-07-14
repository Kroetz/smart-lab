package de.qaware.smartlabaction.service.controller;

import de.qaware.smartlabaction.service.business.IActionBusinessLogic;
import de.qaware.smartlabapi.service.constant.action.ActionApiConstants;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.service.controller.url.AbstractBaseUrlController;
import de.qaware.smartlabcore.service.url.IBaseUrlDetector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;

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

    @RestController
    @RequestMapping(ActionApiConstants.MAPPING_BASE)
    @Slf4j
    public static class BaseUrlController extends AbstractBaseUrlController {

        public BaseUrlController(IBaseUrlDetector baseUrlDetector) {
            super(baseUrlDetector);
        }

        @Override
        @GetMapping(ActionApiConstants.MAPPING_GET_BASE_URL)
        public ResponseEntity<URL> getBaseUrl() {
            return super.getBaseUrl();
        }
    }
}