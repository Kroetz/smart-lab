package de.qaware.smartlabmeetingcleanuptriggermock;

import de.qaware.smartlabcore.entity.meeting.IMeeting;
import de.qaware.smartlabcore.meeting.controller.MeetingController;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "meeting-controller2", url = "http://localhost:8080")
@RestController
@RequestMapping(MeetingController.MAPPING_BASE)
@Qualifier("mock")
public interface IMeetingControllerFeignClient {

    @GetMapping
    List<IMeeting> getMeetings();
}
