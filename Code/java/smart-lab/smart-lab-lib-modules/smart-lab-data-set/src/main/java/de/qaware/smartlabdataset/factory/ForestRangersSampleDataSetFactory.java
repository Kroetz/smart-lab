package de.qaware.smartlabdataset.factory;

import com.google.common.collect.ImmutableMap;
import de.qaware.smartlab.actuator.adapter.adapters.display.DummyDisplayAdapter;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.info.generic.IProjectBaseInfoFactory;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.info.github.GithubProjectBaseInfo;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.service.github.GithubServiceConnector;
import de.qaware.smartlab.assistance.assistances.info.agendashowing.AgendaShowingInfo;
import de.qaware.smartlab.assistance.assistances.info.devicepreparation.DevicePreparationInfo;
import de.qaware.smartlab.assistance.assistances.info.generic.IAssistanceInfo;
import de.qaware.smartlab.assistance.assistances.info.websitedisplaying.WebsiteDisplayingInfo;
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
public class ForestRangersSampleDataSetFactory extends AbstractDataSetFactory {

    public static final String ID = "sample-data-forest-rangers";

    public static final WorkgroupId WORKGROUP_ID_FOREST_RANGERS = WorkgroupId.of("forest-rangers");
    public static final PersonId MEMBER_ID_ANNA = PersonId.of("forest-ranger-anna");
    public static final PersonId MEMBER_ID_BARRY = PersonId.of("forest-ranger-barry");
    public static final PersonId MEMBER_ID_CAROLINE = PersonId.of("forest-ranger-caroline");
    public static final LocationId LOCATION_ID_GREEN = LocationId.of("forest-rangers-workplace");
    public static final DeviceId DEVICE_ID_GREEN_DISPLAY_BIG = DeviceId.of("green-display-big");
    public static final DeviceId DEVICE_ID_GREEN_DISPLAY_SMALL = DeviceId.of("green-display-small");
    public static final DeviceId DEVICE_ID_GREEN_WEB_BROWSER = DeviceId.of("green-web-browser");
    public static final MeetingId MEETING_ID_BARK_BEETLE = MeetingId.of("bark-beetle", LOCATION_ID_GREEN);
    public static final String DELEGATE_ID_GREEN = "smart-lab-green-delegate-microservice";

