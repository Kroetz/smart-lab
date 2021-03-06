package de.qaware.smartlab.api.service.client.assistance;

import de.qaware.smartlab.api.service.constant.assistance.AssistanceApiConstants;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URL;

@FeignClient(name = AssistanceApiConstants.FEIGN_CLIENT_NAME, path = AssistanceApiConstants.MAPPING_BASE)
public interface IAssistanceApiClient {

    @PostMapping(
            value = AssistanceApiConstants.MAPPING_BEGIN,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> beginAssistance(@RequestBody IAssistanceContext context);

    @PostMapping(
            value = AssistanceApiConstants.MAPPING_END,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> endAssistance(@RequestBody IAssistanceContext context);

    @PostMapping(
            value = AssistanceApiConstants.MAPPING_DURING,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> duringAssistance(@RequestBody IAssistanceContext context);

    @GetMapping(AssistanceApiConstants.MAPPING_GET_BASE_URL)
    ResponseEntity<URL> getBaseUrl();
}
