package de.qaware.smartlabcore.data.context;

import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;

import java.util.Set;

public interface IContextFactory {

    IAssistanceContext of(IMeeting meeting, IAssistanceConfiguration config);
    IAssistanceContext of(IMeeting meeting);
    IAssistanceContext of(IWorkgroup workgroup);
    IAssistanceContext of(Set<IPerson> persons);
    IAssistanceContext of(IRoom room);
}
