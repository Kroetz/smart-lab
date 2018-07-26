package de.qaware.smartlabsampledata.factory;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.Coordinates;
import de.qaware.smartlabaction.action.actor.beamer.DummyBeamerAdapter;
import de.qaware.smartlabaction.action.actor.github.GithubKnowledgeBaseInfo;
import de.qaware.smartlabassistance.assistance.info.agendashowing.AgendaShowingInfo;
import de.qaware.smartlabassistance.assistance.info.devicepreparation.DevicePreparationInfo;
import de.qaware.smartlabassistance.assistance.info.filedisplaying.FileDisplayingInfo;
import de.qaware.smartlabassistance.assistance.info.generic.IAssistanceInfo;
import de.qaware.smartlabassistance.assistance.info.minutetaking.MinuteTakingInfo;
import de.qaware.smartlabassistance.assistance.info.websitedisplaying.WebsiteDisplayingInfo;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabaction.action.actor.display.DummyDisplay;
import de.qaware.smartlabcore.data.device.entity.Device;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabaction.action.actor.microphone.ThinkpadP50InternalMicrophoneAdapter;
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
import de.qaware.smartlabcore.miscellaneous.Language;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CoastGuardDataFactory extends AbstractSampleDataFactory {

    public static final WorkgroupId WORKGROUP_ID_COAST_GUARD = WorkgroupId.of("coast-guard");
    public static final PersonId MEMBER_ID_ALICE = PersonId.of("coast-guard-alice");
    public static final PersonId MEMBER_ID_BEN = PersonId.of("coast-guard-ben");
    public static final PersonId MEMBER_ID_CHARLIE = PersonId.of("coast-guard-charlie");
    public static final RoomId ROOM_ID_BLUE = RoomId.of("blue");
    public static final DeviceId DEVICE_ID_BLUE_DISPLAY_BIG = DeviceId.of("blue-display-big");
    public static final DeviceId DEVICE_ID_BLUE_DISPLAY_SMALL = DeviceId.of("blue-display-small");
    public static final DeviceId DEVICE_ID_BLUE_DISPLAY_BEAMER = DeviceId.of("blue-display-beamer");
    public static final DeviceId DEVICE_ID_BLUE_MICROPHONE = DeviceId.of("blue-microphone");
    public static final DeviceId DEVICE_ID_BLUE_WEB_BROWSER = DeviceId.of("blue-web-browser");
    public static final DeviceId DEVICE_ID_BLUE_POWER_POINT = DeviceId.of("blue-power-point");
    public static final MeetingId MEETING_ID_WHALES = MeetingId.of("whales", ROOM_ID_BLUE);
    public static final MeetingId MEETING_ID_WHIRLPOOLS = MeetingId.of("whirlpools", ROOM_ID_BLUE);
    public static final String DELEGATE_ID_BLUE = "blue-delegate";

    private final IAssistanceInfo minuteTakingInfo;
    private final IAssistanceInfo websiteDisplayingInfo;
    private final IAssistanceInfo agendaShowingInfo;
    private final IAssistanceInfo fileDisplayingInfo;
    private final IAssistanceInfo roomUnlockingInfo;
    private final IAssistanceInfo devicePreparationInfo;

    public CoastGuardDataFactory(
            IAssistanceInfo minuteTakingInfo,
            IAssistanceInfo websiteDisplayingInfo,
            IAssistanceInfo agendaShowingInfo,
            IAssistanceInfo fileDisplayingInfo,
            IAssistanceInfo roomUnlockingInfo,
            IAssistanceInfo devicePreparationInfo) {
        super();
        this.minuteTakingInfo = minuteTakingInfo;
        this.websiteDisplayingInfo = websiteDisplayingInfo;
        this.agendaShowingInfo = agendaShowingInfo;
        this.fileDisplayingInfo = fileDisplayingInfo;
        this.roomUnlockingInfo = roomUnlockingInfo;
        this.devicePreparationInfo = devicePreparationInfo;
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
        whaleMeetingAgenda.add(AgendaItem.builder().content("Show critical areas").build());
        whaleMeetingAgenda.add(AgendaItem.builder().content("Explain whale anatomy").build());
        whaleMeetingAgenda.add(AgendaItem.builder().content("Drink coffee").build());
        Set<IAssistanceConfiguration> whaleConfigs = new HashSet<>();
        whaleConfigs.add(this.minuteTakingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(MinuteTakingInfo.Configuration.CONFIG_PROPERTY_KEY_SPOKEN_LANGUAGE, Language.EN_US.toString())
                .put(MinuteTakingInfo.Configuration.CONFIG_PROPERTY_KEY_UPLOAD_DIR, "/sampleDataMinutes")
                .put(MinuteTakingInfo.Configuration.CONFIG_PROPERTY_KEY_MICROPHONE_ID, DEVICE_ID_BLUE_MICROPHONE.getIdValue())
                .build()));
        whaleConfigs.add(this.websiteDisplayingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(WebsiteDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_URL, "https://en.wikipedia.org/wiki/Whale")
                .put(WebsiteDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_WEB_BROWSER_ID, DEVICE_ID_BLUE_WEB_BROWSER.getIdValue())
                .put(WebsiteDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_DISPLAY_ID, DEVICE_ID_BLUE_DISPLAY_BIG.getIdValue())
                .build()));
        whaleConfigs.add(this.agendaShowingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(AgendaShowingInfo.Configuration.CONFIG_PROPERTY_KEY_WEB_BROWSER_ID, DEVICE_ID_BLUE_WEB_BROWSER.getIdValue())
                .put(AgendaShowingInfo.Configuration.CONFIG_PROPERTY_KEY_DISPLAY_ID, DEVICE_ID_BLUE_DISPLAY_SMALL.getIdValue())
                .build()));
        whaleConfigs.add(this.fileDisplayingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(FileDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_FILE, "slides/whale_slides.pptx")
                .put(FileDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_PROGRAM_ID, DEVICE_ID_BLUE_POWER_POINT.getIdValue())
                .put(FileDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_DISPLAY_ID, DEVICE_ID_BLUE_DISPLAY_BEAMER.getIdValue())
                .build()));
        whaleConfigs.add(this.roomUnlockingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .build()));
        whaleConfigs.add(this.devicePreparationInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(DevicePreparationInfo.Configuration.CONFIG_PROPERTY_KEY_DEVICE_ID, DEVICE_ID_BLUE_DISPLAY_BIG.getIdValue())
                .build()));
        whaleConfigs.add(this.devicePreparationInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(DevicePreparationInfo.Configuration.CONFIG_PROPERTY_KEY_DEVICE_ID, DEVICE_ID_BLUE_DISPLAY_SMALL.getIdValue())
                .build()));
        whaleConfigs.add(this.devicePreparationInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(DevicePreparationInfo.Configuration.CONFIG_PROPERTY_KEY_DEVICE_ID, DEVICE_ID_BLUE_DISPLAY_BEAMER.getIdValue())
                .build()));
        meetings.add(Meeting.builder()
                .id(MEETING_ID_WHALES)
                .title("Meeting about preventing illegal whale hunting")
                .workgroupId(WORKGROUP_ID_COAST_GUARD)
                .agenda(whaleMeetingAgenda)
                .assistanceConfigurations(whaleConfigs)
                .start(timeBase.plusSeconds(0))
                .end(timeBase.plusSeconds(300)).build());

        List<IAgendaItem> whirlpoolMeetingAgenda = new ArrayList<>();
        whirlpoolMeetingAgenda.add(AgendaItem.builder().content("Explain how whirlpools develop").build());
        whirlpoolMeetingAgenda.add(AgendaItem.builder().content("Show how you can escape whirlpools").build());
        whirlpoolMeetingAgenda.add(AgendaItem.builder().content("Admire the fine weather").build());
        Set<IAssistanceConfiguration> whirlpoolConfigs = new HashSet<>();
        whirlpoolConfigs.add(this.minuteTakingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(MinuteTakingInfo.Configuration.CONFIG_PROPERTY_KEY_SPOKEN_LANGUAGE, Language.EN_US.toString())
                .put(MinuteTakingInfo.Configuration.CONFIG_PROPERTY_KEY_UPLOAD_DIR, "/sampleDataMinutes")
                .put(MinuteTakingInfo.Configuration.CONFIG_PROPERTY_KEY_MICROPHONE_ID, DEVICE_ID_BLUE_MICROPHONE.getIdValue())
                .build()));
        whirlpoolConfigs.add(this.websiteDisplayingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(WebsiteDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_URL, "https://en.wikipedia.org/wiki/Whirlpool")
                .put(WebsiteDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_WEB_BROWSER_ID, DEVICE_ID_BLUE_WEB_BROWSER.getIdValue())
                .put(WebsiteDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_DISPLAY_ID, DEVICE_ID_BLUE_DISPLAY_BIG.getIdValue())
                .build()));
        whirlpoolConfigs.add(this.agendaShowingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(AgendaShowingInfo.Configuration.CONFIG_PROPERTY_KEY_WEB_BROWSER_ID, DEVICE_ID_BLUE_WEB_BROWSER.getIdValue())
                .put(AgendaShowingInfo.Configuration.CONFIG_PROPERTY_KEY_DISPLAY_ID, DEVICE_ID_BLUE_DISPLAY_SMALL.getIdValue())
                .build()));
        whirlpoolConfigs.add(this.fileDisplayingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(FileDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_FILE, "slides/whirlpool_slides.pptx")
                .put(FileDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_PROGRAM_ID, DEVICE_ID_BLUE_POWER_POINT.getIdValue())
                .put(FileDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_DISPLAY_ID, DEVICE_ID_BLUE_DISPLAY_BEAMER.getIdValue())
                .build()));
        whirlpoolConfigs.add(this.roomUnlockingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .build()));
        whirlpoolConfigs.add(this.devicePreparationInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(DevicePreparationInfo.Configuration.CONFIG_PROPERTY_KEY_DEVICE_ID, DEVICE_ID_BLUE_DISPLAY_BIG.getIdValue())
                .build()));
        whirlpoolConfigs.add(this.devicePreparationInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(DevicePreparationInfo.Configuration.CONFIG_PROPERTY_KEY_DEVICE_ID, DEVICE_ID_BLUE_DISPLAY_SMALL.getIdValue())
                .build()));
        whirlpoolConfigs.add(this.devicePreparationInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(DevicePreparationInfo.Configuration.CONFIG_PROPERTY_KEY_DEVICE_ID, DEVICE_ID_BLUE_DISPLAY_BEAMER.getIdValue())
                .build()));
        meetings.add(Meeting.builder()
                .id(MEETING_ID_WHIRLPOOLS)
                .title("Meeting about dangers of whirlpools")
                .workgroupId(WORKGROUP_ID_COAST_GUARD)
                .agenda(whirlpoolMeetingAgenda)
                .assistanceConfigurations(whirlpoolConfigs)
                .start(timeBase.plusSeconds(360))
                .end(timeBase.plusSeconds(660)).build());

        return meetings;
    }

    @Override
    public Set<IRoom> createRoomSet() {
        Set<IRoom> rooms = new HashSet<>();
        Set<DeviceId> blueRoomDevices = new HashSet<>();
        blueRoomDevices.add(DEVICE_ID_BLUE_DISPLAY_BIG);
        blueRoomDevices.add(DEVICE_ID_BLUE_DISPLAY_SMALL);
        blueRoomDevices.add(DEVICE_ID_BLUE_DISPLAY_BEAMER);
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
                .id(DEVICE_ID_BLUE_DISPLAY_BIG)
                .type(DummyDisplay.DEVICE_TYPE)
                .name("Big main display in room \"Blue\"")
                .responsibleDelegate(DELEGATE_ID_BLUE)
                .build());
        devices.add(Device.builder()
                .id(DEVICE_ID_BLUE_DISPLAY_SMALL)
                .type(DummyDisplay.DEVICE_TYPE)
                .name("Small display next to the door in room \"Blue\"")
                .responsibleDelegate(DELEGATE_ID_BLUE)
                .build());
        devices.add(Device.builder()
                .id(DEVICE_ID_BLUE_DISPLAY_BEAMER)
                .type(DummyBeamerAdapter.DEVICE_TYPE)
                .name("Beamer in room \"Blue\"")
                .responsibleDelegate(DELEGATE_ID_BLUE)
                .build());
        devices.add(Device.builder()
                .id(DEVICE_ID_BLUE_MICROPHONE)
                .type(ThinkpadP50InternalMicrophoneAdapter.DEVICE_TYPE)
                .name("Microphone in room \"Blue\"")
                .responsibleDelegate(DELEGATE_ID_BLUE)
                .build());
        devices.add(Device.builder()
                .id(DEVICE_ID_BLUE_WEB_BROWSER)
                .type("firefox")
                .name("Web browser in room \"Blue\"")
                .responsibleDelegate(DELEGATE_ID_BLUE)
                .build());
        devices.add(Device.builder()
                .id(DEVICE_ID_BLUE_POWER_POINT)
                .type("powerPoint")
                .name("PowerPoint in room \"Blue\"")
                .responsibleDelegate(DELEGATE_ID_BLUE)
                .build());
        return devices;
    }
}
