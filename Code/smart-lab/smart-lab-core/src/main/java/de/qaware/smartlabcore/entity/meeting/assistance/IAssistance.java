package de.qaware.smartlabcore.entity.meeting.assistance;

import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import de.qaware.smartlabcore.entity.meeting.IMeeting;
import de.qaware.smartlabcore.entity.room.Room;

public interface IAssistance {

    void setUp(Room room, IMeeting meeting, Workgroup workgroup);
    void cleanUp(Room room, IMeeting meeting, Workgroup workgroup);
    void start(Room room, IMeeting meeting, Workgroup workgroup);
    void stop(Room room, IMeeting meeting, Workgroup workgroup);
}
