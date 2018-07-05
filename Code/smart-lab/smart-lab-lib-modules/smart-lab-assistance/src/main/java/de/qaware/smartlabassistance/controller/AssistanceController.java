package de.qaware.smartlabassistance.controller;

import de.qaware.smartlabapi.AssistanceApiConstants;
import de.qaware.smartlabassistance.business.IAssistanceBusinessLogic;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.exception.InsufficientContextException;
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
            value = AssistanceApiConstants.MAPPING_BEGIN,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> beginAssistance(
            @PathVariable(AssistanceApiConstants.PARAMETER_NAME_ASSISTANCE_ID) String assistanceId,
            @RequestBody IAssistanceContext context) {
        log.info("Received call to begin assistance with ID \"{}\" in the room with ID \"{}\"",
                assistanceId,
                context.getRoom().map(IRoom::getId).orElseThrow(InsufficientContextException::new));
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
}
