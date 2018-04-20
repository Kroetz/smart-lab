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

    public static final String WORKGROUP_ID_ASTRONAUTS = "astronauts";
    public static final String MEMBER_ID_ALEX = "alex";
    public static final String MEMBER_ID_BEVERLY = "beverly";
    public static final String MEMBER_ID_CHARLOTTE = "charlotte";
    public static final String MEETING_ID_MARS = "mars";
    public static final String ROOM_ID_BLACK = "black";
    public static final String DEVICE_ID_BLACK_DISPLAY = "black-display";

    public AstronautsDataFactory() {
        super();
    }

    @Override
    public Map<String, IWorkgroup> createWorkgroups() throws MalformedURLException {
        val workgroups = new HashMap<String, IWorkgroup>();
        val astronautsMembers = new ArrayList<String>();
        astronautsMembers.add(MEMBER_ID_ALEX);
        astronautsMembers.add(MEMBER_ID_BEVERLY);
        astronautsMembers.add(MEMBER_ID_CHARLOTTE);
        workgroups.put(WORKGROUP_ID_ASTRONAUTS, Workgroup.builder()
                .id(WORKGROUP_ID_ASTRONAUTS)
                .name("Astronauts")
                .memberIds(astronautsMembers)
                .knowledgeBase(new URL("http", "astronauts.com", 80, "/wiki"))
                .codeRepository(new URL("http", "astronauts.com", 80, "/git"))
                .build());
        return workgroups;
    }

    @Override
    public Map<String, IPerson> createWorkgroupMembers() {
        val workgroupMembers = new HashMap<String, IPerson>();
        workgroupMembers.put(MEMBER_ID_ALEX, Person.builder()
                .id(MEMBER_ID_ALEX)
                .name("Astronaut Alex")
                .email("alex@astronauts.com")
                .build());
        workgroupMembers.put(MEMBER_ID_BEVERLY, Person.builder()
                .id(MEMBER_ID_BEVERLY)
                .name("Austronaut Beverly")
                .email("beverly@astronauts.com")
                .build());
        workgroupMembers.put(MEMBER_ID_CHARLOTTE, Person.builder()
                .id(MEMBER_ID_CHARLOTTE)
                .name("Astronaut Charlotte")
                .email("charlotte@astronauts.com")
                .build());
        return workgroupMembers;
    }

    @Override
    public Map<String, IMeeting> createMeetings() {
        val meetings = new HashMap<String, IMeeting>();
        val astronautsMeetingAgenda = new ArrayList<IAgendaItem>();
        astronautsMeetingAgenda.add(AgendaItem.builder().text("Calculate journey duration").build());
        astronautsMeetingAgenda.add(AgendaItem.builder().text("Discuss who may press the launch button of the rocket").build());
        astronautsMeetingAgenda.add(AgendaItem.builder().text("Complain that this is all rocket science").build());
        val astronautsMeetingAssistances = new HashSet<IAssistanceDao>();
        astronautsMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.MINUTE_TAKING).build());
        astronautsMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.ROOM_UNLOCKING).build());
        meetings.put(MEETING_ID_MARS, Meeting.builder()
                .id(MEETING_ID_MARS)
                .title("Meeting about travelling to Mars")
                .workgroupId(WORKGROUP_ID_ASTRONAUTS)
                .roomId(ROOM_ID_BLACK)
                .agenda(astronautsMeetingAgenda)
                .assistances(astronautsMeetingAssistances)
                .start(timeBase.plusSeconds(0))
                .end(timeBase.plusSeconds(300)).build());
        return meetings;
    }

    @Override
    public Map<String, IRoom> createRooms() {
        val rooms = new HashMap<String, IRoom>();
        val blackRoomDevices = new ArrayList<String>();
        blackRoomDevices.add(DEVICE_ID_BLACK_DISPLAY);
        rooms.put(ROOM_ID_BLACK, Room.builder()
                .id(ROOM_ID_BLACK)
                .name("Room Black")
                .deviceIds(blackRoomDevices)
                .build());
        return rooms;
    }

    @Override
    public Map<String, IDevice> createDevices() {
        val devices = new HashMap<String, IDevice>();
        devices.put(DEVICE_ID_BLACK_DISPLAY, AcmeDisplay.builder()
                .id(DEVICE_ID_BLACK_DISPLAY)
                .name("Display in Room Black")
                .dummyDisplayProperty("Dummy property of Display in Room Black")
                .build());
        return devices;
    }
}
