package de.qaware.smartlabcore.data.context;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.miscellaneous.Constants;

import java.util.Optional;
import java.util.Set;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = Constants.JSON_TYPE_PROPERTY_NAME)
public interface IAssistanceContext {

    Optional<IMeeting> getMeeting();
    Optional<IWorkgroup> getWorkgroup();
    Optional<Set<IPerson>> getPersons();
    Optional<IRoom> getRoom();
    Optional<IAssistanceConfiguration> getAssistanceConfiguration();
}
