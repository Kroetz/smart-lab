package de.qaware.smartlab.data.set.factory;

import com.google.common.collect.ImmutableMap;
import de.qaware.smartlab.actuator.adapter.adapters.beamer.DummyBeamerAdapter;
import de.qaware.smartlab.actuator.adapter.adapters.display.DummyDisplayAdapter;
import de.qaware.smartlab.actuator.adapter.adapters.microphone.ThinkpadP50InternalMicrophoneAdapter;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.info.generic.IProjectBaseInfoFactory;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.info.github.GithubInfo;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.service.github.GithubAdapter;
import de.qaware.smartlab.assistance.assistances.info.agendashowing.AgendaShowingInfo;
import de.qaware.smartlab.assistance.assistances.info.devicepreparation.DevicePreparationInfo;
import de.qaware.smartlab.assistance.assistances.info.filedisplaying.FileDisplayingInfo;
import de.qaware.smartlab.assistance.assistances.info.generic.IAssistanceInfo;
import de.qaware.smartlab.assistance.assistances.info.minutetaking.MinuteTakingInfo;
import de.qaware.smartlab.assistance.assistances.info.websitedisplaying.WebsiteDisplayingInfo;
import de.qaware.smartlab.core.data.actuator.Actuator;
import de.qaware.smartlab.core.data.actuator.ActuatorId;
import de.qaware.smartlab.core.data.actuator.IActuator;
import de.qaware.smartlab.core.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlab.core.data.event.*;
import de.qaware.smartlab.core.data.generic.IResolver;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.location.Location;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.person.IPerson;
import de.qaware.smartlab.core.data.person.Person;
import de.qaware.smartlab.core.data.person.PersonId;
import de.qaware.smartlab.core.data.person.PersonRole;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.data.workgroup.Workgroup;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.exception.data.DataException;
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
    public static final ActuatorId ACTUATOR_ID_BLUE_DISPLAY_BIG = ActuatorId.of("blue-display-big");
    public static final ActuatorId ACTUATOR_ID_BLUE_DISPLAY_SMALL = ActuatorId.of("blue-display-small");
    public static final ActuatorId ACTUATOR_ID_BLUE_DISPLAY_BEAMER = ActuatorId.of("blue-display-beamer");
    public static final ActuatorId ACTUATOR_ID_BLUE_MICROPHONE = ActuatorId.of("blue-microphone");
    public static final ActuatorId ACTUATOR_ID_BLUE_WEB_BROWSER = ActuatorId.of("blue-web-browser");
    public static final ActuatorId ACTUATOR_ID_BLUE_POWER_POINT = ActuatorId.of("blue-power-point");
    public static final EventId EVENT_ID_WHALES = EventId.of("whales", LOCATION_ID_BLUE);
    public static final EventId EVENT_ID_WHIRLPOOLS = EventId.of("whirlpools", LOCATION_ID_BLUE);
    public static final String DELEGATE_ID_BLUE = "smart-lab-blue-delegate-microservice";

    private final IAssistanceInfo minuteTakingInfo;
    private final IAssistanceInfo websiteDisplayingInfo;
    private final IAssistanceInfo agendaShowingInfo;
    private final IAssistanceInfo fileDisplayingInfo;
    private final IAssistanceInfo locationUnlockingInfo;
    private final IAssistanceInfo devicePreparationInfo;
    private final IProjectBaseInfoFactory githubInfoFactory;

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
        this.githubInfoFactory = projectBaseInfoFactoryResolver.resolve(GithubAdapter.ACTUATOR_TYPE);
    }

    @Override
    public Set<IWorkgroup> createWorkgroupSet() throws DataException {
        Set<IWorkgroup> workgroups = new HashSet<>();
        Set<PersonId> coastGuardMembers = new HashSet<>();
        coastGuardMembers.add(MEMBER_ID_ALICE);
        coastGuardMembers.add(MEMBER_ID_BEN);
        coastGuardMembers.add(MEMBER_ID_CHARLIE);
        workgroups.add(Workgroup.of(
                WORKGROUP_ID_COAST_GUARD,
                "Coast Guard",
                coastGuardMembers,
                this.githubInfoFactory.newInstance(ImmutableMap
                        .<String, String>builder()
                        .put(GithubInfo.PROJECT_BASE_PROPERTY_KEY_USER, "Kroetz")
                        .put(GithubInfo.PROJECT_BASE_PROPERTY_KEY_REPOSITORY, "coastGuardRepo")
                        .build())));
        return workgroups;
    }

    @Override
    public Set<IPerson> createWorkgroupMemberSet() throws DataException {
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
    public Set<IEvent> createEventSet() throws DataException {
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
                .put(MinuteTakingInfo.Configuration.CONFIG_PROPERTY_KEY_MICROPHONE_ID, ACTUATOR_ID_BLUE_MICROPHONE.getIdValue())
                .build()));
        whaleConfigs.add(this.websiteDisplayingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(WebsiteDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_URL, "https://en.wikipedia.org/wiki/Whale")
                .put(WebsiteDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_WEB_BROWSER_ID, ACTUATOR_ID_BLUE_WEB_BROWSER.getIdValue())
                .put(WebsiteDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_DISPLAY_ID, ACTUATOR_ID_BLUE_DISPLAY_BIG.getIdValue())
                .build()));
        whaleConfigs.add(this.agendaShowingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(AgendaShowingInfo.Configuration.CONFIG_PROPERTY_KEY_WEB_BROWSER_ID, ACTUATOR_ID_BLUE_WEB_BROWSER.getIdValue())
                .put(AgendaShowingInfo.Configuration.CONFIG_PROPERTY_KEY_DISPLAY_ID, ACTUATOR_ID_BLUE_DISPLAY_SMALL.getIdValue())
                .build()));
        whaleConfigs.add(this.fileDisplayingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(FileDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_FILE, "slides/whale_slides.pptx")
                .put(FileDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_PROGRAM_ID, ACTUATOR_ID_BLUE_POWER_POINT.getIdValue())
                .put(FileDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_DISPLAY_ID, ACTUATOR_ID_BLUE_DISPLAY_BEAMER.getIdValue())
                .build()));
        whaleConfigs.add(this.locationUnlockingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .build()));
        whaleConfigs.add(this.devicePreparationInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(DevicePreparationInfo.Configuration.CONFIG_PROPERTY_KEY_DEVICE_ID, ACTUATOR_ID_BLUE_DISPLAY_BIG.getIdValue())
                .build()));
        whaleConfigs.add(this.devicePreparationInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(DevicePreparationInfo.Configuration.CONFIG_PROPERTY_KEY_DEVICE_ID, ACTUATOR_ID_BLUE_DISPLAY_SMALL.getIdValue())
                .build()));
        whaleConfigs.add(this.devicePreparationInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(DevicePreparationInfo.Configuration.CONFIG_PROPERTY_KEY_DEVICE_ID, ACTUATOR_ID_BLUE_DISPLAY_BEAMER.getIdValue())
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
                .put(MinuteTakingInfo.Configuration.CONFIG_PROPERTY_KEY_MICROPHONE_ID, ACTUATOR_ID_BLUE_MICROPHONE.getIdValue())
                .build()));
        whirlpoolConfigs.add(this.websiteDisplayingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(WebsiteDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_URL, "https://en.wikipedia.org/wiki/Whirlpool")
                .put(WebsiteDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_WEB_BROWSER_ID, ACTUATOR_ID_BLUE_WEB_BROWSER.getIdValue())
                .put(WebsiteDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_DISPLAY_ID, ACTUATOR_ID_BLUE_DISPLAY_BIG.getIdValue())
                .build()));
        whirlpoolConfigs.add(this.agendaShowingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(AgendaShowingInfo.Configuration.CONFIG_PROPERTY_KEY_WEB_BROWSER_ID, ACTUATOR_ID_BLUE_WEB_BROWSER.getIdValue())
                .put(AgendaShowingInfo.Configuration.CONFIG_PROPERTY_KEY_DISPLAY_ID, ACTUATOR_ID_BLUE_DISPLAY_SMALL.getIdValue())
                .build()));
        whirlpoolConfigs.add(this.fileDisplayingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(FileDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_FILE, "slides/whirlpool_slides.pptx")
                .put(FileDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_PROGRAM_ID, ACTUATOR_ID_BLUE_POWER_POINT.getIdValue())
                .put(FileDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_DISPLAY_ID, ACTUATOR_ID_BLUE_DISPLAY_BEAMER.getIdValue())
                .build()));
        whirlpoolConfigs.add(this.locationUnlockingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .build()));
        whirlpoolConfigs.add(this.devicePreparationInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(DevicePreparationInfo.Configuration.CONFIG_PROPERTY_KEY_DEVICE_ID, ACTUATOR_ID_BLUE_DISPLAY_BIG.getIdValue())
                .build()));
        whirlpoolConfigs.add(this.devicePreparationInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(DevicePreparationInfo.Configuration.CONFIG_PROPERTY_KEY_DEVICE_ID, ACTUATOR_ID_BLUE_DISPLAY_SMALL.getIdValue())
                .build()));
        whirlpoolConfigs.add(this.devicePreparationInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(DevicePreparationInfo.Configuration.CONFIG_PROPERTY_KEY_DEVICE_ID, ACTUATOR_ID_BLUE_DISPLAY_BEAMER.getIdValue())
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
    public Set<ILocation> createLocationSet() throws DataException {
        Set<ILocation> locations = new HashSet<>();
        Set<ActuatorId> blueLocationActuators = new HashSet<>();
        blueLocationActuators.add(ACTUATOR_ID_BLUE_DISPLAY_BIG);
        blueLocationActuators.add(ACTUATOR_ID_BLUE_DISPLAY_SMALL);
        blueLocationActuators.add(ACTUATOR_ID_BLUE_DISPLAY_BEAMER);
        blueLocationActuators.add(ACTUATOR_ID_BLUE_MICROPHONE);
        locations.add(Location.of(
                LOCATION_ID_BLUE,
                "Coast guard workplace",
                blueLocationActuators));
        return locations;
    }

    @Override
    public Set<IActuator> createActuatorSet() throws DataException {
        Set<IActuator> actuators = new HashSet<>();
        actuators.add(Actuator.of(
                ACTUATOR_ID_BLUE_DISPLAY_BIG,
                DummyDisplayAdapter.ACTUATOR_TYPE,
                format("Big main display at location \"%s\"", LOCATION_ID_BLUE),
                DELEGATE_ID_BLUE));
        actuators.add(Actuator.of(
                ACTUATOR_ID_BLUE_DISPLAY_SMALL,
                DummyDisplayAdapter.ACTUATOR_TYPE,
                format("Small display next to the door at location \"%s\"", LOCATION_ID_BLUE),
                DELEGATE_ID_BLUE));
        actuators.add(Actuator.of(
                ACTUATOR_ID_BLUE_DISPLAY_BEAMER,
                DummyBeamerAdapter.ACTUATOR_TYPE,
                format("Beamer at location \"%s\"", LOCATION_ID_BLUE),
                DELEGATE_ID_BLUE));
        actuators.add(Actuator.of(
                ACTUATOR_ID_BLUE_MICROPHONE,
                ThinkpadP50InternalMicrophoneAdapter.ACTUATOR_TYPE,
                format("Microphone at location \"%s\"", LOCATION_ID_BLUE),
                DELEGATE_ID_BLUE));
        actuators.add(Actuator.of(
                ACTUATOR_ID_BLUE_WEB_BROWSER,
                "firefox",
                format("Web browser at location \"%s\"", LOCATION_ID_BLUE),
                DELEGATE_ID_BLUE));
        actuators.add(Actuator.of(
                ACTUATOR_ID_BLUE_POWER_POINT,
                "powerPoint",
                format("PowerPoint at location \"%s\"", LOCATION_ID_BLUE),
                DELEGATE_ID_BLUE));
        return actuators;
    }
}
