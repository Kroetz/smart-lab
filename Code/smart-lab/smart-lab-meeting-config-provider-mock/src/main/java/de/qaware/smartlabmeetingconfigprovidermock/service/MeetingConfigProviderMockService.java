package de.qaware.smartlabmeetingconfigprovidermock.service;

import de.qaware.smartlabcommons.data.meeting.AgendaItem;
import de.qaware.smartlabcommons.data.meeting.IAgendaItem;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.meeting.Meeting;
import de.qaware.smartlabcommons.data.meeting.assistance.AssistanceDao;
import de.qaware.smartlabcommons.data.meeting.assistance.IAssistanceDao;
import de.qaware.smartlabcommons.Constants;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MeetingConfigProviderMockService implements IMeetingConfigProviderMockService {

    private List<IMeeting> meetings;

    public MeetingConfigProviderMockService() {
        this.meetings = new ArrayList<>();

        val coastGuardMeetingAgenda = new ArrayList<IAgendaItem>();
        coastGuardMeetingAgenda.add(AgendaItem.builder().text("Show critical areas").build());
        coastGuardMeetingAgenda.add(AgendaItem.builder().text("Explain whale anatomy").build());
        coastGuardMeetingAgenda.add(AgendaItem.builder().text("Drink coffee").build());
        val coastGuardMeetingAssistances = new HashSet<IAssistanceDao>();
        coastGuardMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.MINUTE_TAKING).build());
        coastGuardMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.ROOM_UNLOCKING).build());
        meetings.add(Meeting.builder()
                .type("de.qaware.smartlabcommons.data.meeting.Meeting")
                .id(0)
                .title("Meeting about preventing illegal whale hunting")
                .workgroupId(0)
                .roomId(0)
                .agenda(coastGuardMeetingAgenda)
                .assistances(coastGuardMeetingAssistances)
                .start(Instant.now().plusSeconds(0))
                .end(Instant.now().plusSeconds(300)).build());

        val forestRangersMeetingAgenda = new ArrayList<IAgendaItem>();
        forestRangersMeetingAgenda.add(AgendaItem.builder().text("Show potential damage").build());
        forestRangersMeetingAgenda.add(AgendaItem.builder().text("Show increase in population").build());
        forestRangersMeetingAgenda.add(AgendaItem.builder().text("Laugh together").build());
        val forestRangersMeetingAssistances = new HashSet<IAssistanceDao>();
        forestRangersMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.MINUTE_TAKING).build());
        forestRangersMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.ROOM_UNLOCKING).build());
        meetings.add(Meeting.builder()
                .type("de.qaware.smartlabcommons.data.meeting.Meeting")
                .id(1)
                .title("Meeting about the danger of the bark beetle")
                .workgroupId(1)
                .roomId(1)
                .agenda(forestRangersMeetingAgenda)
                .assistances(forestRangersMeetingAssistances)
                .start(Instant.now().plusSeconds(60))
                .end(Instant.now().plusSeconds(360)).build());

        val fireFightersMeetingAgenda = new ArrayList<IAgendaItem>();
        fireFightersMeetingAgenda.add(AgendaItem.builder().text("Show how bad the old truck is").build());
        fireFightersMeetingAgenda.add(AgendaItem.builder().text("Show how great the new truck is").build());
        fireFightersMeetingAgenda.add(AgendaItem.builder().text("Discuss how to pay for the new truck").build());
        val fireFightersMeetingAssistances = new HashSet<IAssistanceDao>();
        fireFightersMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.MINUTE_TAKING).build());
        fireFightersMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.ROOM_UNLOCKING).build());
        meetings.add(Meeting.builder()
                .type("de.qaware.smartlabcommons.data.meeting.Meeting")
                .id(2)
                .title("Meeting about the new fire truck \"Fire Exterminator 3000\"")
                .workgroupId(2)
                .roomId(2)
                .agenda(fireFightersMeetingAgenda)
                .assistances(fireFightersMeetingAssistances)
                .start(Instant.now().plusSeconds(120))
                .end(Instant.now().plusSeconds(420)).build());

        sortMeetingsByStart();
    }

    private boolean exists(long meetingId) {
        return meetings.stream().anyMatch(m -> m.getId() == meetingId);
    }

    @Override
    public List<IMeeting> getMeetings() {
        return meetings;
    }

    @Override
    public Optional<IMeeting> getMeeting(long meetingId) {
        return meetings.stream()
                .filter(m -> m.getId() == meetingId)
                .findFirst();
    }

    @Override
    public boolean createMeeting(IMeeting meeting) {
        val meetingCollision = getMeetings().stream().anyMatch(m -> areMeetingsColliding(meeting, m));
        if(meetingCollision || exists(meeting.getId())) {
            return false;
        }
        meetings.add(meeting);
        sortMeetingsByStart();
        return true;
    }

    @Override
    public boolean deleteMeeting(long meetingId) {
        return meetings.removeAll(meetings.stream()
                .filter(meeting -> meeting.getId() == meetingId)
                .collect(Collectors.toList()));
    }

    @Override
    public void shortenMeeting(long meetingId, Duration shortening) {
        val meeting = getMeeting(meetingId);
        if(meeting.isPresent()) {
            if(deleteMeeting(meetingId)) {
                val shortenedMeeting = meeting.get().copy();
                shortenedMeeting.setEnd(meeting.get().getEnd().minus(shortening));
                createMeeting(shortenedMeeting);
            }
        }
    }

    @Override
    public boolean extendMeeting(long meetingId, Duration extension) {
        val meeting = getMeeting(meetingId);
        if(meeting.isPresent()) {
            val extendedMeeting = meeting.get().copy();
            extendedMeeting.setEnd(meeting.get().getEnd().plus(extension));
            deleteMeeting(meetingId);
            if(createMeeting(extendedMeeting)) {
                return true;
            }
            else {
                createMeeting(meeting.get());
            }
        }
        return false;
    }

    @Override
    public boolean shiftMeeting(long meetingId, Duration shift) {
        val meeting = getMeeting(meetingId);
        if(meeting.isPresent()) {
            val shiftedMeeting = meeting.get().copy();
            shiftedMeeting.setStart(meeting.get().getStart().plus(shift));
            shiftedMeeting.setEnd(meeting.get().getEnd().plus(shift));
            deleteMeeting(meetingId);
            if(createMeeting(shiftedMeeting)) {
                return true;
            }
            else {
                createMeeting(meeting.get());
            }
        }
        return false;
    }

    private void sortMeetingsByStart() {
        meetings.sort((m1, m2) -> {
            if(m1.getStart().isBefore(m2.getStart())) {
                return -1;
            }
            if(m1.getStart().isAfter(m2.getStart())) {
                return 1;
            }
            return 0;
        });
    }

    private boolean areMeetingsColliding(IMeeting m1, IMeeting m2) {
        return (m1.getRoomId() == m2.getRoomId()
                && (m1.getStart().equals(m2.getStart()) && m1.getEnd().equals(m2.getEnd())
                    || m1.getStart().isAfter(m2.getStart()) && m1.getStart().isBefore(m2.getEnd())
                    || m1.getEnd().isAfter(m2.getStart()) && m1.getEnd().isBefore(m2.getEnd())
                    || m2.getStart().isAfter(m1.getStart()) && m2.getStart().isBefore(m1.getEnd())
                    || m2.getEnd().isAfter(m1.getStart()) && m2.getEnd().isBefore(m1.getEnd())));
    }
}
