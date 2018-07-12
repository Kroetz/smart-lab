package de.qaware.smartlabdelegate.controller;

import de.qaware.smartlabapi.DelegateApiConstants;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.generic.controller.url.AbstractBaseUrlController;
import de.qaware.smartlabcore.url.IBaseUrlDetector;
import de.qaware.smartlabdelegate.business.IDelegateBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;

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
            @PathVariable(DelegateApiConstants.PARAMETER_NAME_DEVICE_TYPE) String deviceType,
            @RequestBody IActionArgs actionArgs) {
        log.info("Received call to execute action with ID \"{}\"", actionId);
        IActionResult actionResult = this.delegateBusinessLogic.executeAction(actionId, deviceType, actionArgs);
        ResponseEntity<IActionResult> response = ResponseEntity.ok(actionResult);
        log.info("Returning response with HTTP status code {}", response.getStatusCodeValue());
        // TODO: Proper response
        return response;
    }

    @RestController
    @RequestMapping(DelegateApiConstants.MAPPING_BASE)
    @Slf4j
    public static class BaseUrlController extends AbstractBaseUrlController {

        public BaseUrlController(IBaseUrlDetector baseUrlDetector) {
            super(baseUrlDetector);
        }

        @Override
        @GetMapping(DelegateApiConstants.MAPPING_GET_BASE_URL)
        public ResponseEntity<URL> getBaseUrl() {
            return super.getBaseUrl();
        }
    }
}
