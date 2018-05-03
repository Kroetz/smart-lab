package de.qaware.smartlabcore.data.sample.factory;

import de.qaware.smartlabcommons.Constants;
import de.qaware.smartlabcommons.data.device.AcmeDisplay;
import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcommons.data.meeting.AgendaItem;
import de.qaware.smartlabcommons.data.meeting.IAgendaItem;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.meeting.Meeting;
import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcommons.data.person.Person;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.data.room.Room;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public List<IWorkgroup> createWorkgroupList() {
        List<IWorkgroup> workgroups = new ArrayList<>();
        Set<String> astronautsMembers = new HashSet<>();
        astronautsMembers.add(MEMBER_ID_ALEX);
        astronautsMembers.add(MEMBER_ID_BEVERLY);
        astronautsMembers.add(MEMBER_ID_CHARLOTTE);
        try {
            workgroups.add(Workgroup.builder()
                    .id(WORKGROUP_ID_ASTRONAUTS)
                    .name("Astronauts")
                    .memberIds(astronautsMembers)
                    .knowledgeBase(new URL("http", "astronauts.com", 80, "/wiki"))
                    .codeRepository(new URL("http", "astronauts.com", 80, "/git"))
                    .build());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            assert false;
        }
        return workgroups;
    }

    @Override
    public List<IPerson> createWorkgroupMemberList() {
        List<IPerson> workgroupMembers = new ArrayList<>();
        workgroupMembers.add(Person.builder()
                .id(MEMBER_ID_ALEX)
                .name("Astronaut Alex")
                .email("alex@astronauts.com")
                .build());
        workgroupMembers.add(Person.builder()
                .id(MEMBER_ID_BEVERLY)
                .name("Astronaut Beverly")
                .email("beverly@astronauts.com")
                .build());
        workgroupMembers.add(Person.builder()
                .id(MEMBER_ID_CHARLOTTE)
                .name("Astronaut Charlotte")
                .email("charlotte@astronauts.com")
                .build());
        return workgroupMembers;
    }

    @Override
    public List<IMeeting> createMeetingList() {
        List<IMeeting> meetings = new ArrayList<>();
        List<IAgendaItem> astronautsMeetingAgenda = new ArrayList<>();
        astronautsMeetingAgenda.add(AgendaItem.builder().text("Calculate journey duration").build());
        astronautsMeetingAgenda.add(AgendaItem.builder().text("Discuss who may press the launch button of the rocket").build());
        astronautsMeetingAgenda.add(AgendaItem.builder().text("Complain that this is all rocket science").build());
        Set<String> astronautsMeetingAssistances = new HashSet<>();
        astronautsMeetingAssistances.add(Constants.MINUTE_TAKING);
        astronautsMeetingAssistances.add(Constants.ROOM_UNLOCKING);
        meetings.add(Meeting.builder()
                .id(MEETING_ID_MARS)
                .title("Meeting about travelling to Mars")
                .workgroupId(WORKGROUP_ID_ASTRONAUTS)
                .roomId(ROOM_ID_BLACK)
                .agenda(astronautsMeetingAgenda)
                .assistanceIds(astronautsMeetingAssistances)
                .start(timeBase.plusSeconds(0))
                .end(timeBase.plusSeconds(300)).build());
        return meetings;
    }

    @Override
    public List<IRoom> createRoomList() {
        List<IRoom> rooms = new ArrayList<>();
        List<String> blackRoomDevices = new ArrayList<>();
        blackRoomDevices.add(DEVICE_ID_BLACK_DISPLAY);
        rooms.add(Room.builder()
                .id(ROOM_ID_BLACK)
                .name("Room Black")
                .deviceIds(blackRoomDevices)
                .build());
        return rooms;
    }

    @Override
    public List<IDevice> createDeviceList() {
        List<IDevice> devices = new ArrayList<>();
        devices.add(AcmeDisplay.builder()
                .id(DEVICE_ID_BLACK_DISPLAY)
                .name("Display in Room Black")
                .dummyDisplayProperty("Dummy property of Display in Room Black")
                .build());
        return devices;
    }
}
