package de.qaware.smartlabcore.data.context;

import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;

import java.util.Set;

public interface IAssistanceContextFactory {

    IAssistanceContext of(IAssistanceConfiguration config, IMeeting meeting);
    IAssistanceContext of(IAssistanceConfiguration config, IWorkgroup workgroup);
    IAssistanceContext of(IAssistanceConfiguration config, Set<IPerson> persons);
    IAssistanceContext of(IAssistanceConfiguration config, IRoom room);
}
