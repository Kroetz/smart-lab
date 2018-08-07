package de.qaware.smartlab.data.set.factory;

import com.google.common.collect.ImmutableMap;
import de.qaware.smartlab.actuator.adapter.adapters.beamer.DummyBeamerAdapter;
import de.qaware.smartlab.actuator.adapter.adapters.display.DummyDisplayAdapter;
import de.qaware.smartlab.actuator.adapter.adapters.microphone.ThinkpadP50InternalMicrophoneAdapter;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.info.generic.IProjectBaseInfoFactory;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.info.github.GithubProjectBaseInfo;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.service.github.GithubServiceConnector;
import de.qaware.smartlab.assistance.assistances.info.agendashowing.AgendaShowingInfo;
import de.qaware.smartlab.assistance.assistances.info.devicepreparation.DevicePreparationInfo;
import de.qaware.smartlab.assistance.assistances.info.filedisplaying.FileDisplayingInfo;
import de.qaware.smartlab.assistance.assistances.info.generic.IAssistanceInfo;
import de.qaware.smartlab.assistance.assistances.info.minutetaking.MinuteTakingInfo;
import de.qaware.smartlab.assistance.assistances.info.websitedisplaying.WebsiteDisplayingInfo;
import de.qaware.smartlab.core.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlab.core.data.device.Device;
import de.qaware.smartlab.core.data.device.DeviceId;
import de.qaware.smartlab.core.data.device.IDevice;
import de.qaware.smartlab.core.data.generic.IResolver;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.location.Location;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.event.*;
import de.qaware.smartlab.core.data.person.IPerson;
import de.qaware.smartlab.core.data.person.Person;
import de.qaware.smartlab.core.data.person.PersonId;
import de.qaware.smartlab.core.data.person.PersonRole;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.data.workgroup.Workgroup;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.exception.DataSetException;
import de.qaware.smartlab.core.miscellaneous.Language;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;

