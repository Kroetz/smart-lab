package de.qaware.smartlabdataset.factory;

import com.google.common.collect.ImmutableMap;
import de.qaware.smartlabaction.action.actor.beamer.DummyBeamerAdapter;
import de.qaware.smartlabaction.action.actor.display.DummyDisplayAdapter;
import de.qaware.smartlabaction.action.actor.github.GithubKnowledgeBaseInfo;
import de.qaware.smartlabaction.action.actor.microphone.ThinkpadP50InternalMicrophoneAdapter;
import de.qaware.smartlabassistance.assistance.info.agendashowing.AgendaShowingInfo;
import de.qaware.smartlabassistance.assistance.info.devicepreparation.DevicePreparationInfo;
import de.qaware.smartlabassistance.assistance.info.filedisplaying.FileDisplayingInfo;
import de.qaware.smartlabassistance.assistance.info.generic.IAssistanceInfo;
import de.qaware.smartlabassistance.assistance.info.minutetaking.MinuteTakingInfo;
import de.qaware.smartlabassistance.assistance.info.websitedisplaying.WebsiteDisplayingInfo;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.device.entity.Device;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.location.Location;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.data.meeting.AgendaItem;
import de.qaware.smartlabcore.data.meeting.IAgendaItem;
import de.qaware.smartlabcore.data.meeting.Meeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.person.Person;
import de.qaware.smartlabcore.data.person.PersonId;
import de.qaware.smartlabcore.data.workgroup.Workgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.exception.DataSetException;
import de.qaware.smartlabcore.miscellaneous.Language;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;

@Component
public class CoastGuardSampleDataSetFactory extends AbstractDataSetFactory {

    public static final String ID = "sample-data-coast-guard";

    public static final WorkgroupId WORKGROUP_ID_COAST_GUARD = WorkgroupId.of("coast-guard");
    public static final PersonId MEMBER_ID_ALICE = PersonId.of("coast-guard-alice");
    public static final PersonId MEMBER_ID_BEN = PersonId.of("coast-guard-ben");
    public static final PersonId MEMBER_ID_CHARLIE = PersonId.of("coast-guard-charlie");
    public static final LocationId LOCATION_ID_BLUE = LocationId.of("coast-guard-workplace");
    public static final DeviceId DEVICE_ID_BLUE_DISPLAY_BIG = DeviceId.of("blue-display-big");
    public static final DeviceId DEVICE_ID_BLUE_DISPLAY_SMALL = DeviceId.of("blue-display-small");
    public static final DeviceId DEVICE_ID_BLUE_DISPLAY_BEAMER = DeviceId.of("blue-display-beamer");
    public static final DeviceId DEVICE_ID_BLUE_MICROPHONE = DeviceId.of("blue-microphone");
    public static final DeviceId DEVICE_ID_BLUE_WEB_BROWSER = DeviceId.of("blue-web-browser");
    public static final DeviceId DEVICE_ID_BLUE_POWER_POINT = DeviceId.of("blue-power-point");
    public static final MeetingId MEETING_ID_WHALES = MeetingId.of("whales", LOCATION_ID_BLUE);
    public static final MeetingId MEETING_ID_WHIRLPOOLS = MeetingId.of("whirlpools", LOCATION_ID_BLUE);
    public static final String DELEGATE_ID_BLUE = "smart-lab-blue-delegate-microservice";

    private final IAssistanceInfo minuteTakingInfo;
    private final IAssistanceInfo websiteDisplayingInfo;
    private final IAssistanceInfo agendaShowingInfo;
    private final IAssistanceInfo fileDisplayingInfo;
    private final IAssistanceInfo locationUnlockingInfo;
    private final IAssistanceInfo devicePreparationInfo;
    private final GithubKnowledgeBaseInfo.Factory githubKnowledgeBaseInfoFactory;

    public CoastGuardSampleDataSetFactory(
            IAssistanceInfo minuteTakingInfo,
            IAssistanceInfo websiteDisplayingInfo,
            IAssistanceInfo agendaShowingInfo,
            IAssistanceInfo fileDisplayingInfo,
            IAssistanceInfo locationUnlockingInfo,
            IAssistanceInfo devicePreparationInfo,
            GithubKnowledgeBaseInfo.Factory githubKnowledgeBaseInfoFactory) {
        super(ID);
        this.minuteTakingInfo = minuteTakingInfo;
        this.websiteDisplayingInfo = websiteDisplayingInfo;
        this.agendaShowingInfo = agendaShowingInfo;
        this.fileDisplayingInfo = fileDisplayingInfo;
        this.locationUnlockingInfo = locationUnlockingInfo;
        this.devicePreparationInfo = devicePreparationInfo;
        this.githubKnowledgeBaseInfoFactory = githubKnowledgeBaseInfoFactory;
    }

