package de.qaware.smartlabcommons.api.client;

import de.qaware.smartlabcommons.api.DelegateApiConstants;
import de.qaware.smartlabcommons.data.action.ActionResult;
import de.qaware.smartlabcommons.data.action.IActionArgs;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public interface IDelegateApiClient {

    @RequestLine("POST " + DelegateApiConstants.MAPPING_BASE + DelegateApiConstants.MAPPING_EXECUTE)
    @Headers("Content-Type: " + MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ActionResult> executeAction(
            @Param(DelegateApiConstants.PARAMETER_NAME_ACTION_ID) String actionId,
            @Param(DelegateApiConstants.PARAMETER_NAME_DEVICE_TYPE) String deviceType,
            IActionArgs actionArgs);
}
