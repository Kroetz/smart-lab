package de.qaware.smartlabcore.data.sample;

import de.qaware.smartlabcommons.Constants;
import de.qaware.smartlabcommons.data.device.AcmeDisplay;
import de.qaware.smartlabcommons.data.device.AcmeMicrophone;
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
public class FireFightersDataFactory extends AbstractSampleDataFactory {

    public FireFightersDataFactory() {
        super();
    }

    @Override
    public Map<Long, IWorkgroup> createWorkgroups() throws MalformedURLException {
        val workgroups = new HashMap<Long, IWorkgroup>();
        val fireFighterMembers = new ArrayList<Long>();
        fireFighterMembers.add(6L);
        fireFighterMembers.add(7L);
        fireFighterMembers.add(8L);
        workgroups.put(2L, Workgroup.builder()
                .id(2L)
                .name("Fire Fighters")
                .memberIds(fireFighterMembers)
                .knowledgeBase(new URL("http", "fire-fighters.com", 80, "/wiki"))
                .codeRepository(new URL("http", "fire-fighters.com", 80, "/git"))
                .build());
        return workgroups;
    }

    @Override
    public Map<Long, IPerson> createWorkgroupMembers() {
        val workgroupMembers = new HashMap<Long, IPerson>();
        workgroupMembers.put(6L, Person.builder()
                .id(6L)
                .name("Fire Fighter Anthony")
                .email("anthony@fire-fighters.com")
                .build());
        workgroupMembers.put(7L, Person.builder()
                .id(7L)
                .name("Fire Fighter Bruce")
                .email("bruce@fire-fighters.com")
                .build());
        workgroupMembers.put(8L, Person.builder()
                .id(8L)
                .name("Fire Fighter Carlos")
                .email("carlos@fire-fighters.com")
                .build());
        return workgroupMembers;
    }

    @Override
    public Map<Long, IMeeting> createMeetings() {
        val meetings = new HashMap<Long, IMeeting>();
        val fireFightersMeetingAgenda = new ArrayList<IAgendaItem>();
        fireFightersMeetingAgenda.add(AgendaItem.builder().text("Show how bad the old truck is").build());
        fireFightersMeetingAgenda.add(AgendaItem.builder().text("Show how great the new truck is").build());
        fireFightersMeetingAgenda.add(AgendaItem.builder().text("Discuss how to pay for the new truck").build());
        val fireFightersMeetingAssistances = new HashSet<IAssistanceDao>();
        fireFightersMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.MINUTE_TAKING).build());
        fireFightersMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.ROOM_UNLOCKING).build());
        meetings.put(2L, Meeting.builder()
                .id(2L)
                .title("Meeting about the new fire truck \"Fire Exterminator 3000\"")
                .workgroupId(2L)
                .roomId(2L)
                .agenda(fireFightersMeetingAgenda)
                .assistances(fireFightersMeetingAssistances)
                .start(timeBase.plusSeconds(120))
                .end(timeBase.plusSeconds(420)).build());
        return meetings;
    }

    @Override
    public Map<Long, IRoom> createRooms() {
        val rooms = new HashMap<Long, IRoom>();
        val redRoomDevices = new ArrayList<Long>();
        redRoomDevices.add(2L);
        redRoomDevices.add(3L);
        rooms.put(2L, Room.builder()
                .id(2L)
                .name("Room Red")
                .deviceIds(redRoomDevices)
                .build());
        return rooms;
    }

    @Override
    public Map<Long, IDevice> createDevices() {
        val devices = new HashMap<Long, IDevice>();
        devices.put(2L, AcmeDisplay.builder()
                .id(2L)
                .name("Display in Room Red")
                .dummyDisplayProperty("Dummy property of Display in Room Red")
                .build());
        devices.put(3L, AcmeMicrophone.builder()
                .id(3L)
                .name("Microphone in Room Red")
                .dummyMicrophoneProperty("Dummy property of Microphone in Room Red")
                .build());
        return devices;
    }
}
