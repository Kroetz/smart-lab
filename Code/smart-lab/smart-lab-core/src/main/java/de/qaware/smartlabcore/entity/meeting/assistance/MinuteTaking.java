package de.qaware.smartlabcore.entity.meeting.assistance;

import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import de.qaware.smartlabcore.entity.meeting.IMeeting;
import de.qaware.smartlabcore.entity.room.Room;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MinuteTaking implements IAssistance {

    @Override
    public void setUp(Room room, IMeeting meeting, Workgroup workgroup) {

    }

    @Override
    public void cleanUp(Room room, IMeeting meeting, Workgroup workgroup) {

    }

    @Override
    public void start(Room room, IMeeting meeting, Workgroup workgroup) {
        log.info(String.format("Started minute taking in room \"%s\"", room.getName()));
        //room.getMinuteTakingDevice().ifPresent(device -> device.start());
    }

    @Override
    public void stop(Room room, IMeeting meeting, Workgroup workgroup) {
        log.info(String.format("Stopped minute taking in room \"%s\"", room.getName()));
        //room.getMinuteTakingDevice().ifPresent(device -> device.stop());
        // TODO Daten verschicken/hochladen
    }
}
