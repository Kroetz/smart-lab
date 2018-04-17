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
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
@Slf4j
public class FireFightersDataFactory extends AbstractSampleDataFactory {

    @Override
    public List<IWorkgroup> createWorkgroups() throws MalformedURLException {
        val workgroups = new ArrayList<IWorkgroup>();
        val fireFighterMembers = new ArrayList<Long>();
        fireFighterMembers.add(6L);
        fireFighterMembers.add(7L);
        fireFighterMembers.add(8L);
        workgroups.add(Workgroup.builder()
                .id(2)
                .name("Fire Fighters")
                .memberIds(fireFighterMembers)
                .knowledgeBase(new URL("http", "fire-fighters.com", 80, "/wiki"))
                .codeRepository(new URL("http", "fire-fighters.com", 80, "/git"))
                .build());
        return workgroups;
    }

    @Override
    public List<IPerson> createWorkgroupMembers() {
        val workgroupMembers = new ArrayList<IPerson>();
        workgroupMembers.add(Person.builder()
                .id(6)
                .name("Fire Fighter Anthony")
                .email("anthony@fire-fighters.com")
                .build());
        workgroupMembers.add(Person.builder()
                .id(7)
                .name("Fire Fighter Bruce")
                .email("bruce@fire-fighters.com")
                .build());
        workgroupMembers.add(Person.builder()
                .id(8)
                .name("Fire Fighter Carlos")
                .email("carlos@fire-fighters.com")
                .build());
        return workgroupMembers;
    }

    @Override
    public List<IMeeting> createMeetings() {
        val meetings = new ArrayList<IMeeting>();
        val fireFightersMeetingAgenda = new ArrayList<IAgendaItem>();
        fireFightersMeetingAgenda.add(AgendaItem.builder().text("Show how bad the old truck is").build());
        fireFightersMeetingAgenda.add(AgendaItem.builder().text("Show how great the new truck is").build());
        fireFightersMeetingAgenda.add(AgendaItem.builder().text("Discuss how to pay for the new truck").build());
        val fireFightersMeetingAssistances = new HashSet<IAssistanceDao>();
        fireFightersMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.MINUTE_TAKING).build());
        fireFightersMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.ROOM_UNLOCKING).build());
        meetings.add(Meeting.builder()
                .id(2)
                .title("Meeting about the new fire truck \"Fire Exterminator 3000\"")
                .workgroupId(2)
                .roomId(2)
                .agenda(fireFightersMeetingAgenda)
                .assistances(fireFightersMeetingAssistances)
                .start(Instant.now().plusSeconds(120))
                .end(Instant.now().plusSeconds(420)).build());
        return meetings;
    }

    @Override
    public List<IRoom> createRooms() {
        val rooms = new ArrayList<IRoom>();
        val redRoomDevices = new ArrayList<Long>();
        redRoomDevices.add(2L);
        redRoomDevices.add(3L);
        rooms.add(Room.builder()
                .id(2)
                .name("Room Red")
                .deviceIds(redRoomDevices)
                .build());
        return rooms;
    }

    @Override
    public List<IDevice> createDevices() {
        val devices = new ArrayList<IDevice>();
        devices.add(AcmeDisplay.builder()
                .id(2)
                .name("Display in Room Red")
                .dummyDisplayProperty("Dummy property of Display in Room Red")
                .build());
        devices.add(AcmeMicrophone.builder()
                .id(3)
                .name("Microphone in Room Red")
                .dummyMicrophoneProperty("Dummy property of Microphone in Room Red")
                .build());
        return devices;
    }
}
