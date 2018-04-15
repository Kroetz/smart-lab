package de.qaware.smartlabcommons.api.client;

import de.qaware.smartlabcommons.api.MeetingManagementApiConstants;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "meeting-management", url = "http://localhost:8080")
public interface IMeetingManagementApiClient {

    @GetMapping(MeetingManagementApiConstants.MAPPING_BASE + MeetingManagementApiConstants.MAPPING_GET_MEETINGS)
    List<IMeeting> getMeetings();

    @GetMapping(MeetingManagementApiConstants.MAPPING_BASE + MeetingManagementApiConstants.MAPPING_GET_MEETING)
    Optional<IMeeting> getMeeting(@PathVariable("meetingId") long meetingId);

    @PostMapping(value = MeetingManagementApiConstants.MAPPING_BASE + MeetingManagementApiConstants.MAPPING_CREATE_MEETING, consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean createMeeting(@RequestBody IMeeting meeting);

    @PutMapping(MeetingManagementApiConstants.MAPPING_BASE + MeetingManagementApiConstants.MAPPING_SHORTEN_MEETING)
    void shortenMeeting(
            @PathVariable("meetingId") long meetingId,
            @RequestParam(value = "shortening-in-minutes") long shorteningInMinutes);

    @PutMapping(MeetingManagementApiConstants.MAPPING_BASE + MeetingManagementApiConstants.MAPPING_EXTEND_MEETING)
    boolean extendMeeting(
            @PathVariable("meetingId") long meetingId,
            @RequestParam(value = "extension-in-minutes") long extensionInMinutes);

    @PutMapping(MeetingManagementApiConstants.MAPPING_BASE + MeetingManagementApiConstants.MAPPING_SHIFT_MEETING)
    boolean shiftMeeting(
            @PathVariable("meetingId") long meetingId,
            @RequestParam(value = "shift-in-minutes") long shiftInMinutes);

    @DeleteMapping(MeetingManagementApiConstants.MAPPING_BASE + MeetingManagementApiConstants.MAPPING_DELETE_MEETING)
    void deleteMeeting(@PathVariable("meetingId") long meetingId);
}
