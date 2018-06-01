package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabapi.service.meeting.IMeetingManagementService;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.miscellaneous.Property;
import de.qaware.smartlabmeeting.controller.MeetingManagementController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class MeetingManagementServiceMonolith extends AbstractEntityManagementServiceMonolith<IMeeting> implements IMeetingManagementService {

    private final MeetingManagementController meetingManagementController;

    public MeetingManagementServiceMonolith(MeetingManagementController meetingManagementController) {
        super(meetingManagementController);
        this.meetingManagementController = meetingManagementController;
    }

    @Override
    public void shortenMeeting(String meetingId, Duration shortening) {
        this.meetingManagementController.shortenMeeting(meetingId, shortening.toMinutes());
    }

    @Override
    public void extendMeeting(String meetingId, Duration extension) {
        this.meetingManagementController.extendMeeting(meetingId, extension.toMinutes());
    }

    @Override
    public void shiftMeeting(String meetingId, Duration shift) {
        this.meetingManagementController.shiftMeeting(meetingId, shift.toMinutes());
    }
}
