package de.qaware.smartlabcommons.data.room;

import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import lombok.*;

import java.util.Collection;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room implements IRoom {

    private String id;
    private String name;
    private Collection<String> deviceIds;
    private Optional<IDevice> minuteTakingDevice;

    public void setUpMeeting(IMeeting meeting, IWorkgroup workgroup) {
        for(val assistance : meeting.getAssistances()) {
            assistance.toAssistance().ifPresent(a -> a.setUp(this, meeting, workgroup));
        }
    }

    public void cleanUpMeeting(IMeeting meeting, IWorkgroup workgroup) {
        for(val assistance : meeting.getAssistances()) {
            assistance.toAssistance().ifPresent(a -> a.cleanUp(this, meeting, workgroup));
        }
    }

    public void startMeeting(IMeeting meeting, IWorkgroup workgroup) {
        for(val assistance : meeting.getAssistances()) {
            assistance.toAssistance().ifPresent(a -> a.start(this, meeting, workgroup));
        }
    }

    public void stopMeeting(IMeeting meeting, IWorkgroup workgroup) {
        for(val assistance : meeting.getAssistances()) {
            assistance.toAssistance().ifPresent(a -> a.stop(this, meeting, workgroup));
        }
    }

    public Optional<IDevice> getMinuteTakingDevice() {
        return minuteTakingDevice;
    }

    // This small piece of a builder is needed to tell Lombok about default values (see https://reinhard.codes/2016/07/13/using-lomboks-builder-annotation-with-default-values/)
    public static class RoomBuilder {
        private Optional<IDevice> minuteTakingDevice = Optional.empty();
    }
}
