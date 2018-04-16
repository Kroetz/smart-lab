package de.qaware.smartlabmeetingconfigprovidermock.controller;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabmeetingconfigprovidermock.service.IMeetingConfigProviderMockService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/smart-lab/meeting-config-provider")
public class MeetingConfigProviderMockController {

    private final IMeetingConfigProviderMockService meetingConfigProviderService;

    public MeetingConfigProviderMockController(IMeetingConfigProviderMockService meetingConfigProviderService) {
        this.meetingConfigProviderService = meetingConfigProviderService;
    }

    @PostMapping(value = "/{meetingId}/exists")
    public boolean exists(@PathVariable("meetingId") long meetingId) {
        return meetingConfigProviderService.exists(meetingId);
    }

    @GetMapping
    public List<IMeeting> getMeetings() {
        return meetingConfigProviderService.getMeetings();
    }

    @GetMapping("/{meetingId}")
    public Optional<IMeeting> getMeeting(@PathVariable("meetingId") long meetingId) {
        return meetingConfigProviderService.getMeeting(meetingId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean createMeeting(@RequestBody IMeeting meeting) {
        return meetingConfigProviderService.createMeeting(meeting);
    }

    @DeleteMapping("/{meetingId}")
    public boolean deleteMeeting(@PathVariable("meetingId") long meetingId) {
        return meetingConfigProviderService.deleteMeeting(meetingId);
    }

    @PutMapping("/{meetingId}/shorten")
    public void shortenMeeting(
            @PathVariable("meetingId") long meetingId,
            @RequestParam(value = "shortening-in-minutes") long shorteningInMinutes) {
        meetingConfigProviderService.shortenMeeting(meetingId, Duration.ofMinutes(shorteningInMinutes));
    }

    @PutMapping("/{meetingId}/extend")
    public boolean extendMeeting(
            @PathVariable("meetingId") long meetingId,
            @RequestParam(value = "extension-in-minutes") long extensionInMinutes) {
        return meetingConfigProviderService.extendMeeting(meetingId, Duration.ofMinutes(extensionInMinutes));
    }

    @PutMapping("/{meetingId}/shift")
    public boolean shiftMeeting(
            @PathVariable("meetingId") long meetingId,
            @RequestParam(value = "shift-in-minutes") long shiftInMinutes) {
        return meetingConfigProviderService.shiftMeeting(meetingId, Duration.ofMinutes(shiftInMinutes));
    }
}
