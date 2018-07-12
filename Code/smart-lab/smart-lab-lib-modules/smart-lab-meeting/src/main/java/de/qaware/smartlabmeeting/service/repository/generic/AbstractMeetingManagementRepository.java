package de.qaware.smartlabmeeting.service.repository.generic;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.service.repository.AbstractBasicEntityManagementRepositoryMock;
import de.qaware.smartlabmeeting.service.repository.IMeetingManagementRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public abstract class AbstractMeetingManagementRepository extends AbstractBasicEntityManagementRepositoryMock<IMeeting, MeetingId> implements IMeetingManagementRepository {

    public AbstractMeetingManagementRepository(Set<IMeeting> initialMeetings) {
        super(initialMeetings);
    }

    protected boolean exists(MeetingId meetingId) {
        return findOne(meetingId).isPresent();
    }

    protected boolean areMeetingsColliding(IMeeting m1, IMeeting m2) {
        return (m1.getRoomId().equals(m2.getRoomId())
                && (m1.getStart().equals(m2.getStart()) && m1.getEnd().equals(m2.getEnd())
                || m1.getStart().isAfter(m2.getStart()) && m1.getStart().isBefore(m2.getEnd())
                || m1.getEnd().isAfter(m2.getStart()) && m1.getEnd().isBefore(m2.getEnd())
                || m2.getStart().isAfter(m1.getStart()) && m2.getStart().isBefore(m1.getEnd())
                || m2.getEnd().isAfter(m1.getStart()) && m2.getEnd().isBefore(m1.getEnd())));
    }
}
