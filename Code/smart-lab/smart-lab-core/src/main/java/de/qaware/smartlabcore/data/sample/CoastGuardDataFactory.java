package de.qaware.smartlabcore.data.sample;

import de.qaware.smartlabcommons.Constants;
import de.qaware.smartlabcommons.data.device.AcmeDisplay;
import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcommons.data.meeting.AgendaItem;
import de.qaware.smartlabcommons.data.meeting.IAgendaItem;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.meeting.Meeting;
import de.qaware.smartlabcommons.data.meeting.assistance.AssistanceDao;
import de.qaware.smartlabcommons.data.meeting.assistance.IAssistanceDao;
import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcommons.data.person.Person;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.data.room.Room;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
@Slf4j
public class CoastGuardDataFactory extends AbstractSampleDataFactory {

    @Override
    public List<IWorkgroup> createWorkgroups() throws MalformedURLException {
        val workgroups = new ArrayList<IWorkgroup>();
        val coastGuardMembers = new ArrayList<Long>();
        coastGuardMembers.add(0L);
        coastGuardMembers.add(1L);
        coastGuardMembers.add(2L);
        workgroups.add(Workgroup.builder()
                .id(0)
                .name("Coast Guard")
                .memberIds(coastGuardMembers)
                .knowledgeBase(new URL("http", "coast-guard.com", 80, "/wiki"))
                .codeRepository(new URL("http", "coast-guard.com", 80, "/git"))
                .build());
        return workgroups;
    }

    @Override
    public List<IPerson> createWorkgroupMembers() {
        val workgroupMembers = new ArrayList<IPerson>();
        workgroupMembers.add(Person.builder()
                .id(0)
                .name("Coast Guard Alice")
                .email("alice@coast-guard.com")
                .build());
        workgroupMembers.add(Person.builder()
                .id(1)
                .name("Coast Guard Ben")
                .email("ben@coast-guard.com")
                .build());
        workgroupMembers.add(Person.builder()
                .id(2)
                .name("Coast Guard Charlie")
                .email("charlie@coast-guard.com")
                .build());
        return workgroupMembers;
    }

    @Override
    public List<IMeeting> createMeetings() {
        val meetings = new ArrayList<IMeeting>();
        val coastGuardMeetingAgenda = new ArrayList<IAgendaItem>();
        coastGuardMeetingAgenda.add(AgendaItem.builder().text("Show critical areas").build());
        coastGuardMeetingAgenda.add(AgendaItem.builder().text("Explain whale anatomy").build());
        coastGuardMeetingAgenda.add(AgendaItem.builder().text("Drink coffee").build());
        val coastGuardMeetingAssistances = new HashSet<IAssistanceDao>();
        coastGuardMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.MINUTE_TAKING).build());
        coastGuardMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.ROOM_UNLOCKING).build());
        meetings.add(Meeting.builder()
                .id(0)
                .title("Meeting about preventing illegal whale hunting")
                .workgroupId(0)
                .roomId(0)
                .agenda(coastGuardMeetingAgenda)
                .assistances(coastGuardMeetingAssistances)
                .start(Instant.now().plusSeconds(0))
                .end(Instant.now().plusSeconds(300)).build());
        return meetings;
    }

    @Override
    public List<IRoom> createRooms() {
        val rooms = new ArrayList<IRoom>();
        val blueRoomDevices = new ArrayList<Long>();
        blueRoomDevices.add(0L);
        rooms.add(Room.builder()
                .id(0)
                .name("Room Blue")
                .deviceIds(blueRoomDevices)
                .build());
        return rooms;
    }

    @Override
    public List<IDevice> createDevices() {
        val devices = new ArrayList<IDevice>();
        devices.add(AcmeDisplay.builder()
                .id(0)
                .name("Display in Room Blue")
                .dummyDisplayProperty("Dummy property of Display in Room Blue")
                .build());
        return devices;
    }
}
