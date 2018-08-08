package de.qaware.smartlab.data.set.factory;

import com.google.common.collect.ImmutableMap;
import de.qaware.smartlab.actuator.adapter.adapters.display.DummyDisplayAdapter;
import de.qaware.smartlab.actuator.adapter.adapters.microphone.DummyMicrophoneAdapter;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.info.generic.IProjectBaseInfoFactory;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.info.github.GithubInfo;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.service.github.GithubAdapter;
import de.qaware.smartlab.assistance.assistances.info.agendashowing.AgendaShowingInfo;
import de.qaware.smartlab.assistance.assistances.info.devicepreparation.DevicePreparationInfo;
import de.qaware.smartlab.assistance.assistances.info.generic.IAssistanceInfo;
import de.qaware.smartlab.assistance.assistances.info.minutetaking.MinuteTakingInfo;
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
public class FireFightersSampleDataSetFactory extends AbstractDataSetFactory {

    public static final String ID = "sample-data-fire-fighters";

    public static final WorkgroupId WORKGROUP_ID_FIRE_FIGHTERS = WorkgroupId.of("fire-fighters");
    public static final PersonId MEMBER_ID_ANTHONY = PersonId.of("fire-fighter-anthony");
    public static final PersonId MEMBER_ID_BRUCE = PersonId.of("fire-fighter-bruce");
    public static final PersonId MEMBER_ID_CARLOS = PersonId.of("fire-fighter-carlos");
    public static final LocationId LOCATION_ID_RED = LocationId.of("fire-fighters-workplace");
    public static final ActuatorId ACTUATOR_ID_RED_DISPLAY_BIG = ActuatorId.of("red-display-big");
    public static final ActuatorId ACTUATOR_ID_RED_MICROPHONE = ActuatorId.of("red-microphone");
    public static final ActuatorId ACTUATOR_ID_RED_WEB_BROWSER = ActuatorId.of("red-web-browser");
    public static final EventId EVENT_ID_TRUCK = EventId.of("truck", LOCATION_ID_RED);
    public static final String DELEGATE_ID_RED = "smart-lab-red-delegate-microservice";

    private final IAssistanceInfo minuteTakingInfo;
    private final IAssistanceInfo agendaShowingInfo;
    private final IAssistanceInfo locationUnlockingInfo;
    private final IAssistanceInfo devicePreparationInfo;
    private final IProjectBaseInfoFactory githubInfoFactory;

    public FireFightersSampleDataSetFactory(
            IAssistanceInfo minuteTakingInfo,
            IAssistanceInfo agendaShowingInfo,
            IAssistanceInfo locationUnlockingInfo,
            IAssistanceInfo devicePreparationInfo,
            IResolver<String, IProjectBaseInfoFactory> projectBaseInfoFactoryResolver) {
        super(ID);
        this.minuteTakingInfo = minuteTakingInfo;
        this.agendaShowingInfo = agendaShowingInfo;
        this.locationUnlockingInfo = locationUnlockingInfo;
        this.devicePreparationInfo = devicePreparationInfo;
        this.githubInfoFactory = projectBaseInfoFactoryResolver.resolve(GithubAdapter.ACTUATOR_TYPE);
    }

    @Override
    public Set<IWorkgroup> createWorkgroupSet() throws DataSetException {
        Set<IWorkgroup> workgroups = new HashSet<>();
        Set<PersonId> fireFighterMembers = new HashSet<>();
        fireFighterMembers.add(MEMBER_ID_ANTHONY);
        fireFighterMembers.add(MEMBER_ID_BRUCE);
        fireFighterMembers.add(MEMBER_ID_CARLOS);
        workgroups.add(Workgroup.of(
                WORKGROUP_ID_FIRE_FIGHTERS,
                "Fire Fighters",
                fireFighterMembers,
                this.githubInfoFactory.newInstance(ImmutableMap
                        .<String, String>builder()
                        .put(GithubInfo.PROJECT_BASE_PROPERTY_KEY_USER, "Kroetz")
                        .put(GithubInfo.PROJECT_BASE_PROPERTY_KEY_REPOSITORY, "fireFightersRepo")
                        .build())));
        return workgroups;
    }

