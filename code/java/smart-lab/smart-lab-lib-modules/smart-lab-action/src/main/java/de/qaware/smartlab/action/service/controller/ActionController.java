package de.qaware.smartlab.action.service.controller;

import de.qaware.smartlab.action.service.business.IActionBusinessLogic;
import de.qaware.smartlab.api.service.constant.action.ActionApiConstants;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.service.controller.url.AbstractBaseUrlController;
import de.qaware.smartlab.core.service.url.IBaseUrlDetector;
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
        // TODO: A DTO for the action args must be introduced in order to make this this REST endpoint work when called from outside the system (either when the system was started as monolith or as microservices)
        log.info("Received call to execute action \"{}\"", actionId);
        IActionResult actionResult = this.actionBusinessLogic.executeAction(actionId, actionArgs);
        ResponseEntity<IActionResult> response = ResponseEntity.ok(actionResult);
        log.info("Returning response with HTTP status code {}", response.getStatusCodeValue());
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