    private final IAssistanceInfo websiteDisplayingInfo;
    private final IAssistanceInfo agendaShowingInfo;
    private final IAssistanceInfo locationUnlockingInfo;
    private final IAssistanceInfo devicePreparationInfo;
    private final IProjectBaseInfoFactory githubProjectBaseInfoFactory;

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
        this.githubProjectBaseInfoFactory = projectBaseInfoFactoryResolver.resolve(GithubServiceConnector.SERVICE_ID);
    }

    @Override
    public Set<IWorkgroup> createWorkgroupSet() throws DataSetException {
        Set<IWorkgroup> workgroups = new HashSet<>();
        Set<PersonId> forestRangerMembers = new HashSet<>();
        forestRangerMembers.add(MEMBER_ID_ANNA);
        forestRangerMembers.add(MEMBER_ID_BARRY);
        forestRangerMembers.add(MEMBER_ID_CAROLINE);
        workgroups.add(Workgroup.of(
                WORKGROUP_ID_FOREST_RANGERS,
                "Forest Rangers",
                forestRangerMembers,
                this.githubProjectBaseInfoFactory.newInstance(ImmutableMap
                        .<String, String>builder()
                        .put(GithubProjectBaseInfo.PROJECT_BASE_PROPERTY_KEY_USER, "Kroetz")
                        .put(GithubProjectBaseInfo.PROJECT_BASE_PROPERTY_KEY_REPOSITORY, "forestRangersRepo")
                        .build())));
        return workgroups;
    }

    @Override
    public Set<IPerson> createWorkgroupMemberSet() throws DataSetException {
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
    public Set<IMeeting> createMeetingSet() throws DataSetException {
        Set<IMeeting> meetings = new HashSet<>();
        List<IAgendaItem> forestRangersMeetingAgenda = new ArrayList<>();
        forestRangersMeetingAgenda.add(AgendaItem.of("Show potential damage"));
        forestRangersMeetingAgenda.add(AgendaItem.of("Show increase in population"));
        forestRangersMeetingAgenda.add(AgendaItem.of("Laugh together"));
        Set<IAssistanceConfiguration> configs = new HashSet<>();
        configs.add(this.websiteDisplayingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(WebsiteDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_URL, "https://en.wikipedia.org/wiki/Bark_beetle")
                .put(WebsiteDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_WEB_BROWSER_ID, DEVICE_ID_GREEN_WEB_BROWSER.getIdValue())
                .put(WebsiteDisplayingInfo.Configuration.CONFIG_PROPERTY_KEY_DISPLAY_ID, DEVICE_ID_GREEN_DISPLAY_BIG.getIdValue())
                .build()));
        configs.add(this.agendaShowingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(AgendaShowingInfo.Configuration.CONFIG_PROPERTY_KEY_WEB_BROWSER_ID, DEVICE_ID_GREEN_WEB_BROWSER.getIdValue())
                .put(AgendaShowingInfo.Configuration.CONFIG_PROPERTY_KEY_DISPLAY_ID, DEVICE_ID_GREEN_DISPLAY_SMALL.getIdValue())
                .build()));
        configs.add(this.locationUnlockingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .build()));
        configs.add(this.devicePreparationInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(DevicePreparationInfo.Configuration.CONFIG_PROPERTY_KEY_DEVICE_ID, DEVICE_ID_GREEN_DISPLAY_BIG.getIdValue())
                .build()));
        configs.add(this.devicePreparationInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(DevicePreparationInfo.Configuration.CONFIG_PROPERTY_KEY_DEVICE_ID, DEVICE_ID_GREEN_DISPLAY_SMALL.getIdValue())
                .build()));
        meetings.add(Meeting.of(
                MEETING_ID_BARK_BEETLE,
                "Meeting about the danger of the bark beetle",
                WORKGROUP_ID_FOREST_RANGERS,
                forestRangersMeetingAgenda,
                configs,
                timeBase.plusSeconds(60),
                timeBase.plusSeconds(360)));
        return meetings;
    }

    @Override
    public Set<ILocation> createLocationSet() throws DataSetException {
        Set<ILocation> locations = new HashSet<>();
        Set<DeviceId> greenLocationDevices = new HashSet<>();
        greenLocationDevices.add(DEVICE_ID_GREEN_DISPLAY_BIG);
        greenLocationDevices.add(DEVICE_ID_GREEN_DISPLAY_SMALL);
        locations.add(Location.of(
                LOCATION_ID_GREEN,
                "Forest rangers workplace",
                greenLocationDevices));
        return locations;
    }

    @Override
    public Set<IDevice> createDeviceSet() throws DataSetException {
        Set<IDevice> devices = new HashSet<>();
        devices.add(Device.of(
                DEVICE_ID_GREEN_DISPLAY_BIG,
                DummyDisplayAdapter.DEVICE_TYPE,
                format("Big main display at location \"%s\"", LOCATION_ID_GREEN),
                DELEGATE_ID_GREEN));
        devices.add(Device.of(
                DEVICE_ID_GREEN_DISPLAY_SMALL,
                DummyDisplayAdapter.DEVICE_TYPE,
                format("Small display next to the door at location \"%s\"", LOCATION_ID_GREEN),
                DELEGATE_ID_GREEN));
        devices.add(Device.of(
                DEVICE_ID_GREEN_WEB_BROWSER,
                "chrome",
                format("Web browser at location \"%s\"", LOCATION_ID_GREEN),
                DELEGATE_ID_GREEN));
        return devices;
    }
}