    @Override
    public Set<IPerson> createWorkgroupMemberSet() throws DataSetException {
        Set<IPerson> workgroupMembers = new HashSet<>();
        workgroupMembers.add(Person.of(
                MEMBER_ID_ANTHONY,
                "Fire Fighter Anthony",
                "anthony@fire-fighters.com",
                PersonRole.TEAM_LEADER));
        workgroupMembers.add(Person.of(
                MEMBER_ID_BRUCE,
                "Fire Fighter Bruce",
                "bruce@fire-fighters.com",
                PersonRole.REGULAR_MEMBER));
        workgroupMembers.add(Person.of(
                MEMBER_ID_CARLOS,
                "Fire Fighter Carlos",
                "carlos@fire-fighters.com",
                PersonRole.REGULAR_MEMBER));
        return workgroupMembers;
    }

    @Override
    public Set<IEvent> createEventSet() throws DataSetException {
        Set<IEvent> events = new HashSet<>();
        List<IAgendaItem> truckEventAgenda = new ArrayList<>();
        truckEventAgenda.add(AgendaItem.of("Show how bad the old truck is"));
        truckEventAgenda.add(AgendaItem.of("Show how great the new truck is"));
        truckEventAgenda.add(AgendaItem.of("Discuss how to pay for the new truck"));
        Set<IAssistanceConfiguration> configs = new HashSet<>();
        configs.add(this.minuteTakingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(MinuteTakingInfo.Configuration.CONFIG_PROPERTY_KEY_SPOKEN_LANGUAGE, Language.EN_US.toString())
                .put(MinuteTakingInfo.Configuration.CONFIG_PROPERTY_KEY_UPLOAD_DIR, "/sampleDataMinutes")
                .put(MinuteTakingInfo.Configuration.CONFIG_PROPERTY_KEY_MICROPHONE_ID, ACTUATOR_ID_RED_MICROPHONE.getIdValue())
                .build()));
        configs.add(this.agendaShowingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(AgendaShowingInfo.Configuration.CONFIG_PROPERTY_KEY_WEB_BROWSER_ID, ACTUATOR_ID_RED_WEB_BROWSER.getIdValue())
                .put(AgendaShowingInfo.Configuration.CONFIG_PROPERTY_KEY_DISPLAY_ID, ACTUATOR_ID_RED_DISPLAY_BIG.getIdValue())
                .build()));
        configs.add(this.locationUnlockingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .build()));
        configs.add(this.devicePreparationInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(DevicePreparationInfo.Configuration.CONFIG_PROPERTY_KEY_DEVICE_ID, ACTUATOR_ID_RED_DISPLAY_BIG.getIdValue())
                .build()));
        events.add(Event.of(
                EVENT_ID_TRUCK,
                "Meeting about the new fire truck \"Fire Exterminator 3000\"",
                WORKGROUP_ID_FIRE_FIGHTERS,
                truckEventAgenda,
                configs,
                timeBase.plusSeconds(120),
                timeBase.plusSeconds(420)));
        return events;
    }

    @Override
    public Set<ILocation> createLocationSet() throws DataSetException {
        Set<ILocation> locations = new HashSet<>();
        Set<ActuatorId> redLocationActuators = new HashSet<>();
        redLocationActuators.add(ACTUATOR_ID_RED_MICROPHONE);
        locations.add(Location.of(
                LOCATION_ID_RED,
                "Fire fighters workplace",
                redLocationActuators));
        return locations;
    }

    @Override
    public Set<IActuator> createActuatorSet() throws DataSetException {
        Set<IActuator> actuators = new HashSet<>();
        actuators.add(Actuator.of(
                ACTUATOR_ID_RED_DISPLAY_BIG,
                DummyDisplayAdapter.ACTUATOR_TYPE,
                format("Big display at location \"%s\"", LOCATION_ID_RED),
                DELEGATE_ID_RED));
        actuators.add(Actuator.of(
                ACTUATOR_ID_RED_MICROPHONE,
                DummyMicrophoneAdapter.ACTUATOR_TYPE,
                format("Microphone at location \"%s\"", LOCATION_ID_RED),
                DELEGATE_ID_RED));
        return actuators;
    }
}
