package de.qaware.smartlabcore.data.context;

import de.qaware.smartlabcore.data.assistance.IAssistance;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;

import java.util.Set;

public interface IContextFactory {

    IContext ofMeeting(IMeeting meeting);
    IContext ofMeetingAssistance(IMeeting meeting, IAssistance assistance);
    IContext ofWorkgroup(IWorkgroup workgroup);
    IContext ofPersons(Set<IPerson> persons);
    IContext ofRoom(IRoom room);
}
