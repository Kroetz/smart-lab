package de.qaware.smartlabcommons.data.context;

import de.qaware.smartlabcommons.data.assistance.IAssistance;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;

import java.util.Set;

public interface IContextFactory {

    IContext ofMeeting(IMeeting meeting);
    IContext ofMeetingAssistance(IMeeting meeting, IAssistance assistance);
    IContext ofWorkgroup(IWorkgroup workgroup);
    IContext ofPersons(Set<IPerson> persons);
    IContext ofRoom(IRoom room);
}
