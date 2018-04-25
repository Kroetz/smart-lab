package de.qaware.smartlabcommons.data.room;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlabcommons.data.IEntity;
import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;

import java.util.Collection;
import java.util.Optional;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Room.class)
})
public interface IRoom extends IEntity {

    String getName();
    Collection<String> getDeviceIds();

    void setUpMeeting(IMeeting meeting, IWorkgroup workgroup);
    void cleanUpMeeting(IMeeting meeting, IWorkgroup workgroup);
    void startMeeting(IMeeting meeting, IWorkgroup workgroup);
    void stopMeeting(IMeeting meeting, IWorkgroup workgroup);
    Optional<IDevice> getMinuteTakingDevice();
}
