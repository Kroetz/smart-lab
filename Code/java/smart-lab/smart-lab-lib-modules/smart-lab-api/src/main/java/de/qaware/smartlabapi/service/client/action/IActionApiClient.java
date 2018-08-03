package de.qaware.smartlabapi.service.client.action;

import de.qaware.smartlabcore.service.constant.action.ActionApiConstants;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URL;

@FeignClient(name = ActionApiConstants.FEIGN_CLIENT_NAME, path = ActionApiConstants.MAPPING_BASE)
public interface IActionApiClient {

    @PostMapping(
            value = ActionApiConstants.MAPPING_EXECUTE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<IActionResult> executeAction(
            @PathVariable(ActionApiConstants.PARAMETER_NAME_ACTION_ID) String actionId,
            @RequestBody IActionArgs actionArgs);

    @GetMapping(ActionApiConstants.MAPPING_GET_BASE_URL)
    ResponseEntity<URL> getBaseUrl();
}
