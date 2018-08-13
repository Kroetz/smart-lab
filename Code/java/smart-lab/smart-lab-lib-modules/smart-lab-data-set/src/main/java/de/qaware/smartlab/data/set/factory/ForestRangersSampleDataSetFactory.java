package de.qaware.smartlab.data.set.factory;

import com.google.common.collect.ImmutableMap;
import de.qaware.smartlab.actuator.adapter.adapters.display.DummyDisplayAdapter;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.info.generic.IProjectBaseInfoFactory;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.info.github.GithubInfo;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.service.github.GithubAdapter;
import de.qaware.smartlab.assistance.assistances.info.agendashowing.AgendaShowingInfo;
import de.qaware.smartlab.assistance.assistances.info.devicepreparation.DevicePreparationInfo;
import de.qaware.smartlab.assistance.assistances.info.generic.IAssistanceInfo;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;
import static java.util.stream.Collectors.toSet;

@Component
@Slf4j
public class ForestRangersSampleDataSetFactory extends AbstractDataSetFactory {

    public static final String ID = "sample-data-forest-rangers";

    public static final WorkgroupId WORKGROUP_ID_FOREST_RANGERS = WorkgroupId.of("forest-rangers");
    public static final PersonId MEMBER_ID_ANNA = PersonId.of("forest-ranger-anna");
    public static final PersonId MEMBER_ID_BARRY = PersonId.of("forest-ranger-barry");
    public static final PersonId MEMBER_ID_CAROLINE = PersonId.of("forest-ranger-caroline");
    public static final LocationId LOCATION_ID_GREEN = LocationId.of("forest-rangers-workplace");
    public static final ActuatorId ACTUATOR_ID_GREEN_DISPLAY_BIG = ActuatorId.of("green-display-big");
    public static final ActuatorId ACTUATOR_ID_GREEN_DISPLAY_SMALL = ActuatorId.of("green-display-small");
    public static final ActuatorId ACTUATOR_ID_GREEN_WEB_BROWSER = ActuatorId.of("green-web-browser");
    public static final EventId EVENT_ID_BARK_BEETLE = EventId.of("bark-beetle", LOCATION_ID_GREEN);
    public static final String DELEGATE_ID_GREEN = "smart-lab-green-delegate-microservice";

    private final IAssistanceInfo websiteDisplayingInfo;
    private final IAssistanceInfo agendaShowingInfo;
    private final IAssistanceInfo locationUnlockingInfo;
    private final IAssistanceInfo devicePreparationInfo;
    private final IProjectBaseInfoFactory githubInfoFactory;

    public ForestRangersSampleDataSetFactory(
            IAssistanceInfo websiteDisplayingInfo,
            IAssistanceInfo agendaShowingInfo,
            IAssistanceInfo locationUnlockingInfo,
            IAssistanceInfo devicePreparationInfo,
            IResolver<String, IProjectBaseInfoFactory> projectBaseInfoFactoryResolver) {
        super(ID);
        this.websiteDisplayingInfo = websiteDisplayingInfo;
        this.agendaShowingInfo = agendaShowingInfo;
        this.locationUnlockingInfo = locationUnlockingInfo;
        this.devicePreparationInfo = devicePreparationInfo;
        this.githubInfoFactory = projectBaseInfoFactoryResolver.resolve(GithubAdapter.ACTUATOR_TYPE);
    }

    @Override
    public Set<IWorkgroup> createWorkgroupSet() throws DataException {
        Set<IWorkgroup> workgroups = new HashSet<>();
        Set<PersonId> forestRangerMembers = new HashSet<>();
        forestRangerMembers.add(MEMBER_ID_ANNA);
        forestRangerMembers.add(MEMBER_ID_BARRY);
        forestRangerMembers.add(MEMBER_ID_CAROLINE);
        workgroups.add(Workgroup.of(
                WORKGROUP_ID_FOREST_RANGERS,
                "Forest Rangers",
                forestRangerMembers,
                this.githubInfoFactory.newInstance(ImmutableMap
                        .<String, String>builder()
                        .put(GithubInfo.PROJECT_BASE_PROPERTY_KEY_USER, "Kroetz")
                        .put(GithubInfo.PROJECT_BASE_PROPERTY_KEY_REPOSITORY, "forestRangersRepo")
                        .build())));
        return workgroups;
    }

