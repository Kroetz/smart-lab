package de.qaware.smartlabassistance.controller;

import de.qaware.smartlabcommons.api.AssistanceApiConstants;
import de.qaware.smartlabcommons.data.context.IContext;
import de.qaware.smartlabassistance.business.IAssistanceBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AssistanceApiConstants.MAPPING_BASE)
@Slf4j
public class AssistanceController {

    private final IAssistanceBusinessLogic assistanceBusinessLogic;

    public AssistanceController(IAssistanceBusinessLogic assistanceBusinessLogic) {
        this.assistanceBusinessLogic = assistanceBusinessLogic;
    }

    @PostMapping(
            value = AssistanceApiConstants.MAPPING_BASE + AssistanceApiConstants.MAPPING_BEGIN,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> beginAssistance(
            @PathVariable(AssistanceApiConstants.PARAMETER_NAME_ASSISTANCE_ID) String assistanceId,
            @RequestBody IContext context) {
        this.assistanceBusinessLogic.beginAssistance(assistanceId, context);
        // TODO
        return ResponseEntity.ok().build();
    }

    @PostMapping(
            value = AssistanceApiConstants.MAPPING_BASE + AssistanceApiConstants.MAPPING_END,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> endAssistance(
            @PathVariable(AssistanceApiConstants.PARAMETER_NAME_ASSISTANCE_ID) String assistanceId,
            @RequestBody IContext context) {
        this.assistanceBusinessLogic.endAssistance(assistanceId, context);
        // TODO
        return ResponseEntity.ok().build();
    }

    @PostMapping(
            value = AssistanceApiConstants.MAPPING_BASE + AssistanceApiConstants.MAPPING_UPDATE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateAssistance(
            @PathVariable(AssistanceApiConstants.PARAMETER_NAME_ASSISTANCE_ID) String assistanceId,
            @RequestBody IContext context) {
        this.assistanceBusinessLogic.updateAssistance(assistanceId, context);
        // TODO
        return ResponseEntity.ok().build();
    }
}
