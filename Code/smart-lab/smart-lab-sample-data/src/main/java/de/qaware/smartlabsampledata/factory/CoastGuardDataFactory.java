package de.qaware.smartlabsampledata.factory;

import de.qaware.smartlabcommons.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcommons.data.assistance.MinuteTaking;
import de.qaware.smartlabcommons.data.assistance.RoomUnlocking;
import de.qaware.smartlabcommons.data.device.display.DummyDisplay;
import de.qaware.smartlabcommons.data.device.entity.Device;
import de.qaware.smartlabcommons.data.device.entity.IDevice;
import de.qaware.smartlabcommons.data.device.microphone.ThinkpadP50InternalMicrophoneAdapter;
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
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Component
public class CoastGuardDataFactory extends AbstractSampleDataFactory {

    public static final String WORKGROUP_ID_COAST_GUARD = "coast-guard";
    public static final String MEMBER_ID_ALICE = "coast-guard-alice";
    public static final String MEMBER_ID_BEN = "coast-guard-ben";
    public static final String MEMBER_ID_CHARLIE = "coast-guard-charlie";
    public static final String MEETING_ID_WHALES = "whales";
    public static final String MEETING_ID_WHIRLPOOLS = "whirlpools";
    public static final String ROOM_ID_BLUE = "blue";
    public static final String DEVICE_ID_BLUE_DISPLAY = "blue-display";
    public static final String DEVICE_ID_BLUE_MICROPHONE = "blue-microphone";
    public static final String DELEGATE_ID_BLUE = "blue-delegate";

    public CoastGuardDataFactory() {
        super();
    }