    @Override
    public Set<IPerson> createWorkgroupMemberSet() throws DataException {
        Set<IPerson> workgroupMembers = new HashSet<>();
        workgroupMembers.add(Person.of(
                MEMBER_ID_ANNA,
                "Forest Ranger Anna",
                "anna@forest-rangers.com",
                PersonRole.TEAM_LEADER));
        workgroupMembers.add(Person.of(
                MEMBER_ID_BARRY,
                "Forest Ranger Barry",
                "barry@forest-rangers.com",
                PersonRole.REGULAR_MEMBER));
        workgroupMembers.add(Person.of(
                MEMBER_ID_CAROLINE,
                "Forest Ranger Caroline",
                "caroline@forest-rangers.com",
                PersonRole.REGULAR_MEMBER));
        return workgroupMembers;
    }

    @Override
    public Set<IEvent> createEventSet() throws DataException {
        Set<IEvent> events = new HashSet<>();
        List<IAgendaItem> barkBeetleEventAgenda = new ArrayList<>();
        barkBeetleEventAgenda.add(AgendaItem.of("Show potential damage"));
        barkBeetleEventAgenda.add(AgendaItem.of("Show increase in population"));
        barkBeetleEventAgenda.add(AgendaItem.of("Laugh together"));
        Set<IAssistanceConfiguration> configs = new HashSet<>();
        configs.add(this.websiteDisplayingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(WebsiteDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_URL, "https://en.wikipedia.org/wiki/Bark_beetle")
                .put(WebsiteDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_WEB_BROWSER_ID, ACTUATOR_ID_GREEN_WEB_BROWSER.getIdValue())
                .put(WebsiteDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_DISPLAY_ID, ACTUATOR_ID_GREEN_DISPLAY_BIG.getIdValue())
                .build()));
        configs.add(this.agendaShowingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(AgendaShowingInfo.Configuration.CONFIG_PROPERTY_KEY_WEB_BROWSER_ID, ACTUATOR_ID_GREEN_WEB_BROWSER.getIdValue())
                .put(AgendaShowingInfo.Configuration.CONFIG_PROPERTY_KEY_DISPLAY_ID, ACTUATOR_ID_GREEN_DISPLAY_SMALL.getIdValue())
                .build()));
        configs.add(this.locationUnlockingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .build()));
        configs.add(this.devicePreparationInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(DevicePreparationInfo.Configuration.CONFIG_PROPERTY_KEY_DEVICE_ID, ACTUATOR_ID_GREEN_DISPLAY_BIG.getIdValue())
                .build()));
        configs.add(this.devicePreparationInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(DevicePreparationInfo.Configuration.CONFIG_PROPERTY_KEY_DEVICE_ID, ACTUATOR_ID_GREEN_DISPLAY_SMALL.getIdValue())
                .build()));
        events.add(Event.of(
                EVENT_ID_BARK_BEETLE,
                "Meeting about the danger of the bark beetle",
                WORKGROUP_ID_FOREST_RANGERS,
                barkBeetleEventAgenda,
                configs,
                timeBase.plusSeconds(60),
                timeBase.plusSeconds(360)));
        return events;
    }

    @Override
    public Set<ILocation> createLocationSet() throws DataException {
        Set<ILocation> locations = new HashSet<>();
        Set<ActuatorId> greenLocationActuators = createActuatorSet().stream()
                .map(IActuator::getId)
                .collect(toSet());
        locations.add(Location.of(
                LOCATION_ID_GREEN,
                "Forest rangers workplace",
                greenLocationActuators));
        return locations;
    }

    @Override
    public Set<IActuator> createActuatorSet() throws DataException {
        Set<IActuator> actuators = new HashSet<>();
        actuators.add(Actuator.of(
                ACTUATOR_ID_GREEN_DISPLAY_BIG,
                DummyDisplayAdapter.ACTUATOR_TYPE,
                format("Big main display at location \"%s\"", LOCATION_ID_GREEN),
                DELEGATE_ID_GREEN));
        actuators.add(Actuator.of(
                ACTUATOR_ID_GREEN_DISPLAY_SMALL,
                DummyDisplayAdapter.ACTUATOR_TYPE,
                format("Small display next to the door at location \"%s\"", LOCATION_ID_GREEN),
                DELEGATE_ID_GREEN));
        actuators.add(Actuator.of(
                ACTUATOR_ID_GREEN_WEB_BROWSER,
                "chrome",
                format("Web browser at location \"%s\"", LOCATION_ID_GREEN),
                DELEGATE_ID_GREEN));
        return actuators;
    }
}