@Component
@Slf4j
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
    public static final EventId EVENT_ID_WHALES = EventId.of("whales", LOCATION_ID_BLUE);
    public static final EventId EVENT_ID_WHIRLPOOLS = EventId.of("whirlpools", LOCATION_ID_BLUE);
    public static final String DELEGATE_ID_BLUE = "smart-lab-blue-delegate-microservice";

    private final IAssistanceInfo minuteTakingInfo;
    private final IAssistanceInfo websiteDisplayingInfo;
    private final IAssistanceInfo agendaShowingInfo;
    private final IAssistanceInfo fileDisplayingInfo;
    private final IAssistanceInfo locationUnlockingInfo;
    private final IAssistanceInfo devicePreparationInfo;
    private final IProjectBaseInfoFactory githubProjectBaseInfoFactory;

    public CoastGuardSampleDataSetFactory(
            IAssistanceInfo minuteTakingInfo,
            IAssistanceInfo websiteDisplayingInfo,
            IAssistanceInfo agendaShowingInfo,
            IAssistanceInfo fileDisplayingInfo,
            IAssistanceInfo locationUnlockingInfo,
            IAssistanceInfo devicePreparationInfo,
            IResolver<String, IProjectBaseInfoFactory> projectBaseInfoFactoryResolver) {
        super(ID);
        this.minuteTakingInfo = minuteTakingInfo;
        this.websiteDisplayingInfo = websiteDisplayingInfo;
        this.agendaShowingInfo = agendaShowingInfo;
        this.fileDisplayingInfo = fileDisplayingInfo;
        this.locationUnlockingInfo = locationUnlockingInfo;
        this.devicePreparationInfo = devicePreparationInfo;
        this.githubProjectBaseInfoFactory = projectBaseInfoFactoryResolver.resolve(GithubServiceConnector.SERVICE_ID);
    }

    @Override
    public Set<IWorkgroup> createWorkgroupSet() throws DataSetException {
        Set<IWorkgroup> workgroups = new HashSet<>();
        Set<PersonId> coastGuardMembers = new HashSet<>();
        coastGuardMembers.add(MEMBER_ID_ALICE);
        coastGuardMembers.add(MEMBER_ID_BEN);
        coastGuardMembers.add(MEMBER_ID_CHARLIE);
        workgroups.add(Workgroup.of(
                WORKGROUP_ID_COAST_GUARD,
                "Coast Guard",
                coastGuardMembers,
                this.githubProjectBaseInfoFactory.newInstance(ImmutableMap
                        .<String, String>builder()
                        .put(GithubProjectBaseInfo.PROJECT_BASE_PROPERTY_KEY_USER, "Kroetz")
                        .put(GithubProjectBaseInfo.PROJECT_BASE_PROPERTY_KEY_REPOSITORY, "coastGuardRepo")
                        .build())));
        return workgroups;
    }

    @Override
    public Set<IPerson> createWorkgroupMemberSet() throws DataSetException {
        Set<IPerson> workgroupMembers = new HashSet<>();
        workgroupMembers.add(Person.of(
                MEMBER_ID_ALICE,
                "Coast Guard Alice",
                "alice@coast-guard.com",
                PersonRole.TEAM_LEADER));
        workgroupMembers.add(Person.of(
                MEMBER_ID_BEN,
                "Coast Guard Ben",
                "ben@coast-guard.com",
                PersonRole.REGULAR_MEMBER));
        workgroupMembers.add(Person.of(
                MEMBER_ID_CHARLIE,
                "Coast Guard Charlie",
                "charlie@coast-guard.com",
                PersonRole.REGULAR_MEMBER));
        return workgroupMembers;
    }

    @Override
    public Set<IEvent> createEventSet() throws DataSetException {
        Set<IEvent> events = new HashSet<>();
        List<IAgendaItem> whaleEventAgenda = new ArrayList<>();
        whaleEventAgenda.add(AgendaItem.of("Show critical areas"));
        whaleEventAgenda.add(AgendaItem.of("Explain whale anatomy"));
        whaleEventAgenda.add(AgendaItem.of("Drink coffee"));
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
        events.add(Event.of(
                EVENT_ID_WHALES,
                "Meeting about preventing illegal whale hunting",
                WORKGROUP_ID_COAST_GUARD,
                whaleEventAgenda,
                whaleConfigs,
                timeBase.plusSeconds(0),
                timeBase.plusSeconds(300)));

        List<IAgendaItem> whirlpoolEventAgenda = new ArrayList<>();
        whirlpoolEventAgenda.add(AgendaItem.of("Explain how whirlpools develop"));
        whirlpoolEventAgenda.add(AgendaItem.of("Show how you can escape whirlpools"));
        whirlpoolEventAgenda.add(AgendaItem.of("Admire the fine weather"));
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
        events.add(Event.of(
                EVENT_ID_WHIRLPOOLS,
                "Meeting about dangers of whirlpools",
                WORKGROUP_ID_COAST_GUARD,
                whirlpoolEventAgenda,
                whirlpoolConfigs,
                timeBase.plusSeconds(360),
                timeBase.plusSeconds(660)));

        return events;
    }

    @Override
    public Set<ILocation> createLocationSet() throws DataSetException {
        Set<ILocation> locations = new HashSet<>();
        Set<DeviceId> blueLocationDevices = new HashSet<>();
        blueLocationDevices.add(DEVICE_ID_BLUE_DISPLAY_BIG);
        blueLocationDevices.add(DEVICE_ID_BLUE_DISPLAY_SMALL);
        blueLocationDevices.add(DEVICE_ID_BLUE_DISPLAY_BEAMER);
        blueLocationDevices.add(DEVICE_ID_BLUE_MICROPHONE);
        locations.add(Location.of(
                LOCATION_ID_BLUE,
                "Coast guard workplace",
                blueLocationDevices));
        return locations;
    }

    @Override
    public Set<IDevice> createDeviceSet() throws DataSetException {
        Set<IDevice> devices = new HashSet<>();
        devices.add(Device.of(
                DEVICE_ID_BLUE_DISPLAY_BIG,
                DummyDisplayAdapter.DEVICE_TYPE,
                format("Big main display at location \"%s\"", LOCATION_ID_BLUE),
                DELEGATE_ID_BLUE));
        devices.add(Device.of(
                DEVICE_ID_BLUE_DISPLAY_SMALL,
                DummyDisplayAdapter.DEVICE_TYPE,
                format("Small display next to the door at location \"%s\"", LOCATION_ID_BLUE),
                DELEGATE_ID_BLUE));
        devices.add(Device.of(
                DEVICE_ID_BLUE_DISPLAY_BEAMER,
                DummyBeamerAdapter.DEVICE_TYPE,
                format("Beamer at location \"%s\"", LOCATION_ID_BLUE),
                DELEGATE_ID_BLUE));
        devices.add(Device.of(
                DEVICE_ID_BLUE_MICROPHONE,
                ThinkpadP50InternalMicrophoneAdapter.DEVICE_TYPE,
                format("Microphone at location \"%s\"", LOCATION_ID_BLUE),
                DELEGATE_ID_BLUE));
        devices.add(Device.of(
                DEVICE_ID_BLUE_WEB_BROWSER,
                "firefox",
                format("Web browser at location \"%s\"", LOCATION_ID_BLUE),
                DELEGATE_ID_BLUE));
        devices.add(Device.of(
                DEVICE_ID_BLUE_POWER_POINT,
                "powerPoint",
                format("PowerPoint at location \"%s\"", LOCATION_ID_BLUE),
                DELEGATE_ID_BLUE));
        return devices;
    }
}
