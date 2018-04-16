package de.qaware.smartlabmeetingconfigprovidermock.controller;

import de.qaware.smartlabcommons.api.configprovidermock.MeetingConfigProviderMockApiConstants;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcore.generic.controller.AbstractSmartLabController;
import de.qaware.smartlabmeetingconfigprovidermock.service.IMeetingConfigProviderMockService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping(MeetingConfigProviderMockApiConstants.MAPPING_BASE)
public class MeetingConfigProviderMockController extends AbstractSmartLabController {

    private final IMeetingConfigProviderMockService meetingConfigProviderService;

    public MeetingConfigProviderMockController(IMeetingConfigProviderMockService meetingConfigProviderService) {
        this.meetingConfigProviderService = meetingConfigProviderService;
    }

    @GetMapping(MeetingConfigProviderMockApiConstants.MAPPING_GET_MEETINGS)
    public List<IMeeting> getMeetings() {
        return meetingConfigProviderService.getMeetings();
    }

    @GetMapping(MeetingConfigProviderMockApiConstants.MAPPING_GET_MEETING)
    public ResponseEntity<IMeeting> getMeeting(@PathVariable("meetingId") long meetingId) {
        return responseFromOptional(meetingConfigProviderService.getMeeting(meetingId));
    }

    @PostMapping(
            value = MeetingConfigProviderMockApiConstants.MAPPING_CREATE_MEETING,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean createMeeting(@RequestBody IMeeting meeting) {
        return meetingConfigProviderService.createMeeting(meeting);
    }

    @DeleteMapping(MeetingConfigProviderMockApiConstants.MAPPING_DELETE_MEETING)
    public boolean deleteMeeting(@PathVariable("meetingId") long meetingId) {
        return meetingConfigProviderService.deleteMeeting(meetingId);
    }

    @PutMapping(MeetingConfigProviderMockApiConstants.MAPPING_SHORTEN_MEETING)
    public void shortenMeeting(
            @PathVariable("meetingId") long meetingId,
            @RequestParam(value = "shortening-in-minutes") long shorteningInMinutes) {
        meetingConfigProviderService.shortenMeeting(meetingId, Duration.ofMinutes(shorteningInMinutes));
    }

    @PutMapping(MeetingConfigProviderMockApiConstants.MAPPING_EXTEND_MEETING)
    public boolean extendMeeting(
            @PathVariable("meetingId") long meetingId,
            @RequestParam(value = "extension-in-minutes") long extensionInMinutes) {
        return meetingConfigProviderService.extendMeeting(meetingId, Duration.ofMinutes(extensionInMinutes));
    }

    @PutMapping(MeetingConfigProviderMockApiConstants.MAPPING_SHIFT_MEETING)
    public boolean shiftMeeting(
            @PathVariable("meetingId") long meetingId,
            @RequestParam(value = "shift-in-minutes") long shiftInMinutes) {
        return meetingConfigProviderService.shiftMeeting(meetingId, Duration.ofMinutes(shiftInMinutes));
    }
}