    @Override
    public List<IWorkgroup> createWorkgroupList() {
        List<IWorkgroup> workgroups = new ArrayList<>();
        Set<String> coastGuardMembers = new HashSet<>();
        coastGuardMembers.add(MEMBER_ID_ALICE);
        coastGuardMembers.add(MEMBER_ID_BEN);
        coastGuardMembers.add(MEMBER_ID_CHARLIE);
        try {
            workgroups.add(Workgroup.builder()
                    .id(WORKGROUP_ID_COAST_GUARD)
                    .name("Coast Guard")
                    .memberIds(coastGuardMembers)
                    .knowledgeBase(new URL("http", "coast-guard.com", 80, "/wiki"))
                    .codeRepository(new URL("http", "coast-guard.com", 80, "/git"))
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
                .id(MEMBER_ID_ALICE)
                .name("Coast Guard Alice")
                .email("alice@coast-guard.com")
                .build());
        workgroupMembers.add(Person.builder()
                .id(MEMBER_ID_BEN)
                .name("Coast Guard Ben")
                .email("ben@coast-guard.com")
                .build());
        workgroupMembers.add(Person.builder()
                .id(MEMBER_ID_CHARLIE)
                .name("Coast Guard Charlie")
                .email("charlie@coast-guard.com")
                .build());
        return workgroupMembers;
    }

    @Override
    public List<IMeeting> createMeetingList() {
        List<IMeeting> meetings = new ArrayList<>();
        List<IAgendaItem> whaleMeetingAgenda = new ArrayList<>();
        whaleMeetingAgenda.add(AgendaItem.builder().text("Show critical areas").build());
        whaleMeetingAgenda.add(AgendaItem.builder().text("Explain whale anatomy").build());
        whaleMeetingAgenda.add(AgendaItem.builder().text("Drink coffee").build());
        Set<String> whaleMeetingAssistances = new HashSet<>();
        whaleMeetingAssistances.add(MinuteTaking.ASSISTANCE_ID);
        whaleMeetingAssistances.add(RoomUnlocking.ASSISTANCE_ID);
        Map<String, IAssistanceConfiguration> whaleMeetingAssistanceConfigurations = new HashMap<>();
        whaleMeetingAssistanceConfigurations.put(MinuteTaking.ASSISTANCE_ID, new MinuteTaking.Configuration(DEVICE_ID_BLUE_MICROPHONE));
        whaleMeetingAssistanceConfigurations.put(RoomUnlocking.ASSISTANCE_ID, new RoomUnlocking.Configuration("dummy ID"));
        meetings.add(Meeting.builder()
                .id(MEETING_ID_WHALES)
                .title("Meeting about preventing illegal whale hunting")
                .workgroupId(WORKGROUP_ID_COAST_GUARD)
                .roomId(ROOM_ID_BLUE)
                .agenda(whaleMeetingAgenda)
                .assistanceIds(whaleMeetingAssistances)
                .assistanceConfigurationsById(whaleMeetingAssistanceConfigurations)
                .start(timeBase.plusSeconds(0))
                .end(timeBase.plusSeconds(300)).build());

        List<IAgendaItem> whirlpoolMeetingAgenda = new ArrayList<>();
        whirlpoolMeetingAgenda.add(AgendaItem.builder().text("Explain how whirlpools develop").build());
        whirlpoolMeetingAgenda.add(AgendaItem.builder().text("Show how you can escape whirlpools").build());
        whirlpoolMeetingAgenda.add(AgendaItem.builder().text("Admire the fine weather").build());
        Set<String> whirlpoolMeetingAssistances = new HashSet<>();
        whirlpoolMeetingAssistances.add(MinuteTaking.ASSISTANCE_ID);
        whirlpoolMeetingAssistances.add(RoomUnlocking.ASSISTANCE_ID);
        Map<String, IAssistanceConfiguration> whirlpoolMeetingAssistanceConfigurations = new HashMap<>();
        whirlpoolMeetingAssistanceConfigurations.put(MinuteTaking.ASSISTANCE_ID, new MinuteTaking.Configuration(DEVICE_ID_BLUE_MICROPHONE));
        whirlpoolMeetingAssistanceConfigurations.put(RoomUnlocking.ASSISTANCE_ID, new RoomUnlocking.Configuration("dummy ID"));
        meetings.add(Meeting.builder()
                .id(MEETING_ID_WHIRLPOOLS)
                .title("Meeting about dangers of whirlpools")
                .workgroupId(WORKGROUP_ID_COAST_GUARD)
                .roomId(ROOM_ID_BLUE)
                .agenda(whirlpoolMeetingAgenda)
                .assistanceIds(whirlpoolMeetingAssistances)
                .assistanceConfigurationsById(whirlpoolMeetingAssistanceConfigurations)
                .start(timeBase.plusSeconds(360))
                .end(timeBase.plusSeconds(660)).build());

        return meetings;
    }

    @Override
    public List<IRoom> createRoomList() {
        List<IRoom> rooms = new ArrayList<>();
        List<String> blueRoomDevices = new ArrayList<>();
        blueRoomDevices.add(DEVICE_ID_BLUE_DISPLAY);
        blueRoomDevices.add(DEVICE_ID_BLUE_MICROPHONE);
        rooms.add(Room.builder()
                .id(ROOM_ID_BLUE)
                .name("Room Blue")
                .deviceIds(blueRoomDevices)
                .build());
        return rooms;
    }

    @Override
    public List<IDevice> createDeviceList() {
        List<IDevice> devices = new ArrayList<>();
        devices.add(Device.builder()
                .id(DEVICE_ID_BLUE_DISPLAY)
                .type(DummyDisplay.DEVICE_TYPE)
                .name("Display in Room Blue")
                .responsibleDelegate(DELEGATE_ID_BLUE)
                .build());
        devices.add(Device.builder()
                .id(DEVICE_ID_BLUE_MICROPHONE)
                .type(ThinkpadP50InternalMicrophoneAdapter.DEVICE_TYPE)
                .name("Microphone in Room Blue")
                .responsibleDelegate(DELEGATE_ID_BLUE)
                .build());
        return devices;
    }
}
