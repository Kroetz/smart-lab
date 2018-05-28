package de.qaware.smartlabcommons.api.internal.client;

import de.qaware.smartlabcommons.api.internal.DelegateApiConstants;
import de.qaware.smartlabaction.action.IActionArgs;
import de.qaware.smartlabaction.action.generic.result.IActionResult;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public interface IDelegateApiClient {

    @RequestLine("POST " + DelegateApiConstants.MAPPING_BASE + DelegateApiConstants.MAPPING_EXECUTE)
    @Headers("Content-Type: " + MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<IActionResult> executeAction(
            @Param(DelegateApiConstants.PARAMETER_NAME_ACTION_ID) String actionId,
            @Param(DelegateApiConstants.PARAMETER_NAME_DEVICE_TYPE) String deviceType,
            IActionArgs actionArgs);
}
