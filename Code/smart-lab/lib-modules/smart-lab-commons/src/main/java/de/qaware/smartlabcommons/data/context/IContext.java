package de.qaware.smartlabcommons.data.context;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlabcommons.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcommons.miscellaneous.Constants;

import java.util.Optional;
import java.util.Set;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = Constants.JSON_TYPE_PROPERTY_NAME)
public interface IContext {

    Optional<IMeeting> getMeeting();
    Optional<IWorkgroup> getWorkgroup();
    Optional<Set<IPerson>> getPersons();
    Optional<IRoom> getRoom();
    Optional<IAssistanceConfiguration> getAssistanceConfiguration();
}