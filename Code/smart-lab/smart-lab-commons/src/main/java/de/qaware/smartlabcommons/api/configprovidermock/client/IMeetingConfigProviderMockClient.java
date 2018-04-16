package de.qaware.smartlabcommons.api.configprovidermock.client;

import de.qaware.smartlabcommons.api.configprovidermock.MeetingConfigProviderMockApiConstants;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "meeting-config-provider", url = "http://localhost:8083")
public interface IMeetingConfigProviderMockClient {

    @GetMapping(MeetingConfigProviderMockApiConstants.MAPPING_BASE + MeetingConfigProviderMockApiConstants.MAPPING_GET_MEETINGS)
    List<IMeeting> getMeetings();

    @GetMapping(MeetingConfigProviderMockApiConstants.MAPPING_BASE + MeetingConfigProviderMockApiConstants.MAPPING_GET_MEETING)
    Optional<IMeeting> getMeeting(@PathVariable("meetingId") long meetingId);

    @PostMapping(
            value = MeetingConfigProviderMockApiConstants.MAPPING_BASE + MeetingConfigProviderMockApiConstants.MAPPING_CREATE_MEETING,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean createMeeting(@RequestBody IMeeting meeting);

    @DeleteMapping(MeetingConfigProviderMockApiConstants.MAPPING_BASE + MeetingConfigProviderMockApiConstants.MAPPING_DELETE_MEETING)
    boolean deleteMeeting(@PathVariable("meetingId") long meetingId);

    @PutMapping(MeetingConfigProviderMockApiConstants.MAPPING_BASE + MeetingConfigProviderMockApiConstants.MAPPING_SHORTEN_MEETING)
    public void shortenMeeting(
            @PathVariable("meetingId") long meetingId,
            @RequestParam(value = "shortening-in-minutes") long shorteningInMinutes);

    @PutMapping(MeetingConfigProviderMockApiConstants.MAPPING_BASE + MeetingConfigProviderMockApiConstants.MAPPING_EXTEND_MEETING)
    public boolean extendMeeting(
            @PathVariable("meetingId") long meetingId,
            @RequestParam(value = "extension-in-minutes") long extensionInMinutes);

    @PutMapping(MeetingConfigProviderMockApiConstants.MAPPING_BASE + MeetingConfigProviderMockApiConstants.MAPPING_SHIFT_MEETING)
    public boolean shiftMeeting(
            @PathVariable("meetingId") long meetingId,
            @RequestParam(value = "shift-in-minutes") long shiftInMinutes);
}
