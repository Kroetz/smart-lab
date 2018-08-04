package de.qaware.smartlabdataset.factory;

import com.google.common.collect.ImmutableMap;
import de.qaware.smartlabaction.action.actor.display.DummyDisplayAdapter;
import de.qaware.smartlabaction.action.actor.microphone.DummyMicrophoneAdapter;
import de.qaware.smartlabaction.action.actor.projectbase.info.github.GithubProjectBaseInfo;
import de.qaware.smartlabassistance.assistance.info.agendashowing.AgendaShowingInfo;
import de.qaware.smartlabassistance.assistance.info.devicepreparation.DevicePreparationInfo;
import de.qaware.smartlabassistance.assistance.info.generic.IAssistanceInfo;
import de.qaware.smartlabassistance.assistance.info.minutetaking.MinuteTakingInfo;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.device.Device;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
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
import de.qaware.smartlabcore.miscellaneous.Language;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;

@Component
public class FireFightersSampleDataSetFactory extends AbstractDataSetFactory {

    public static final String ID = "sample-data-fire-fighters";

    public static final WorkgroupId WORKGROUP_ID_FIRE_FIGHTERS = WorkgroupId.of("fire-fighters");
    public static final PersonId MEMBER_ID_ANTHONY = PersonId.of("fire-fighter-anthony");
    public static final PersonId MEMBER_ID_BRUCE = PersonId.of("fire-fighter-bruce");
    public static final PersonId MEMBER_ID_CARLOS = PersonId.of("fire-fighter-carlos");
    public static final LocationId LOCATION_ID_RED = LocationId.of("fire-fighters-workplace");
    public static final DeviceId DEVICE_ID_RED_DISPLAY_BIG = DeviceId.of("red-display-big");
    public static final DeviceId DEVICE_ID_RED_MICROPHONE = DeviceId.of("red-microphone");
    public static final DeviceId DEVICE_ID_RED_WEB_BROWSER = DeviceId.of("red-web-browser");
    public static final MeetingId MEETING_ID_TRUCK = MeetingId.of("truck", LOCATION_ID_RED);
    public static final String DELEGATE_ID_RED = "smart-lab-red-delegate-microservice";

    private final IAssistanceInfo minuteTakingInfo;
    private final IAssistanceInfo agendaShowingInfo;
    private final IAssistanceInfo locationUnlockingInfo;
    private final IAssistanceInfo devicePreparationInfo;
    private final GithubProjectBaseInfo.Factory githubProjectBaseInfoFactory;

    public FireFightersSampleDataSetFactory(
            IAssistanceInfo minuteTakingInfo,
            IAssistanceInfo agendaShowingInfo,
            IAssistanceInfo locationUnlockingInfo,
            IAssistanceInfo devicePreparationInfo,
            GithubProjectBaseInfo.Factory githubProjectBaseInfoFactory) {
        super(ID);
        this.minuteTakingInfo = minuteTakingInfo;
        this.agendaShowingInfo = agendaShowingInfo;
        this.locationUnlockingInfo = locationUnlockingInfo;
        this.devicePreparationInfo = devicePreparationInfo;
        this.githubProjectBaseInfoFactory = githubProjectBaseInfoFactory;
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
                this.githubProjectBaseInfoFactory.newInstance(ImmutableMap
                        .<String, String>builder()
                        .put(GithubProjectBaseInfo.PROJECT_BASE_PROPERTY_KEY_USER, "Kroetz")
                        .put(GithubProjectBaseInfo.PROJECT_BASE_PROPERTY_KEY_REPOSITORY, "fireFightersRepo")
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
    public Set<IMeeting> createMeetingSet() throws DataSetException {
        Set<IMeeting> meetings = new HashSet<>();
        List<IAgendaItem> fireFightersMeetingAgenda = new ArrayList<>();
        fireFightersMeetingAgenda.add(AgendaItem.of("Show how bad the old truck is"));
        fireFightersMeetingAgenda.add(AgendaItem.of("Show how great the new truck is"));
        fireFightersMeetingAgenda.add(AgendaItem.of("Discuss how to pay for the new truck"));
        Set<IAssistanceConfiguration> configs = new HashSet<>();
        configs.add(this.minuteTakingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(MinuteTakingInfo.Configuration.CONFIG_PROPERTY_KEY_SPOKEN_LANGUAGE, Language.EN_US.toString())
                .put(MinuteTakingInfo.Configuration.CONFIG_PROPERTY_KEY_UPLOAD_DIR, "/sampleDataMinutes")
                .put(MinuteTakingInfo.Configuration.CONFIG_PROPERTY_KEY_MICROPHONE_ID, DEVICE_ID_RED_MICROPHONE.getIdValue())
                .build()));
        configs.add(this.agendaShowingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(AgendaShowingInfo.Configuration.CONFIG_PROPERTY_KEY_WEB_BROWSER_ID, DEVICE_ID_RED_WEB_BROWSER.getIdValue())
                .put(AgendaShowingInfo.Configuration.CONFIG_PROPERTY_KEY_DISPLAY_ID, DEVICE_ID_RED_DISPLAY_BIG.getIdValue())
                .build()));
        configs.add(this.locationUnlockingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .build()));
        configs.add(this.devicePreparationInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(DevicePreparationInfo.Configuration.CONFIG_PROPERTY_KEY_DEVICE_ID, DEVICE_ID_RED_DISPLAY_BIG.getIdValue())
                .build()));
        meetings.add(Meeting.of(
                MEETING_ID_TRUCK,
                "Meeting about the new fire truck \"Fire Exterminator 3000\"",
                WORKGROUP_ID_FIRE_FIGHTERS,
                fireFightersMeetingAgenda,
                configs,
                timeBase.plusSeconds(120),
                timeBase.plusSeconds(420)));
        return meetings;
    }

    @Override
    public Set<ILocation> createLocationSet() throws DataSetException {
        Set<ILocation> locations = new HashSet<>();
        Set<DeviceId> redLocationDevices = new HashSet<>();
        redLocationDevices.add(DEVICE_ID_RED_MICROPHONE);
        locations.add(Location.of(
                LOCATION_ID_RED,
                "Fire fighters workplace",
                redLocationDevices));
        return locations;
    }

    @Override
    public Set<IDevice> createDeviceSet() throws DataSetException {
        Set<IDevice> devices = new HashSet<>();
        devices.add(Device.of(
                DEVICE_ID_RED_DISPLAY_BIG,
                DummyDisplayAdapter.DEVICE_TYPE,
                format("Big display at location \"%s\"", LOCATION_ID_RED),
                DELEGATE_ID_RED));
        devices.add(Device.of(
                DEVICE_ID_RED_MICROPHONE,
                DummyMicrophoneAdapter.DEVICE_TYPE,
                format("Microphone at location \"%s\"", LOCATION_ID_RED),
                DELEGATE_ID_RED));
        return devices;
    }
}
