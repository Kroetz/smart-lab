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
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class MeetingManagementServiceMonolith implements IMeetingManagementService {

    private final MeetingManagementController meetingManagementController;

    public MeetingManagementServiceMonolith(MeetingManagementController meetingManagementController) {
        this.meetingManagementController = meetingManagementController;
    }

    @Override
    public Map<RoomId, Set<IMeeting>> findAll() {
        return this.meetingManagementController.findAll();
    }

    @Override
    public Set<IMeeting> findAll(RoomId roomId) {
        return this.meetingManagementController.findAll(roomId.getIdValue());
    }

    @Override
    public IMeeting findOne(MeetingId meetingId, RoomId roomId) {
        return this.meetingManagementController.findOne(
                meetingId.getIdValue(),
                roomId.getIdValue()).getBody();
    }

    @Override
    public Set<IMeeting> findMultiple(MeetingId[] meetingIds, RoomId roomId) {
        return this.meetingManagementController.findMultiple(
                Arrays.stream(meetingIds).map(MeetingId::getIdValue).toArray(String[]::new),
                roomId.getIdValue())
                .getBody();
    }

    @Override
    public void create(IMeeting meeting) {
        this.meetingManagementController.create(meeting);
    }

    @Override
    public void delete(MeetingId entityId, RoomId roomId) {
        this.meetingManagementController.delete(
                entityId.getIdValue(),
                roomId.getIdValue());
    }

    @Override
    public void shortenMeeting(MeetingId meetingId, RoomId roomId, Duration shortening) {
        this.meetingManagementController.shortenMeeting(
                meetingId.getIdValue(),
                roomId.getIdValue(),
                shortening.toMinutes());
    }

    @Override
    public void extendMeeting(MeetingId meetingId, RoomId roomId, Duration extension) {
        this.meetingManagementController.extendMeeting(
                meetingId.getIdValue(),
                roomId.getIdValue(),
                extension.toMinutes());
    }

    @Override
    public void shiftMeeting(MeetingId meetingId, RoomId roomId, Duration shift) {
        this.meetingManagementController.shiftMeeting(
                meetingId.getIdValue(),
                roomId.getIdValue(),
                shift.toMinutes());
    }
}
