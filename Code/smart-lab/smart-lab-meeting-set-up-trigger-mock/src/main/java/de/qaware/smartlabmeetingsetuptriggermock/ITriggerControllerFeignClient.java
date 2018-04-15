package de.qaware.smartlabmeetingsetuptriggermock;

import de.qaware.smartlabcore.trigger.controller.TriggerController;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@FeignClient(value = "trigger-controller2", url = "http://localhost:8080")
@RestController
@RequestMapping(TriggerController.MAPPING_BASE)
@Qualifier("mock")
public interface ITriggerControllerFeignClient {

    @PostMapping(TriggerController.MAPPING_SET_UP_CURRENT_MEETING_BY_ROOM_ID)
    void setUpCurrentMeetingByRoomId(@PathVariable("roomId") long roomId);

    @PostMapping(TriggerController.MAPPING_CLEAN_UP_CURRENT_MEETING_BY_ROOM_ID)
    void cleanUpCurrentMeetingByRoomId(@PathVariable("roomId") long roomId);
}
