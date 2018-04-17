package de.qaware.smartlabcommons.data.meeting.assistance;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.Room;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;

public interface IAssistance {

    void setUp(Room room, IMeeting meeting, IWorkgroup workgroup);
    void cleanUp(Room room, IMeeting meeting, IWorkgroup workgroup);
    void start(Room room, IMeeting meeting, IWorkgroup workgroup);
    void stop(Room room, IMeeting meeting, IWorkgroup workgroup);
}