    @Override
    public Set<Workgroup> createWorkgroupSet() throws DataSetException {
        Set<Workgroup> workgroups = new HashSet<>();
        Set<PersonId> coastGuardMembers = new HashSet<>();
        coastGuardMembers.add(MEMBER_ID_ALICE);
        coastGuardMembers.add(MEMBER_ID_BEN);
        coastGuardMembers.add(MEMBER_ID_CHARLIE);
        workgroups.add(Workgroup.builder()
                .id(WORKGROUP_ID_COAST_GUARD)
                .name("Coast Guard")
                .memberIds(coastGuardMembers)
                .knowledgeBaseInfo(this.githubKnowledgeBaseInfoFactory.newInstance(ImmutableMap
                        .<String, String>builder()
                        .put(GithubKnowledgeBaseInfo.KNOWLEDGE_BASE_PROPERTY_KEY_USER, "Kroetz")
                        .put(GithubKnowledgeBaseInfo.KNOWLEDGE_BASE_PROPERTY_KEY_REPOSITORY, "coastGuardRepo")
                        .build()))
                .build());
        return workgroups;
    }

    @Override
    public Set<Person> createWorkgroupMemberSet() throws DataSetException {
        Set<Person> workgroupMembers = new HashSet<>();
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
    public Set<Meeting> createMeetingSet() throws DataSetException {
        Set<Meeting> meetings = new HashSet<>();
        List<IAgendaItem> whaleMeetingAgenda = new ArrayList<>();
        whaleMeetingAgenda.add(AgendaItem.of("Show critical areas"));
        whaleMeetingAgenda.add(AgendaItem.of("Explain whale anatomy"));
        whaleMeetingAgenda.add(AgendaItem.of("Drink coffee"));
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
        whaleConfigs.add(this.locationUnlockingInfo.createConfiguration(ImmutableMap
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
        whirlpoolMeetingAgenda.add(AgendaItem.of("Explain how whirlpools develop"));
        whirlpoolMeetingAgenda.add(AgendaItem.of("Show how you can escape whirlpools"));
        whirlpoolMeetingAgenda.add(AgendaItem.of("Admire the fine weather"));
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
        whirlpoolConfigs.add(this.locationUnlockingInfo.createConfiguration(ImmutableMap
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
    public Set<Location> createLocationSet() throws DataSetException {
        Set<Location> locations = new HashSet<>();
        Set<DeviceId> blueLocationDevices = new HashSet<>();
        blueLocationDevices.add(DEVICE_ID_BLUE_DISPLAY_BIG);
        blueLocationDevices.add(DEVICE_ID_BLUE_DISPLAY_SMALL);
        blueLocationDevices.add(DEVICE_ID_BLUE_DISPLAY_BEAMER);
        blueLocationDevices.add(DEVICE_ID_BLUE_MICROPHONE);
        locations.add(Location.builder()
                .id(LOCATION_ID_BLUE)
                .name("Coast guard workplace")
                .deviceIds(blueLocationDevices)
                .build());
        return locations;
    }

    @Override
    public Set<Device> createDeviceSet() throws DataSetException {
        Set<Device> devices = new HashSet<>();
        devices.add(Device.builder()
                .id(DEVICE_ID_BLUE_DISPLAY_BIG)
                .type(DummyDisplayAdapter.DEVICE_TYPE)
                .name(format("Big main display at location \"%s\"", LOCATION_ID_BLUE))
                .responsibleDelegate(DELEGATE_ID_BLUE)
                .build());
        devices.add(Device.builder()
                .id(DEVICE_ID_BLUE_DISPLAY_SMALL)
                .type(DummyDisplayAdapter.DEVICE_TYPE)
                .name(format("Small display next to the door at location \"%s\"", LOCATION_ID_BLUE))
                .responsibleDelegate(DELEGATE_ID_BLUE)
                .build());
        devices.add(Device.builder()
                .id(DEVICE_ID_BLUE_DISPLAY_BEAMER)
                .type(DummyBeamerAdapter.DEVICE_TYPE)
                .name(format("Beamer at location \"%s\"", LOCATION_ID_BLUE))
                .responsibleDelegate(DELEGATE_ID_BLUE)
                .build());
        devices.add(Device.builder()
                .id(DEVICE_ID_BLUE_MICROPHONE)
                .type(ThinkpadP50InternalMicrophoneAdapter.DEVICE_TYPE)
                .name(format("Microphone at location \"%s\"", LOCATION_ID_BLUE))
                .responsibleDelegate(DELEGATE_ID_BLUE)
                .build());
        devices.add(Device.builder()
                .id(DEVICE_ID_BLUE_WEB_BROWSER)
                .type("firefox")
                .name(format("Web browser at location \"%s\"", LOCATION_ID_BLUE))
                .responsibleDelegate(DELEGATE_ID_BLUE)
                .build());
        devices.add(Device.builder()
                .id(DEVICE_ID_BLUE_POWER_POINT)
                .type("powerPoint")
                .name(format("PowerPoint at location \"%s\"", LOCATION_ID_BLUE))
                .responsibleDelegate(DELEGATE_ID_BLUE)
                .build());
        return devices;
    }
}
