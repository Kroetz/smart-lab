package de.qaware.smartlabassistance.service.controller;

import de.qaware.smartlab.api.service.constant.assistance.AssistanceApiConstants;
import de.qaware.smartlabassistance.service.business.IAssistanceBusinessLogic;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import de.qaware.smartlabcore.data.location.ILocation;
import de.qaware.smartlabcore.exception.InsufficientContextException;
import de.qaware.smartlabcore.service.controller.url.AbstractBaseUrlController;
import de.qaware.smartlabcore.service.url.IBaseUrlDetector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;

@RestController
@RequestMapping(AssistanceApiConstants.MAPPING_BASE)
@Slf4j
public class AssistanceController {

    private final IAssistanceBusinessLogic assistanceBusinessLogic;

    public AssistanceController(IAssistanceBusinessLogic assistanceBusinessLogic) {
        this.assistanceBusinessLogic = assistanceBusinessLogic;
    }

    @PostMapping(
            value = AssistanceApiConstants.MAPPING_BEGIN,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> beginAssistance(
            @PathVariable(AssistanceApiConstants.PARAMETER_NAME_ASSISTANCE_ID) String assistanceId,
            @RequestBody IAssistanceContext context) {
        log.info("Received call to begin assistance with ID \"{}\" at the location with ID \"{}\"",
                assistanceId,
                context.getLocation().map(ILocation::getId).orElseThrow(InsufficientContextException::new));
        this.assistanceBusinessLogic.beginAssistance(assistanceId, context);
        ResponseEntity<Void> response = ResponseEntity.ok().build();
        log.info("Returning response with HTTP status code {}", response.getStatusCodeValue());
        // TODO
        return response;
    }

    @PostMapping(
            value = AssistanceApiConstants.MAPPING_END,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> endAssistance(
            @PathVariable(AssistanceApiConstants.PARAMETER_NAME_ASSISTANCE_ID) String assistanceId,
            @RequestBody IAssistanceContext context) {
        this.assistanceBusinessLogic.endAssistance(assistanceId, context);
        // TODO
        return ResponseEntity.ok().build();
    }

    @PostMapping(
            value = AssistanceApiConstants.MAPPING_UPDATE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateAssistance(
            @PathVariable(AssistanceApiConstants.PARAMETER_NAME_ASSISTANCE_ID) String assistanceId,
            @RequestBody IAssistanceContext context) {
        this.assistanceBusinessLogic.updateAssistance(assistanceId, context);
        // TODO
        return ResponseEntity.ok().build();
    }

    @RestController
    @RequestMapping(AssistanceApiConstants.MAPPING_BASE)
    @Slf4j
    public static class BaseUrlController extends AbstractBaseUrlController {

        public BaseUrlController(IBaseUrlDetector baseUrlDetector) {
            super(baseUrlDetector);
        }

        @Override
        @GetMapping(AssistanceApiConstants.MAPPING_GET_BASE_URL)
        public ResponseEntity<URL> getBaseUrl() {
            return super.getBaseUrl();
        }
    }
}
