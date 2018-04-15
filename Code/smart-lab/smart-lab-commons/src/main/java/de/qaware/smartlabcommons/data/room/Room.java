package de.qaware.smartlabcommons.data.room;

import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import lombok.*;

import java.util.Collection;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    private long id;
    private String name;
    private Collection<Long> deviceIds;

    private Optional<IDevice> minuteTakingDevice;

    public void setUpMeeting(IMeeting meeting, Workgroup workgroup) {
        for(val assistance : meeting.getAssistances()) {
            assistance.toAssistance().ifPresent(a -> a.setUp(this, meeting, workgroup));
        }
    }

    public void cleanUpMeeting(IMeeting meeting, Workgroup workgroup) {
        for(val assistance : meeting.getAssistances()) {
            assistance.toAssistance().ifPresent(a -> a.cleanUp(this, meeting, workgroup));
        }
    }

    public void startMeeting(IMeeting meeting, Workgroup workgroup) {
        for(val assistance : meeting.getAssistances()) {
            assistance.toAssistance().ifPresent(a -> a.start(this, meeting, workgroup));
        }
    }

    public void stopMeeting(IMeeting meeting, Workgroup workgroup) {
        for(val assistance : meeting.getAssistances()) {
            assistance.toAssistance().ifPresent(a -> a.stop(this, meeting, workgroup));
        }
    }

    public Optional<IDevice> getMinuteTakingDevice() {
        return minuteTakingDevice;
    }
}
