package de.qaware.smartlabcore.meeting.service.mock;

import de.qaware.smartlabcore.entity.meeting.IMeeting;
import org.springframework.beans.factory.annotation.Qualifier;
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
@Qualifier("mock")
public interface IMeetingConfigProviderMock {

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
}
