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
public class AstronautsDataFactory extends AbstractSampleDataFactory {

    public AstronautsDataFactory() {
        super();
    }

    @Override
    public Map<Long, IWorkgroup> createWorkgroups() throws MalformedURLException {
        val workgroups = new HashMap<Long, IWorkgroup>();
        val astronautsMembers = new ArrayList<Long>();
        astronautsMembers.add(9L);
        astronautsMembers.add(10L);
        astronautsMembers.add(11L);
        workgroups.put(3L, Workgroup.builder()
                .id(3L)
                .name("Astronauts")
                .memberIds(astronautsMembers)
                .knowledgeBase(new URL("http", "astronauts.com", 80, "/wiki"))
                .codeRepository(new URL("http", "astronauts.com", 80, "/git"))
                .build());
        return workgroups;
    }

    @Override
    public Map<Long, IPerson> createWorkgroupMembers() {
        val workgroupMembers = new HashMap<Long, IPerson>();
        workgroupMembers.put(9L, Person.builder()
                .id(9L)
                .name("Astronaut Alex")
                .email("alex@astronauts.com")
                .build());
        workgroupMembers.put(10L, Person.builder()
                .id(10L)
                .name("Austronaut Beverly")
                .email("beverly@astronauts.com")
                .build());
        workgroupMembers.put(11L, Person.builder()
                .id(11L)
                .name("Astronaut Charlotte")
                .email("charlotte@astronauts.com")
                .build());
        return workgroupMembers;
    }

    @Override
    public Map<Long, IMeeting> createMeetings() {
        val meetings = new HashMap<Long, IMeeting>();
        val astronautsMeetingAgenda = new ArrayList<IAgendaItem>();
        astronautsMeetingAgenda.add(AgendaItem.builder().text("Calculate journey duration").build());
        astronautsMeetingAgenda.add(AgendaItem.builder().text("Discuss who may press the launch button of the rocket").build());
        astronautsMeetingAgenda.add(AgendaItem.builder().text("Complain that this is all rocket science").build());
        val astronautsMeetingAssistances = new HashSet<IAssistanceDao>();
        astronautsMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.MINUTE_TAKING).build());
        astronautsMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.ROOM_UNLOCKING).build());
        meetings.put(3L, Meeting.builder()
                .id(3L)
                .title("Meeting about travelling to Mars")
                .workgroupId(3L)
                .roomId(3L)
                .agenda(astronautsMeetingAgenda)
                .assistances(astronautsMeetingAssistances)
                .start(timeBase.plusSeconds(0))
                .end(timeBase.plusSeconds(300)).build());
        return meetings;
    }

    @Override
    public Map<Long, IRoom> createRooms() {
        val rooms = new HashMap<Long, IRoom>();
        val blackRoomDevices = new ArrayList<Long>();
        blackRoomDevices.add(4L);
        rooms.put(3L, Room.builder()
                .id(3L)
                .name("Room Black")
                .deviceIds(blackRoomDevices)
                .build());
        return rooms;
    }

    @Override
    public Map<Long, IDevice> createDevices() {
        val devices = new HashMap<Long, IDevice>();
        devices.put(4L, AcmeDisplay.builder()
                .id(4L)
                .name("Display in Room Black")
                .dummyDisplayProperty("Dummy property of Display in Room Black")
                .build());
        return devices;
    }
}
