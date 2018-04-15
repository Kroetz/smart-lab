package de.qaware.smartlabcommons.data.meeting.assistance;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.Room;
import de.qaware.smartlabcommons.data.workgroup.Workgroup;
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
