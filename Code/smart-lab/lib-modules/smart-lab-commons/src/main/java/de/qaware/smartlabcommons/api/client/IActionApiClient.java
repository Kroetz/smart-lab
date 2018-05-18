package de.qaware.smartlabcommons.api.client;

import de.qaware.smartlabcommons.api.ActionApiConstants;
import de.qaware.smartlabcommons.data.action.ActionResult;
import de.qaware.smartlabcommons.data.action.IActionArgs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(ActionApiConstants.FEIGN_CLIENT_VALUE)
@Component  // TODO: Remove annotation and suppress resulting autowiring warnings
public interface IActionApiClient {

    @PostMapping(
            value = ActionApiConstants.MAPPING_BASE + ActionApiConstants.MAPPING_EXECUTE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ActionResult> executeAction(
            @PathVariable(ActionApiConstants.PARAMETER_NAME_ACTION_ID) String actionId,
            @RequestBody IActionArgs actionArgs);
}
