package de.qaware.smartlab.api.service.client.trigger;

import de.qaware.smartlab.api.service.constant.trigger.TriggerApiConstants;
import de.qaware.smartlab.core.data.job.IJobInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URL;

@FeignClient(name = TriggerApiConstants.FEIGN_CLIENT_NAME, path = TriggerApiConstants.MAPPING_BASE)
public interface ITriggerApiClient {

    @PostMapping(TriggerApiConstants.MAPPING_SET_UP_CURRENT_EVENT_BY_LOCATION_ID)
    ResponseEntity<IJobInfo> setUpCurrentEventByLocationId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl);

    @PostMapping(TriggerApiConstants.MAPPING_SET_UP_CURRENT_EVENT_BY_WORKGROUP_ID)
    ResponseEntity<IJobInfo> setUpCurrentEventByWorkgroupId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl);

    @PostMapping(TriggerApiConstants.MAPPING_CLEAN_UP_CURRENT_EVENT_BY_LOCATION_ID)
    ResponseEntity<IJobInfo> cleanUpCurrentEventByLocationId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl);

    @PostMapping(TriggerApiConstants.MAPPING_CLEAN_UP_CURRENT_EVENT_BY_WORKGROUP_ID)
    ResponseEntity<IJobInfo> cleanUpCurrentEventByWorkgroupId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl);

    @PostMapping(TriggerApiConstants.MAPPING_START_CURRENT_EVENT_BY_LOCATION_ID)
    ResponseEntity<IJobInfo> startCurrentEventByLocationId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl);

    @PostMapping(TriggerApiConstants.MAPPING_START_CURRENT_EVENT_BY_WORKGROUP_ID)
    ResponseEntity<IJobInfo> startCurrentEventByWorkgroupId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl);

    @PostMapping(TriggerApiConstants.MAPPING_STOP_CURRENT_EVENT_BY_LOCATION_ID)
    ResponseEntity<IJobInfo> stopCurrentEventByLocationId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl);

    @PostMapping(TriggerApiConstants.MAPPING_STOP_CURRENT_EVENT_BY_WORKGROUP_ID)
    ResponseEntity<IJobInfo> stopCurrentEventByWorkgroupId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl);

    @GetMapping(TriggerApiConstants.MAPPING_GET_BASE_URL)
    ResponseEntity<URL> getBaseUrl();
}
