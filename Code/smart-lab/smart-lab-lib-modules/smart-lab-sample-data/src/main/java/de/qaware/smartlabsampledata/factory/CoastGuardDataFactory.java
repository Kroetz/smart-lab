package de.qaware.smartlabsampledata.factory;

import com.jcabi.github.Coordinates;
import de.qaware.smartlabaction.action.executable.external.github.GithubKnowledgeBaseInfo;
import de.qaware.smartlabassistance.assistance.info.MinuteTakingInfo;
import de.qaware.smartlabassistance.assistance.info.RoomUnlockingInfo;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.device.display.DummyDisplay;
import de.qaware.smartlabcore.data.device.entity.Device;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.device.microphone.ThinkpadP50InternalMicrophoneAdapter;
import de.qaware.smartlabcore.data.meeting.*;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.Person;
import de.qaware.smartlabcore.data.person.PersonId;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.room.Room;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.workgroup.Workgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CoastGuardDataFactory extends AbstractSampleDataFactory {

    public static final WorkgroupId WORKGROUP_ID_COAST_GUARD = WorkgroupId.of("coast-guard");
    public static final PersonId MEMBER_ID_ALICE = PersonId.of("coast-guard-alice");
    public static final PersonId MEMBER_ID_BEN = PersonId.of("coast-guard-ben");
    public static final PersonId MEMBER_ID_CHARLIE = PersonId.of("coast-guard-charlie");
    public static final MeetingId MEETING_ID_WHALES = MeetingId.of("whales");
    public static final MeetingId MEETING_ID_WHIRLPOOLS = MeetingId.of("whirlpools");
    public static final RoomId ROOM_ID_BLUE = RoomId.of("blue");
    public static final DeviceId DEVICE_ID_BLUE_DISPLAY = DeviceId.of("blue-display");
    public static final DeviceId DEVICE_ID_BLUE_MICROPHONE = DeviceId.of("blue-microphone");
    public static final String DELEGATE_ID_BLUE = "blue-delegate";

    public CoastGuardDataFactory() {
        super();
    }

    @Override
    public Set<IWorkgroup> createWorkgroupSet() {
        Set<IWorkgroup> workgroups = new HashSet<>();
        Set<PersonId> coastGuardMembers = new HashSet<>();
        coastGuardMembers.add(MEMBER_ID_ALICE);
        coastGuardMembers.add(MEMBER_ID_BEN);
        coastGuardMembers.add(MEMBER_ID_CHARLIE);
        workgroups.add(Workgroup.builder()
                .id(WORKGROUP_ID_COAST_GUARD)
                .name("Coast Guard")
                .memberIds(coastGuardMembers)
                .knowledgeBaseInfo(GithubKnowledgeBaseInfo.builder().repository(new Coordinates.Simple(
                        "Kroetz",
                        "coastGuardRepo")).build())
                .build());
        return workgroups;
    }

    @Override
    public Set<IPerson> createWorkgroupMemberSet() {
        Set<IPerson> workgroupMembers = new HashSet<>();
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
    public Set<IMeeting> createMeetingSet() {
        Set<IMeeting> meetings = new HashSet<>();
        List<IAgendaItem> whaleMeetingAgenda = new ArrayList<>();
        whaleMeetingAgenda.add(AgendaItem.builder().text("Show critical areas").build());
        whaleMeetingAgenda.add(AgendaItem.builder().text("Explain whale anatomy").build());
        whaleMeetingAgenda.add(AgendaItem.builder().text("Drink coffee").build());
        Set<String> whaleMeetingAssistances = new HashSet<>();
        whaleMeetingAssistances.add(MinuteTakingInfo.ASSISTANCE_ID);
        whaleMeetingAssistances.add(RoomUnlockingInfo.ASSISTANCE_ID);
        Map<String, IAssistanceConfiguration> whaleMeetingAssistanceConfigurations = new HashMap<>();
        whaleMeetingAssistanceConfigurations.put(MinuteTakingInfo.ASSISTANCE_ID, new MinuteTakingInfo.Configuration(DEVICE_ID_BLUE_MICROPHONE));
        whaleMeetingAssistanceConfigurations.put(RoomUnlockingInfo.ASSISTANCE_ID, new RoomUnlockingInfo.Configuration(DeviceId.of("dummy ID")));    // TODO
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
        whirlpoolMeetingAssistances.add(MinuteTakingInfo.ASSISTANCE_ID);
        whirlpoolMeetingAssistances.add(RoomUnlockingInfo.ASSISTANCE_ID);
        Map<String, IAssistanceConfiguration> whirlpoolMeetingAssistanceConfigurations = new HashMap<>();
        whirlpoolMeetingAssistanceConfigurations.put(MinuteTakingInfo.ASSISTANCE_ID, new MinuteTakingInfo.Configuration(DEVICE_ID_BLUE_MICROPHONE));
        whirlpoolMeetingAssistanceConfigurations.put(RoomUnlockingInfo.ASSISTANCE_ID, new RoomUnlockingInfo.Configuration(DeviceId.of("dummy ID")));
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
    public Set<IRoom> createRoomSet() {
        Set<IRoom> rooms = new HashSet<>();
        Set<DeviceId> blueRoomDevices = new HashSet<>();
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
    public Set<IDevice> createDeviceSet() {
        Set<IDevice> devices = new HashSet<>();
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
