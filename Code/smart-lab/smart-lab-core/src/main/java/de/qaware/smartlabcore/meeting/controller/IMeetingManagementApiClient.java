package de.qaware.smartlabcore.meeting.controller;

import de.qaware.smartlabcore.entity.meeting.IMeeting;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "meeting-management", url = "http://localhost:8080")
public interface IMeetingManagementApiClient {

    @GetMapping(MeetingManagementController.MAPPING_BASE + MeetingManagementController.MAPPING_GET_MEETINGS)
    List<IMeeting> getMeetings();

    @GetMapping(MeetingManagementController.MAPPING_BASE + MeetingManagementController.MAPPING_GET_MEETING)
    Optional<IMeeting> getMeeting(@PathVariable("meetingId") long meetingId);

    @PostMapping(value = MeetingManagementController.MAPPING_BASE + MeetingManagementController.MAPPING_CREATE_MEETING, consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean createMeeting(@RequestBody IMeeting meeting);

    @PutMapping(MeetingManagementController.MAPPING_BASE + MeetingManagementController.MAPPING_SHORTEN_MEETING)
    void shortenMeeting(
            @PathVariable("meetingId") long meetingId,
            @RequestParam(value = "shortening-in-minutes") long shorteningInMinutes);

    @PutMapping(MeetingManagementController.MAPPING_BASE + MeetingManagementController.MAPPING_EXTEND_MEETING)
    boolean extendMeeting(
            @PathVariable("meetingId") long meetingId,
            @RequestParam(value = "extension-in-minutes") long extensionInMinutes);

    @PutMapping(MeetingManagementController.MAPPING_BASE + MeetingManagementController.MAPPING_SHIFT_MEETING)
    boolean shiftMeeting(
            @PathVariable("meetingId") long meetingId,
            @RequestParam(value = "shift-in-minutes") long shiftInMinutes);

    @DeleteMapping(MeetingManagementController.MAPPING_BASE + MeetingManagementController.MAPPING_DELETE_MEETING)
    void deleteMeeting(@PathVariable("meetingId") long meetingId);
}
