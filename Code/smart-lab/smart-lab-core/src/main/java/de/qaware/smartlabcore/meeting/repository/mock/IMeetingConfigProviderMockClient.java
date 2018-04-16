package de.qaware.smartlabcore.meeting.repository.mock;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "meeting-config-provider", url = "http://localhost:8083")
@ApiIgnore
@RestController
@RequestMapping("/smart-lab/meeting-config-provider")
public interface IMeetingConfigProviderMockClient {

    @PostMapping(value = "/{meetingId}/exists")
    boolean exists(@PathVariable("meetingId") long meetingId);

    @GetMapping
    List<IMeeting> getMeetings();

    @GetMapping("/{meetingId}")
    Optional<IMeeting> getMeeting(@PathVariable("meetingId") long meetingId);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean createMeeting(@RequestBody IMeeting meeting);

    @DeleteMapping("/{meetingId}")
    boolean deleteMeeting(@PathVariable("meetingId") long meetingId);

    @PutMapping("/{meetingId}/shorten")
    public void shortenMeeting(
            @PathVariable("meetingId") long meetingId,
            @RequestParam(value = "shortening-in-minutes") long shorteningInMinutes);

    @PutMapping("/{meetingId}/extend")
    public boolean extendMeeting(
            @PathVariable("meetingId") long meetingId,
            @RequestParam(value = "extension-in-minutes") long extensionInMinutes);

    @PutMapping("/{meetingId}/shift")
    public boolean shiftMeeting(
            @PathVariable("meetingId") long meetingId,
            @RequestParam(value = "shift-in-minutes") long shiftInMinutes);
}
