package de.qaware.smartlabapi.service.client.delegate;

import de.qaware.smartlabcore.service.constant.delegate.DelegateApiConstants;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public interface IDelegateApiClient {

    // TODO: String literals
    @RequestLine("POST " + DelegateApiConstants.MAPPING_BASE + DelegateApiConstants.MAPPING_EXECUTE)
    @Headers("Content-Type: " + MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<IActionResult> executeAction(
            @Param(DelegateApiConstants.PARAMETER_NAME_ACTION_ID) String actionId,
            @Param(DelegateApiConstants.PARAMETER_NAME_DEVICE_TYPE) String deviceType,
            IActionArgs actionArgs);
}
