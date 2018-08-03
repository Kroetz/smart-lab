package de.qaware.smartlabtrigger.service.controller;

import de.qaware.smartlabcore.service.constant.trigger.TriggerApiConstants;
import de.qaware.smartlabcore.data.job.IJobInfo;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.service.controller.url.AbstractBaseUrlController;
import de.qaware.smartlabcore.service.url.IBaseUrlDetector;
import de.qaware.smartlabtrigger.service.business.ITriggerBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;

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

    @PostMapping(TriggerApiConstants.MAPPING_SET_UP_CURRENT_MEETING_BY_LOCATION_ID)
    public ResponseEntity<IJobInfo> setUpCurrentMeetingByLocationId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl) {
        log.info("Received call to set up the current meeting at the location with ID \"{}\"", locationId);
        ResponseEntity<IJobInfo> response;
        try {
            if(nonNull(callbackUrl) && !this.urlValidator.isValid(callbackUrl)) throw new MalformedURLException();
            response = ResponseEntity.accepted().body(this.triggerBusinessLogic.setUpCurrentMeetingByLocationId(
                    LocationId.of(locationId),
                    nonNull(callbackUrl) ? new URL(callbackUrl) : null));
        } catch (MalformedURLException e) {
            // TODO: Better exception and message
            throw new RuntimeException(e);
        }
        log.info("Returning response with HTTP status code {}", response.getStatusCodeValue());
        return response;
    }

    @PostMapping(TriggerApiConstants.MAPPING_SET_UP_CURRENT_MEETING_BY_WORKGROUP_ID)
    public ResponseEntity<IJobInfo> setUpCurrentMeetingByWorkgroupId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl) {
        try {
            if(nonNull(callbackUrl) && !this.urlValidator.isValid(callbackUrl)) throw new MalformedURLException();
            return ResponseEntity.accepted().body(this.triggerBusinessLogic.setUpCurrentMeetingByWorkgroupId(
                    WorkgroupId.of(workgroupId),
                    nonNull(callbackUrl) ? new URL(callbackUrl) : null));
        } catch (MalformedURLException e) {
            // TODO: Better exception and message
            throw new RuntimeException(e);
        }
    }

    @PostMapping(TriggerApiConstants.MAPPING_CLEAN_UP_CURRENT_MEETING_BY_LOCATION_ID)
    public ResponseEntity<IJobInfo> cleanUpCurrentMeetingByLocationId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl) {
        try {
            if(nonNull(callbackUrl) && !this.urlValidator.isValid(callbackUrl)) throw new MalformedURLException();
            return ResponseEntity.accepted().body(this.triggerBusinessLogic.cleanUpCurrentMeetingByLocationId(
                    LocationId.of(locationId),
                    nonNull(callbackUrl) ? new URL(callbackUrl) : null));
        } catch (MalformedURLException e) {
            // TODO: Better exception and message
            throw new RuntimeException(e);
        }
    }

    @PostMapping(TriggerApiConstants.MAPPING_CLEAN_UP_CURRENT_MEETING_BY_WORKGROUP_ID)
    public ResponseEntity<IJobInfo> cleanUpCurrentMeetingByWorkgroupId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl) {
        try {
            if(nonNull(callbackUrl) && !this.urlValidator.isValid(callbackUrl)) throw new MalformedURLException();
            return ResponseEntity.accepted().body(this.triggerBusinessLogic.cleanUpCurrentMeetingByWorkgroupId(
                    WorkgroupId.of(workgroupId),
                    nonNull(callbackUrl) ? new URL(callbackUrl) : null));
        } catch (MalformedURLException e) {
            // TODO: Better exception and message
            throw new RuntimeException(e);
        }
    }

    @PostMapping(TriggerApiConstants.MAPPING_START_CURRENT_MEETING_BY_LOCATION_ID)
    public ResponseEntity<IJobInfo> startCurrentMeetingByLocationId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl) {
        try {
            if(nonNull(callbackUrl) && !this.urlValidator.isValid(callbackUrl)) throw new MalformedURLException();
            return ResponseEntity.accepted().body(this.triggerBusinessLogic.startCurrentMeetingByLocationId(
                    LocationId.of(locationId),
                    nonNull(callbackUrl) ? new URL(callbackUrl) : null));
        } catch (MalformedURLException e) {
            // TODO: Better exception and message
            throw new RuntimeException(e);
        }
    }

    @PostMapping(TriggerApiConstants.MAPPING_START_CURRENT_MEETING_BY_WORKGROUP_ID)
    public ResponseEntity<IJobInfo> startCurrentMeetingByWorkgroupId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl) {
        try {
            if(nonNull(callbackUrl) && !this.urlValidator.isValid(callbackUrl)) throw new MalformedURLException();
            return ResponseEntity.accepted().body(this.triggerBusinessLogic.startCurrentMeetingByWorkgroupId(
                    WorkgroupId.of(workgroupId),
                    nonNull(callbackUrl) ? new URL(callbackUrl) : null));
        } catch (MalformedURLException e) {
            // TODO: Better exception and message
            throw new RuntimeException(e);
        }
    }

    @PostMapping(TriggerApiConstants.MAPPING_STOP_CURRENT_MEETING_BY_LOCATION_ID)
    public ResponseEntity<IJobInfo> stopCurrentMeetingByLocationId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl) {
        try {
            if(nonNull(callbackUrl) && !this.urlValidator.isValid(callbackUrl)) throw new MalformedURLException();
            return ResponseEntity.accepted().body(this.triggerBusinessLogic.stopCurrentMeetingByLocationId(
                    LocationId.of(locationId),
                    nonNull(callbackUrl) ? new URL(callbackUrl) : null));
        } catch (MalformedURLException e) {
            // TODO: Better exception and message
            throw new RuntimeException(e);
        }
    }

    @PostMapping(TriggerApiConstants.MAPPING_STOP_CURRENT_MEETING_BY_WORKGROUP_ID)
    public ResponseEntity<IJobInfo> stopCurrentMeetingByWorkgroupId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl) {
        try {
            if(nonNull(callbackUrl) && !this.urlValidator.isValid(callbackUrl)) throw new MalformedURLException();
            return ResponseEntity.accepted().body(this.triggerBusinessLogic.stopCurrentMeetingByWorkgroupId(
                    WorkgroupId.of(workgroupId),
                    nonNull(callbackUrl) ? new URL(callbackUrl) : null));
        } catch (MalformedURLException e) {
            // TODO: Better exception and message
            throw new RuntimeException(e);
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
