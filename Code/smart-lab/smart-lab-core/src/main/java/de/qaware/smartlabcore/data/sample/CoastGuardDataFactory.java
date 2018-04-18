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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Component
@Slf4j
public class CoastGuardDataFactory extends AbstractSampleDataFactory {

    public CoastGuardDataFactory() {
        super();
    }

    @Override
    public Map<Long, IWorkgroup> createWorkgroups() throws MalformedURLException {
        val workgroups = new HashMap<Long, IWorkgroup>();
        val coastGuardMembers = new ArrayList<Long>();
        coastGuardMembers.add(0L);
        coastGuardMembers.add(1L);
        coastGuardMembers.add(2L);
        workgroups.put(0L, Workgroup.builder()
                .id(0L)
                .name("Coast Guard")
                .memberIds(coastGuardMembers)
                .knowledgeBase(new URL("http", "coast-guard.com", 80, "/wiki"))
                .codeRepository(new URL("http", "coast-guard.com", 80, "/git"))
                .build());
        return workgroups;
    }

    @Override
    public Map<Long, IPerson> createWorkgroupMembers() {
        val workgroupMembers = new HashMap<Long, IPerson>();
        workgroupMembers.put(0L, Person.builder()
                .id(0L)
                .name("Coast Guard Alice")
                .email("alice@coast-guard.com")
                .build());
        workgroupMembers.put(1L, Person.builder()
                .id(1L)
                .name("Coast Guard Ben")
                .email("ben@coast-guard.com")
                .build());
        workgroupMembers.put(2L, Person.builder()
                .id(2L)
                .name("Coast Guard Charlie")
                .email("charlie@coast-guard.com")
                .build());
        return workgroupMembers;
    }

    @Override
    public Map<Long, IMeeting> createMeetings() {
        val meetings = new HashMap<Long, IMeeting>();
        val coastGuardMeetingAgenda = new ArrayList<IAgendaItem>();
        coastGuardMeetingAgenda.add(AgendaItem.builder().text("Show critical areas").build());
        coastGuardMeetingAgenda.add(AgendaItem.builder().text("Explain whale anatomy").build());
        coastGuardMeetingAgenda.add(AgendaItem.builder().text("Drink coffee").build());
        val coastGuardMeetingAssistances = new HashSet<IAssistanceDao>();
        coastGuardMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.MINUTE_TAKING).build());
        coastGuardMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.ROOM_UNLOCKING).build());
        meetings.put(0L, Meeting.builder()
                .id(0L)
                .title("Meeting about preventing illegal whale hunting")
                .workgroupId(0L)
                .roomId(0L)
                .agenda(coastGuardMeetingAgenda)
                .assistances(coastGuardMeetingAssistances)
                .start(timeBase.plusSeconds(0))
                .end(timeBase.plusSeconds(300)).build());
        return meetings;
    }

    @Override
    public Map<Long, IRoom> createRooms() {
        val rooms = new HashMap<Long, IRoom>();
        val blueRoomDevices = new ArrayList<Long>();
        blueRoomDevices.add(0L);
        rooms.put(0L, Room.builder()
                .id(0L)
                .name("Room Blue")
                .deviceIds(blueRoomDevices)
                .build());
        return rooms;
    }

    @Override
    public Map<Long, IDevice> createDevices() {
        val devices = new HashMap<Long, IDevice>();
        devices.put(0L, AcmeDisplay.builder()
                .id(0L)
                .name("Display in Room Blue")
                .dummyDisplayProperty("Dummy property of Display in Room Blue")
                .build());
        return devices;
    }
}
