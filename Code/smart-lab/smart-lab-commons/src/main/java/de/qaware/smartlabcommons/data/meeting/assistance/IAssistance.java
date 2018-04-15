package de.qaware.smartlabcommons.data.meeting.assistance;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.Room;
import de.qaware.smartlabcommons.data.workgroup.Workgroup;

public interface IAssistance {

    void setUp(Room room, IMeeting meeting, Workgroup workgroup);
    void cleanUp(Room room, IMeeting meeting, Workgroup workgroup);
    void start(Room room, IMeeting meeting, Workgroup workgroup);
    void stop(Room room, IMeeting meeting, Workgroup workgroup);
}
