package de.qaware.smartlabapi.client;

import de.qaware.smartlabapi.AssistanceApiConstants;
import de.qaware.smartlabcore.data.context.IContext;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = AssistanceApiConstants.FEIGN_CLIENT_VALUE, path = AssistanceApiConstants.MAPPING_BASE)
public interface IAssistanceApiClient {

    @PostMapping(
            value = AssistanceApiConstants.MAPPING_BEGIN,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> beginAssistance(
            @PathVariable(AssistanceApiConstants.PARAMETER_NAME_ASSISTANCE_ID) String assistanceId,
            @RequestBody IContext context);

    @PostMapping(
            value = AssistanceApiConstants.MAPPING_END,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> endAssistance(
            @PathVariable(AssistanceApiConstants.PARAMETER_NAME_ASSISTANCE_ID) String assistanceId,
            @RequestBody IContext context);

    @PostMapping(
            value = AssistanceApiConstants.MAPPING_UPDATE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> updateAssistance(
            @PathVariable(AssistanceApiConstants.PARAMETER_NAME_ASSISTANCE_ID) String assistanceId,
            @RequestBody IContext context);
}
