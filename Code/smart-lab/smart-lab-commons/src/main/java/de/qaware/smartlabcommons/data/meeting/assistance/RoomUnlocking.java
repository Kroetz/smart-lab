package de.qaware.smartlabcommons.data.meeting.assistance;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.Room;
import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoomUnlocking extends AssitanceAdapter {

    @Override
    public void setUp(Room room, IMeeting meeting, Workgroup workgroup) {
        log.info(String.format("Unlocked room \"%s\" for members of workgroup \"%s\"", room.getName(), workgroup.getName()));
        //room.getMinuteTakingDevice().ifPresent(device -> device.start());
    }

    @Override
    public void cleanUp(Room room, IMeeting meeting, Workgroup workgroup) {
        log.info(String.format("Locked room \"%s\" for members of workgroup \"%s\"", room.getName(), workgroup.getName()));
        //room.getMinuteTakingDevice().ifPresent(device -> device.stop());
        // TODO Daten verschicken/hochladen
    }
}
