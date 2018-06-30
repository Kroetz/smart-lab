package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabapi.service.meeting.IMeetingManagementService;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabmeeting.controller.MeetingManagementController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class MeetingManagementServiceMonolith extends AbstractBasicEntityManagementServiceMonolith<IMeeting, MeetingId> implements IMeetingManagementService {

    private final MeetingManagementController meetingManagementController;

    public MeetingManagementServiceMonolith(MeetingManagementController meetingManagementController) {
        super(meetingManagementController);
        this.meetingManagementController = meetingManagementController;
    }

    @Override
    public Set<IMeeting> findAll(RoomId roomId) {
        return this.meetingManagementController.findAll(roomId.getIdValue());
    }

    @Override
    public void shortenMeeting(MeetingId meetingId, Duration shortening) {
        this.meetingManagementController.shortenMeeting(
                meetingId.getIdValue(),
                shortening.toMinutes());
    }

    @Override
    public void extendMeeting(MeetingId meetingId, Duration extension) {
        this.meetingManagementController.extendMeeting(
                meetingId.getIdValue(),
                extension.toMinutes());
    }

    @Override
    public void shiftMeeting(MeetingId meetingId, Duration shift) {
        this.meetingManagementController.shiftMeeting(
                meetingId.getIdValue(),
                shift.toMinutes());
    }
}
