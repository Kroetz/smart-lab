package de.qaware.smartlab.trigger.service.controller;

import de.qaware.smartlab.api.service.constant.trigger.TriggerApiConstants;
import de.qaware.smartlab.core.data.job.IJobInfo;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.service.controller.url.AbstractBaseUrlController;
import de.qaware.smartlab.core.service.url.IBaseUrlDetector;
import de.qaware.smartlab.trigger.service.business.ITriggerBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

@RestController
@RequestMapping(TriggerApiConstants.MAPPING_BASE)
@Slf4j
public class TriggerController {

    private final ITriggerBusinessLogic triggerBusinessLogic;
    private final UrlValidator urlValidator;

    public TriggerController(
            ITriggerBusinessLogic triggerBusinessLogic,
            UrlValidator urlValidator) {
        this.triggerBusinessLogic = triggerBusinessLogic;
        this.urlValidator = urlValidator;
    }

    @PostMapping(TriggerApiConstants.MAPPING_SET_UP_CURRENT_EVENT_BY_LOCATION_ID)
    public ResponseEntity<IJobInfo> setUpCurrentEventByLocationId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl) {
        log.info("Received call to set up the current event at the location \"{}\"", locationId);
        ResponseEntity<IJobInfo> response;
        response = ResponseEntity.accepted().body(this.triggerBusinessLogic.setUpCurrentEventByLocationId(
                LocationId.of(locationId),
                nonNull(callbackUrl) ? callbackUrlFromString(callbackUrl) : null));
        log.info("Returning response with HTTP status code {}", response.getStatusCodeValue());
        return response;
    }

    @PostMapping(TriggerApiConstants.MAPPING_SET_UP_CURRENT_EVENT_BY_WORKGROUP_ID)
    public ResponseEntity<IJobInfo> setUpCurrentEventByWorkgroupId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl) {
        return ResponseEntity.accepted().body(this.triggerBusinessLogic.setUpCurrentEventByWorkgroupId(
                WorkgroupId.of(workgroupId),
                nonNull(callbackUrl) ? callbackUrlFromString(callbackUrl) : null));
    }

    @PostMapping(TriggerApiConstants.MAPPING_CLEAN_UP_CURRENT_EVENT_BY_LOCATION_ID)
    public ResponseEntity<IJobInfo> cleanUpCurrentEventByLocationId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl) {
        return ResponseEntity.accepted().body(this.triggerBusinessLogic.cleanUpCurrentEventByLocationId(
                LocationId.of(locationId),
                nonNull(callbackUrl) ? callbackUrlFromString(callbackUrl) : null));
    }

    @PostMapping(TriggerApiConstants.MAPPING_CLEAN_UP_CURRENT_EVENT_BY_WORKGROUP_ID)
    public ResponseEntity<IJobInfo> cleanUpCurrentEventByWorkgroupId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl) {
        return ResponseEntity.accepted().body(this.triggerBusinessLogic.cleanUpCurrentEventByWorkgroupId(
                WorkgroupId.of(workgroupId),
                nonNull(callbackUrl) ? callbackUrlFromString(callbackUrl) : null));
    }

    @PostMapping(TriggerApiConstants.MAPPING_START_CURRENT_EVENT_BY_LOCATION_ID)
    public ResponseEntity<IJobInfo> startCurrentEventByLocationId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl) {
        return ResponseEntity.accepted().body(this.triggerBusinessLogic.startCurrentEventByLocationId(
                LocationId.of(locationId),
                nonNull(callbackUrl) ? callbackUrlFromString(callbackUrl) : null));
    }

    @PostMapping(TriggerApiConstants.MAPPING_START_CURRENT_EVENT_BY_WORKGROUP_ID)
    public ResponseEntity<IJobInfo> startCurrentEventByWorkgroupId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl) {
        return ResponseEntity.accepted().body(this.triggerBusinessLogic.startCurrentEventByWorkgroupId(
                WorkgroupId.of(workgroupId),
                nonNull(callbackUrl) ? callbackUrlFromString(callbackUrl) : null));
    }

    @PostMapping(TriggerApiConstants.MAPPING_STOP_CURRENT_EVENT_BY_LOCATION_ID)
    public ResponseEntity<IJobInfo> stopCurrentEventByLocationId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl) {
        return ResponseEntity.accepted().body(this.triggerBusinessLogic.stopCurrentEventByLocationId(
                LocationId.of(locationId),
                nonNull(callbackUrl) ? callbackUrlFromString(callbackUrl) : null));
    }

    @PostMapping(TriggerApiConstants.MAPPING_STOP_CURRENT_EVENT_BY_WORKGROUP_ID)
    public ResponseEntity<IJobInfo> stopCurrentEventByWorkgroupId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl) {
        return ResponseEntity.accepted().body(this.triggerBusinessLogic.stopCurrentEventByWorkgroupId(
                WorkgroupId.of(workgroupId),
                nonNull(callbackUrl) ? callbackUrlFromString(callbackUrl) : null));
    }

    private URL callbackUrlFromString(String callbackUrl) throws IllegalArgumentException {
        try {
            if(nonNull(callbackUrl) && !this.urlValidator.isValid(callbackUrl)) throw new MalformedURLException(callbackUrl);
            return new URL(callbackUrl);
        }
        catch(MalformedURLException e) {
            String errorMessage = format("The callback URL \"%s\" must be valid", callbackUrl);
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage, e);
        }
    }

    @RestController
    @RequestMapping(TriggerApiConstants.MAPPING_BASE)
    @Slf4j
    public static class BaseUrlController extends AbstractBaseUrlController {

        public BaseUrlController(IBaseUrlDetector baseUrlDetector) {
            super(baseUrlDetector);
        }

        @Override
        @GetMapping(TriggerApiConstants.MAPPING_GET_BASE_URL)
        public ResponseEntity<URL> getBaseUrl() {
            return super.getBaseUrl();
        }
    }
}
