package de.qaware.smartlabcore.data.sample.factory;

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
import java.util.HashSet;
import java.util.List;

@Component
@Slf4j
public class FireFightersDataFactory extends AbstractSampleDataFactory {

    public static final String WORKGROUP_ID_FIRE_FIGHTERS = "fire-fighters";
    public static final String MEMBER_ID_ANTHONY = "fire-fighter-anthony";
    public static final String MEMBER_ID_BRUCE = "fire-fighter-bruce";
    public static final String MEMBER_ID_CARLOS = "fire-fighter-carlos";
    public static final String MEETING_ID_TRUCK = "truck";
    public static final String ROOM_ID_RED = "red";
    public static final String DEVICE_ID_RED_DISPLAY = "red-display";
    public static final String DEVICE_ID_RED_MICROPHONE = "red-microphone";

    public FireFightersDataFactory() {
        super();
    }

    @Override
    public List<IWorkgroup> createWorkgroupList() {
        val workgroups = new ArrayList<IWorkgroup>();
        val fireFighterMembers = new ArrayList<String>();
        fireFighterMembers.add(MEMBER_ID_ANTHONY);
        fireFighterMembers.add(MEMBER_ID_BRUCE);
        fireFighterMembers.add(MEMBER_ID_CARLOS);
        try {
            workgroups.add(Workgroup.builder()
                    .id(WORKGROUP_ID_FIRE_FIGHTERS)
                    .name("Fire Fighters")
                    .memberIds(fireFighterMembers)
                    .knowledgeBase(new URL("http", "fire-fighters.com", 80, "/wiki"))
                    .codeRepository(new URL("http", "fire-fighters.com", 80, "/git"))
                    .build());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            assert false;
        }
        return workgroups;
    }

    @Override
    public List<IPerson> createWorkgroupMemberList() {
        val workgroupMembers = new ArrayList<IPerson>();
        workgroupMembers.add(Person.builder()
                .id(MEMBER_ID_ANTHONY)
                .name("Fire Fighter Anthony")
                .email("anthony@fire-fighters.com")
                .build());
        workgroupMembers.add(Person.builder()
                .id(MEMBER_ID_BRUCE)
                .name("Fire Fighter Bruce")
                .email("bruce@fire-fighters.com")
                .build());
        workgroupMembers.add(Person.builder()
                .id(MEMBER_ID_CARLOS)
                .name("Fire Fighter Carlos")
                .email("carlos@fire-fighters.com")
                .build());
        return workgroupMembers;
    }

    @Override
    public List<IMeeting> createMeetingList() {
        val meetings = new ArrayList<IMeeting>();
        val fireFightersMeetingAgenda = new ArrayList<IAgendaItem>();
        fireFightersMeetingAgenda.add(AgendaItem.builder().text("Show how bad the old truck is").build());
        fireFightersMeetingAgenda.add(AgendaItem.builder().text("Show how great the new truck is").build());
        fireFightersMeetingAgenda.add(AgendaItem.builder().text("Discuss how to pay for the new truck").build());
        val fireFightersMeetingAssistances = new HashSet<IAssistanceDao>();
        fireFightersMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.MINUTE_TAKING).build());
        fireFightersMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.ROOM_UNLOCKING).build());
        meetings.add(Meeting.builder()
                .id(MEETING_ID_TRUCK)
                .title("Meeting about the new fire truck \"Fire Exterminator 3000\"")
                .workgroupId(WORKGROUP_ID_FIRE_FIGHTERS)
                .roomId(ROOM_ID_RED)
                .agenda(fireFightersMeetingAgenda)
                .assistances(fireFightersMeetingAssistances)
                .start(timeBase.plusSeconds(120))
                .end(timeBase.plusSeconds(420)).build());
        return meetings;
    }

    @Override
    public List<IRoom> createRoomList() {
        val rooms = new ArrayList<IRoom>();
        val redRoomDevices = new ArrayList<String>();
        redRoomDevices.add(DEVICE_ID_RED_DISPLAY);
        redRoomDevices.add(DEVICE_ID_RED_MICROPHONE);
        rooms.add(Room.builder()
                .id(ROOM_ID_RED)
                .name("Room Red")
                .deviceIds(redRoomDevices)
                .build());
        return rooms;
    }

    @Override
    public List<IDevice> createDeviceList() {
        val devices = new ArrayList<IDevice>();
        devices.add(AcmeDisplay.builder()
                .id(DEVICE_ID_RED_DISPLAY)
                .name("Display in Room Red")
                .dummyDisplayProperty("Dummy property of Display in Room Red")
                .build());
        devices.add(AcmeMicrophone.builder()
                .id(DEVICE_ID_RED_MICROPHONE)
                .name("Microphone in Room Red")
                .dummyMicrophoneProperty("Dummy property of Microphone in Room Red")
                .build());
        return devices;
    }
}
