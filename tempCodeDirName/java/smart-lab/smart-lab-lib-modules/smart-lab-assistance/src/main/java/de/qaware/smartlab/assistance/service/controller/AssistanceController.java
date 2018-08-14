package de.qaware.smartlab.assistance.service.controller;

import de.qaware.smartlab.api.service.constant.assistance.AssistanceApiConstants;
import de.qaware.smartlab.assistance.service.business.IAssistanceBusinessLogic;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.exception.context.InsufficientContextException;
import de.qaware.smartlab.core.service.controller.url.AbstractBaseUrlController;
import de.qaware.smartlab.core.service.url.IBaseUrlDetector;
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
    public ResponseEntity<Void> beginAssistance(@RequestBody IAssistanceContext context) {
        log.info("Received call to execute stage \"begin\" of assistance \"{}\" at the location \"{}\"",
                context.getAssistanceConfiguration().getAssistanceId(),
                context.getLocation().getId());
        this.assistanceBusinessLogic.beginAssistance(context);
        ResponseEntity<Void> response = ResponseEntity.ok().build();
        log.info("Returning response with HTTP status code {}", response.getStatusCodeValue());
        return response;
    }

    @PostMapping(
            value = AssistanceApiConstants.MAPPING_END,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> endAssistance(@RequestBody IAssistanceContext context) {
        log.info("Received call to execute stage \"end\" of assistance \"{}\" at the location \"{}\"",
                context.getAssistanceConfiguration().getAssistanceId(),
                context.getLocation().getId());
        this.assistanceBusinessLogic.endAssistance(context);
        ResponseEntity<Void> response = ResponseEntity.ok().build();
        log.info("Returning response with HTTP status code {}", response.getStatusCodeValue());
        return response;
    }

    @PostMapping(
            value = AssistanceApiConstants.MAPPING_DURING,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> duringAssistance(@RequestBody IAssistanceContext context) {
        log.info("Received call to execute stage \"during\" of assistance \"{}\" at the location \"{}\"",
                context.getAssistanceConfiguration().getAssistanceId(),
                context.getLocation().getId());
        this.assistanceBusinessLogic.duringAssistance(context);
        ResponseEntity<Void> response = ResponseEntity.ok().build();
        log.info("Returning response with HTTP status code {}", response.getStatusCodeValue());
        return response;
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
