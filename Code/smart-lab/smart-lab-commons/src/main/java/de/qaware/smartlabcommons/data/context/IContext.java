package de.qaware.smartlabcommons.data.context;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;

import java.util.Optional;
import java.util.Set;

public interface IContext {

    Optional<IMeeting> getMeeting();
    Optional<IWorkgroup> getWorkgroup();
    Optional<Set<IPerson>> getPersons();
    Optional<IRoom> getRoom();
}
