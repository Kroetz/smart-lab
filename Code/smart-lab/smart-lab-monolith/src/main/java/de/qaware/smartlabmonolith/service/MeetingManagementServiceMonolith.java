package de.qaware.smartlabmonolith.service;

import de.qaware.smartlabcommons.api.service.meeting.IMeetingManagementService;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.miscellaneous.Constants;
import de.qaware.smartlabmeeting.controller.MeetingManagementController;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@Profile(Constants.PROFILE_NAME_MONOLITH)
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
