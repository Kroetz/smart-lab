package de.qaware.smartlabdataset.factory;

import com.google.common.collect.ImmutableMap;
import de.qaware.smartlabactuatoradapter.actuator.beamer.DummyBeamerAdapter;
import de.qaware.smartlabactuatoradapter.actuator.projectbase.info.generic.IProjectBaseInfoFactory;
import de.qaware.smartlabactuatoradapter.actuator.projectbase.info.github.GithubProjectBaseInfo;
import de.qaware.smartlabactuatoradapter.actuator.projectbase.service.github.GithubServiceConnector;
import de.qaware.smartlabassistance.assistance.info.agendashowing.AgendaShowingInfo;
import de.qaware.smartlabassistance.assistance.info.devicepreparation.DevicePreparationInfo;
import de.qaware.smartlabassistance.assistance.info.generic.IAssistanceInfo;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.device.Device;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.generic.IResolver;
import de.qaware.smartlabcore.data.location.ILocation;
import de.qaware.smartlabcore.data.location.Location;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.data.meeting.*;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.Person;
import de.qaware.smartlabcore.data.person.PersonId;
import de.qaware.smartlabcore.data.person.PersonRole;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.workgroup.Workgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.exception.DataSetException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;

@Component
@Slf4j
public class AstronautsSampleDataSetFactory extends AbstractDataSetFactory {

    public static final String ID = "sample-data-astronauts";

    public static final WorkgroupId WORKGROUP_ID_ASTRONAUTS = WorkgroupId.of("astronauts");
    public static final PersonId MEMBER_ID_ALEX = PersonId.of("alex");
    public static final PersonId MEMBER_ID_BEVERLY = PersonId.of("beverly");
    public static final PersonId MEMBER_ID_CHARLOTTE = PersonId.of("charlotte");
    public static final LocationId LOCATION_ID_BLACK = LocationId.of("astronauts-workplace");
    public static final DeviceId DEVICE_ID_BLACK_DISPLAY_BEAMER = DeviceId.of("black-display-beamer");
    public static final DeviceId DEVICE_ID_BLACK_WEB_BROWSER = DeviceId.of("black-web-browser");
    public static final MeetingId MEETING_ID_MARS = MeetingId.of("mars", LOCATION_ID_BLACK);
    public static final String DELEGATE_ID_BLACK = "smart-lab-black-delegate-microservice";

    private final IAssistanceInfo agendaShowingInfo;
    private final IAssistanceInfo locationUnlockingInfo;
    private final IAssistanceInfo devicePreparationInfo;
    private final IProjectBaseInfoFactory githubProjectBaseInfoFactory;

    public AstronautsSampleDataSetFactory(
            IAssistanceInfo agendaShowingInfo,
            IAssistanceInfo locationUnlockingInfo,
            IAssistanceInfo devicePreparationInfo,
            IResolver<String, IProjectBaseInfoFactory> projectBaseInfoFactoryResolver) {
        super(ID);
        this.agendaShowingInfo = agendaShowingInfo;
        this.locationUnlockingInfo = locationUnlockingInfo;
        this.devicePreparationInfo = devicePreparationInfo;
        this.githubProjectBaseInfoFactory = projectBaseInfoFactoryResolver.resolve(GithubServiceConnector.SERVICE_ID);
    }

    @Override
    public Set<IWorkgroup> createWorkgroupSet() throws DataSetException {
        Set<IWorkgroup> workgroups = new HashSet<>();
        Set<PersonId> astronautsMembers = new HashSet<>();
        astronautsMembers.add(MEMBER_ID_ALEX);
        astronautsMembers.add(MEMBER_ID_BEVERLY);
        astronautsMembers.add(MEMBER_ID_CHARLOTTE);
        workgroups.add(Workgroup.of(
                WORKGROUP_ID_ASTRONAUTS,
                "Astronauts",
                astronautsMembers,
                this.githubProjectBaseInfoFactory.newInstance(ImmutableMap
                        .<String, String>builder()
                        .put(GithubProjectBaseInfo.PROJECT_BASE_PROPERTY_KEY_USER, "Kroetz")
                        .put(GithubProjectBaseInfo.PROJECT_BASE_PROPERTY_KEY_REPOSITORY, "astronautsRepo")
                        .build())));
        return workgroups;
    }

    @Override
    public Set<IPerson> createWorkgroupMemberSet() throws DataSetException {
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
    public Set<IMeeting> createMeetingSet() throws DataSetException {
        Set<IMeeting> meetings = new HashSet<>();
        List<IAgendaItem> astronautsMeetingAgenda = new ArrayList<>();
        astronautsMeetingAgenda.add(AgendaItem.of("Calculate journey duration"));
        astronautsMeetingAgenda.add(AgendaItem.of("Discuss who may press the launch button of the rocket"));
        astronautsMeetingAgenda.add(AgendaItem.of("Complain that this is all rocket science"));
        Set<IAssistanceConfiguration> configs = new HashSet<>();
        configs.add(this.agendaShowingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(AgendaShowingInfo.Configuration.CONFIG_PROPERTY_KEY_WEB_BROWSER_ID, DEVICE_ID_BLACK_WEB_BROWSER.getIdValue())
                .put(AgendaShowingInfo.Configuration.CONFIG_PROPERTY_KEY_DISPLAY_ID, DEVICE_ID_BLACK_DISPLAY_BEAMER.getIdValue())
                .build()));
        configs.add(this.locationUnlockingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .build()));
        configs.add(this.devicePreparationInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(DevicePreparationInfo.Configuration.CONFIG_PROPERTY_KEY_DEVICE_ID, DEVICE_ID_BLACK_DISPLAY_BEAMER.getIdValue())
                .build()));
        meetings.add(Meeting.of(
                MEETING_ID_MARS,
                "Meeting about travelling to Mars",
                WORKGROUP_ID_ASTRONAUTS,
                astronautsMeetingAgenda,
                configs,
                timeBase.plusSeconds(0),
                timeBase.plusSeconds(300)));
        return meetings;
    }

    @Override
    public Set<ILocation> createLocationSet() throws DataSetException {
        Set<ILocation> locations = new HashSet<>();
        Set<DeviceId> blackLocationDevices = new HashSet<>();
        blackLocationDevices.add(DEVICE_ID_BLACK_DISPLAY_BEAMER);
        locations.add(Location.of(
                LOCATION_ID_BLACK,
                "Astronauts workplace",
                blackLocationDevices));
        return locations;
    }

    @Override
    public Set<IDevice> createDeviceSet() throws DataSetException {
        Set<IDevice> devices = new HashSet<>();
        devices.add(Device.of(
                DEVICE_ID_BLACK_DISPLAY_BEAMER,
                DummyBeamerAdapter.DEVICE_TYPE,
                format("Beamer at location \"%s\"", LOCATION_ID_BLACK),
                DELEGATE_ID_BLACK));
        return devices;
    }
}
