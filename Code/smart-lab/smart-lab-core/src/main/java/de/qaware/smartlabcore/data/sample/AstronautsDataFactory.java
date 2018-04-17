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
public class AstronautsDataFactory extends AbstractSampleDataFactory {

    @Override
    public List<IWorkgroup> createWorkgroups() throws MalformedURLException {
        val workgroups = new ArrayList<IWorkgroup>();
        val astronautsMembers = new ArrayList<Long>();
        astronautsMembers.add(9L);
        astronautsMembers.add(10L);
        astronautsMembers.add(11L);
        workgroups.add(Workgroup.builder()
                .id(3)
                .name("Astronauts")
                .memberIds(astronautsMembers)
                .knowledgeBase(new URL("http", "astronauts.com", 80, "/wiki"))
                .codeRepository(new URL("http", "astronauts.com", 80, "/git"))
                .build());
        return workgroups;
    }

    @Override
    public List<IPerson> createWorkgroupMembers() {
        val workgroupMembers = new ArrayList<IPerson>();
        workgroupMembers.add(Person.builder()
                .id(9)
                .name("Astronaut Alex")
                .email("alex@astronauts.com")
                .build());
        workgroupMembers.add(Person.builder()
                .id(10)
                .name("Austronaut Beverly")
                .email("beverly@astronauts.com")
                .build());
        workgroupMembers.add(Person.builder()
                .id(11)
                .name("Astronaut Charlotte")
                .email("charlotte@astronauts.com")
                .build());
        return workgroupMembers;
    }

    @Override
    public List<IMeeting> createMeetings() {
        val meetings = new ArrayList<IMeeting>();
        val astronautsMeetingAgenda = new ArrayList<IAgendaItem>();
        astronautsMeetingAgenda.add(AgendaItem.builder().text("Calculate journey duration").build());
        astronautsMeetingAgenda.add(AgendaItem.builder().text("Discuss who may press the launch button of the rocket").build());
        astronautsMeetingAgenda.add(AgendaItem.builder().text("Complain that this is all rocket science").build());
        val astronautsMeetingAssistances = new HashSet<IAssistanceDao>();
        astronautsMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.MINUTE_TAKING).build());
        astronautsMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.ROOM_UNLOCKING).build());
        meetings.add(Meeting.builder()
                .id(3)
                .title("Meeting about travelling to Mars")
                .workgroupId(3)
                .roomId(3)
                .agenda(astronautsMeetingAgenda)
                .assistances(astronautsMeetingAssistances)
                .start(Instant.now().plusSeconds(0))
                .end(Instant.now().plusSeconds(300)).build());
        return meetings;
    }

    @Override
    public List<IRoom> createRooms() {
        val rooms = new ArrayList<IRoom>();
        val blackRoomDevices = new ArrayList<Long>();
        blackRoomDevices.add(4L);
        rooms.add(Room.builder()
                .id(3)
                .name("Room Black")
                .deviceIds(blackRoomDevices)
                .build());
        return rooms;
    }

    @Override
    public List<IDevice> createDevices() {
        val devices = new ArrayList<IDevice>();
        devices.add(AcmeDisplay.builder()
                .id(4)
                .name("Display in Room Black")
                .dummyDisplayProperty("Dummy property of Display in Room Black")
                .build());
        return devices;
    }
}
