package de.qaware.smartlabcore.meeting;

import de.qaware.smartlabcore.entity.meeting.AgendaItem;
import de.qaware.smartlabcore.entity.meeting.IAgendaItem;
import de.qaware.smartlabcore.entity.meeting.IMeeting;
import de.qaware.smartlabcore.entity.meeting.Meeting;
import de.qaware.smartlabcore.entity.meeting.assistance.AssistanceDao;
import de.qaware.smartlabcore.entity.meeting.assistance.IAssistanceDao;
import de.qaware.smartlabcore.miscellaneous.Constants;
import lombok.val;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;

public class MeetingFactory {

    public static IMeeting getCoastGuardMeeting() {

        val coastGuardMeetingAgenda = new ArrayList<IAgendaItem>();
        coastGuardMeetingAgenda.add(AgendaItem.builder().text("Show critical areas").build());
        coastGuardMeetingAgenda.add(AgendaItem.builder().text("Explain whale anatomy").build());
        coastGuardMeetingAgenda.add(AgendaItem.builder().text("Drink coffee").build());
        val coastGuardMeetingAssistances = new HashSet<IAssistanceDao>();
        coastGuardMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.MINUTE_TAKING).build());
        coastGuardMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.ROOM_UNLOCKING).build());
        return Meeting.builder()
                .type("de.qaware.smartlabcore.entity.meeting.Meeting")
                .id(0)
                .title("Meeting about preventing illegal whale hunting")
                .workgroupId(0)
                .roomId(0)
                .agenda(coastGuardMeetingAgenda)
                .assistances(coastGuardMeetingAssistances)
                .start(Instant.now().plusSeconds(0))
                .end(Instant.now().plusSeconds(300)).build();
    }

    public static IMeeting getForestRangersMeeting() {
        val forestRangersMeetingAgenda = new ArrayList<IAgendaItem>();
        forestRangersMeetingAgenda.add(AgendaItem.builder().text("Show potential damage").build());
        forestRangersMeetingAgenda.add(AgendaItem.builder().text("Show increase in population").build());
        forestRangersMeetingAgenda.add(AgendaItem.builder().text("Laugh together").build());
        val forestRangersMeetingAssistances = new HashSet<IAssistanceDao>();
        forestRangersMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.MINUTE_TAKING).build());
        forestRangersMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.ROOM_UNLOCKING).build());
        return Meeting.builder()
                .type("de.qaware.smartlabcore.entity.meeting.Meeting")
                .id(1)
                .title("Meeting about the danger of the bark beetle")
                .workgroupId(1)
                .roomId(1)
                .agenda(forestRangersMeetingAgenda)
                .assistances(forestRangersMeetingAssistances)
                .start(Instant.now().plusSeconds(60))
                .end(Instant.now().plusSeconds(360)).build();
    }

    public static IMeeting getFireFightersMeeting() {
        val fireFightersMeetingAgenda = new ArrayList<IAgendaItem>();
        fireFightersMeetingAgenda.add(AgendaItem.builder().text("Show how bad the old truck is").build());
        fireFightersMeetingAgenda.add(AgendaItem.builder().text("Show how great the new truck is").build());
        fireFightersMeetingAgenda.add(AgendaItem.builder().text("Discuss how to pay for the new truck").build());
        val fireFightersMeetingAssistances = new HashSet<IAssistanceDao>();
        fireFightersMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.MINUTE_TAKING).build());
        fireFightersMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.ROOM_UNLOCKING).build());
        return Meeting.builder()
                .type("de.qaware.smartlabcore.entity.meeting.Meeting")
                .id(2)
                .title("Meeting about the new fire truck \"Fire Exterminator 3000\"")
                .workgroupId(2)
                .roomId(2)
                .agenda(fireFightersMeetingAgenda)
                .assistances(fireFightersMeetingAssistances)
                .start(Instant.now().plusSeconds(120))
                .end(Instant.now().plusSeconds(420)).build();
    }
}
