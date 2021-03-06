package de.qaware.smartlab.data.set.factory;

import com.google.common.collect.ImmutableMap;
import de.qaware.smartlab.actuator.adapter.adapters.beamer.DummyBeamerAdapter;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.info.generic.IProjectBaseInfoFactory;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.info.github.GithubInfo;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.service.github.GithubAdapter;
import de.qaware.smartlab.assistance.assistances.info.agendashowing.AgendaShowingInfo;
import de.qaware.smartlab.assistance.assistances.info.devicepreparation.DevicePreparationInfo;
import de.qaware.smartlab.assistance.assistances.info.generic.IAssistanceInfo;
import de.qaware.smartlab.core.data.actuator.Actuator;
import de.qaware.smartlab.core.data.actuator.ActuatorId;
import de.qaware.smartlab.core.data.actuator.IActuator;
import de.qaware.smartlab.core.assistance.IAssistanceConfiguration;
import de.qaware.smartlab.core.data.event.*;
import de.qaware.smartlab.core.resolver.IResolver;
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
public class AstronautsSampleDataSetFactory extends AbstractDataSetFactory {

    public static final String ID = "sample-data-astronauts";

    public static final WorkgroupId WORKGROUP_ID_ASTRONAUTS = WorkgroupId.of("astronauts");
    public static final PersonId MEMBER_ID_ALEX = PersonId.of("alex");
    public static final PersonId MEMBER_ID_BEVERLY = PersonId.of("beverly");
    public static final PersonId MEMBER_ID_CHARLOTTE = PersonId.of("charlotte");
    public static final LocationId LOCATION_ID_BLACK = LocationId.of("astronauts-workplace");
    public static final ActuatorId ACTUATOR_ID_BLACK_DISPLAY_BEAMER = ActuatorId.of("black-display-beamer");
    public static final ActuatorId ACTUATOR_ID_BLACK_WEB_BROWSER = ActuatorId.of("black-web-browser");
    public static final EventId EVENT_ID_MARS = EventId.of("mars", LOCATION_ID_BLACK);
    public static final String DELEGATE_ID_BLACK = "smart-lab-black-delegate-microservice";

    private final IAssistanceInfo agendaShowingInfo;
    private final IAssistanceInfo locationUnlockingInfo;
    private final IAssistanceInfo devicePreparationInfo;
    private final IProjectBaseInfoFactory githubInfoFactory;

    public AstronautsSampleDataSetFactory(
            IAssistanceInfo agendaShowingInfo,
            IAssistanceInfo locationUnlockingInfo,
            IAssistanceInfo devicePreparationInfo,
            IResolver<String, IProjectBaseInfoFactory> projectBaseInfoFactoryResolver) {
        super(ID);
        this.agendaShowingInfo = agendaShowingInfo;
        this.locationUnlockingInfo = locationUnlockingInfo;
        this.devicePreparationInfo = devicePreparationInfo;
        this.githubInfoFactory = projectBaseInfoFactoryResolver.resolve(GithubAdapter.ACTUATOR_TYPE);
    }

    @Override
    public Set<IWorkgroup> createWorkgroupSet() throws DataException {
        Set<IWorkgroup> workgroups = new HashSet<>();
        Set<PersonId> astronautsMembers = new HashSet<>();
        astronautsMembers.add(MEMBER_ID_ALEX);
        astronautsMembers.add(MEMBER_ID_BEVERLY);
        astronautsMembers.add(MEMBER_ID_CHARLOTTE);
        workgroups.add(Workgroup.of(
                WORKGROUP_ID_ASTRONAUTS,
                "Astronauts",
                astronautsMembers,
                this.githubInfoFactory.newInstance(ImmutableMap
                        .<String, String>builder()
                        .put(GithubInfo.PROJECT_BASE_PROPERTY_KEY_USER, "Kroetz")
                        .put(GithubInfo.PROJECT_BASE_PROPERTY_KEY_REPOSITORY, "astronautsRepo")
                        .build())));
        return workgroups;
    }

    @Override
    public Set<IPerson> createWorkgroupMemberSet() throws DataException {
        Set<IPerson> workgroupMembers = new HashSet<>();
        workgroupMembers.add(Person.of(
                MEMBER_ID_ALEX,
                "Astronaut Alex",
                "alex@astronauts.com",
                PersonRole.TEAM_LEADER));
        workgroupMembers.add(Person.of(
                MEMBER_ID_BEVERLY,
                "Astronaut Beverly",
                "beverly@astronauts.com",
                PersonRole.REGULAR_MEMBER));
        workgroupMembers.add(Person.of(
                MEMBER_ID_CHARLOTTE,
                "Astronaut Charlotte",
                "charlotte@astronauts.com",
                PersonRole.REGULAR_MEMBER));
        return workgroupMembers;
    }

    @Override
    public Set<IEvent> createEventSet() throws DataException {
        Set<IEvent> events = new HashSet<>();
        List<IAgendaItem> marsEventAgenda = new ArrayList<>();
        marsEventAgenda.add(AgendaItem.of("Calculate journey duration"));
        marsEventAgenda.add(AgendaItem.of("Discuss who may press the launch button of the rocket"));
        marsEventAgenda.add(AgendaItem.of("Complain that this is all rocket science"));
        Set<IAssistanceConfiguration> configs = new HashSet<>();
        configs.add(this.agendaShowingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(AgendaShowingInfo.Configuration.CONFIG_PROPERTY_KEY_WEB_BROWSER_ID, ACTUATOR_ID_BLACK_WEB_BROWSER.getIdValue())
                .put(AgendaShowingInfo.Configuration.CONFIG_PROPERTY_KEY_DISPLAY_ID, ACTUATOR_ID_BLACK_DISPLAY_BEAMER.getIdValue())
                .build()));
        configs.add(this.locationUnlockingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .build()));
        configs.add(this.devicePreparationInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(DevicePreparationInfo.Configuration.CONFIG_PROPERTY_KEY_DEVICE_ID, ACTUATOR_ID_BLACK_DISPLAY_BEAMER.getIdValue())
                .build()));
        events.add(Event.of(
                EVENT_ID_MARS,
                "Meeting about travelling to Mars",
                WORKGROUP_ID_ASTRONAUTS,
                marsEventAgenda,
                configs,
                timeBase.plusSeconds(0),
                timeBase.plusSeconds(300)));
        return events;
    }

    @Override
    public Set<ILocation> createLocationSet() throws DataException {
        Set<ILocation> locations = new HashSet<>();
        Set<ActuatorId> blackLocationActuators = createActuatorSet().stream()
                .map(IActuator::getId)
                .collect(toSet());
        locations.add(Location.of(
                LOCATION_ID_BLACK,
                "Astronauts workplace",
                blackLocationActuators));
        return locations;
    }

    @Override
    public Set<IActuator> createActuatorSet() throws DataException {
        Set<IActuator> actuators = new HashSet<>();
        actuators.add(Actuator.of(
                ACTUATOR_ID_BLACK_DISPLAY_BEAMER,
                DummyBeamerAdapter.ACTUATOR_TYPE,
                format("Beamer at location \"%s\"", LOCATION_ID_BLACK),
                DELEGATE_ID_BLACK));
        return actuators;
    }
}
