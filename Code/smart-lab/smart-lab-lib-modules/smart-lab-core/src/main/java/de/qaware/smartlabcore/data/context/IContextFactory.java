package de.qaware.smartlabcore.data.context;

import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;

import java.util.Set;

public interface IContextFactory {

    IContext of(IMeeting meeting, IAssistanceConfiguration config);
    IContext of(IMeeting meeting);
    IContext of(IWorkgroup workgroup);
    IContext of(Set<IPerson> persons);
    IContext of(IRoom room);
}
