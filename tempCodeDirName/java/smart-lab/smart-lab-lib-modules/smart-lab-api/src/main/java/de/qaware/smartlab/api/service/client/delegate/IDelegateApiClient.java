package de.qaware.smartlab.api.service.client.delegate;

import de.qaware.smartlab.api.service.constant.delegate.DelegateApiConstants;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
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
            @Param(DelegateApiConstants.PARAMETER_NAME_ACTUATOR_TYPE) String actuatorType,
            IActionArgs actionArgs);
}
