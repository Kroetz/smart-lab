package de.qaware.smartlabsampledata.factory;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.Coordinates;
import de.qaware.smartlabaction.action.actor.github.GithubKnowledgeBaseInfo;
import de.qaware.smartlabassistance.assistance.info.agendashowing.AgendaShowingInfo;
import de.qaware.smartlabassistance.assistance.info.devicepreparation.DevicePreparationInfo;
import de.qaware.smartlabassistance.assistance.info.generic.IAssistanceInfo;
import de.qaware.smartlabassistance.assistance.info.websitedisplaying.WebsiteDisplayingInfo;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabaction.action.actor.display.DummyDisplayAdapter;
import de.qaware.smartlabcore.data.device.entity.Device;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.meeting.*;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.Person;
import de.qaware.smartlabcore.data.person.PersonId;
import de.qaware.smartlabcore.data.location.ILocation;
import de.qaware.smartlabcore.data.location.Location;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.workgroup.Workgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;

@Component
public class ForestRangersDataFactory extends AbstractSampleDataFactory {

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

    public ForestRangersDataFactory(
            IAssistanceInfo websiteDisplayingInfo,
            IAssistanceInfo agendaShowingInfo,
            IAssistanceInfo locationUnlockingInfo,
            IAssistanceInfo devicePreparationInfo) {
        super();
        this.websiteDisplayingInfo = websiteDisplayingInfo;
        this.agendaShowingInfo = agendaShowingInfo;
        this.locationUnlockingInfo = locationUnlockingInfo;
        this.devicePreparationInfo = devicePreparationInfo;
    }

    @Override
    public Set<IWorkgroup> createWorkgroupSet() {
        Set<IWorkgroup> workgroups = new HashSet<>();
        Set<PersonId> forestRangerMembers = new HashSet<>();
        forestRangerMembers.add(MEMBER_ID_ANNA);
        forestRangerMembers.add(MEMBER_ID_BARRY);
        forestRangerMembers.add(MEMBER_ID_CAROLINE);
        workgroups.add(Workgroup.builder()
                .id(WORKGROUP_ID_FOREST_RANGERS)
                .name("Forest Rangers")
                .memberIds(forestRangerMembers)
                .knowledgeBaseInfo(GithubKnowledgeBaseInfo.builder().repository(new Coordinates.Simple(
                        "Kroetz",
                        "forestRangersRepo")).build())
                .build());
        return workgroups;
    }

    @Override
    public Set<IPerson> createWorkgroupMemberSet() {
        Set<IPerson> workgroupMembers = new HashSet<>();
        workgroupMembers.add(Person.builder()
                .id(MEMBER_ID_ANNA)
                .name("Forest Ranger Anna")
                .email("anna@forest-rangers.com")
                .build());
        workgroupMembers.add(Person.builder()
                .id(MEMBER_ID_BARRY)
                .name("Forest Ranger Barry")
                .email("barry@forest-rangers.com")
                .build());
        workgroupMembers.add(Person.builder()
                .id(MEMBER_ID_CAROLINE)
                .name("Forest Ranger Caroline")
                .email("caroline@forest-rangers.com")
                .build());
        return workgroupMembers;
    }

    @Override
    public Set<IMeeting> createMeetingSet() {
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
        meetings.add(Meeting.builder()
                .id(MEETING_ID_BARK_BEETLE)
                .title("Meeting about the danger of the bark beetle")
                .workgroupId(WORKGROUP_ID_FOREST_RANGERS)
                .agenda(forestRangersMeetingAgenda)
                .assistanceConfigurations(configs)
                .start(timeBase.plusSeconds(60))
                .end(timeBase.plusSeconds(360)).build());
        return meetings;
    }

    @Override
    public Set<ILocation> createLocationSet() {
        Set<ILocation> locations = new HashSet<>();
        Set<DeviceId> greenLocationDevices = new HashSet<>();
        greenLocationDevices.add(DEVICE_ID_GREEN_DISPLAY_BIG);
        greenLocationDevices.add(DEVICE_ID_GREEN_DISPLAY_SMALL);
        locations.add(Location.builder()
                .id(LOCATION_ID_GREEN)
                .name("Forest rangers workplace")
                .deviceIds(greenLocationDevices)
                .build());
        return locations;
    }

    @Override
    public Set<IDevice> createDeviceSet() {
        Set<IDevice> devices = new HashSet<>();
        devices.add(Device.builder()
                .id(DEVICE_ID_GREEN_DISPLAY_BIG)
                .type(DummyDisplayAdapter.DEVICE_TYPE)
                .name(format("Big main display at location \"%s\"", LOCATION_ID_GREEN))
                .responsibleDelegate(DELEGATE_ID_GREEN)
                .build());
        devices.add(Device.builder()
                .id(DEVICE_ID_GREEN_DISPLAY_SMALL)
                .type(DummyDisplayAdapter.DEVICE_TYPE)
                .name(format("Small display next to the door at location \"%s\"", LOCATION_ID_GREEN))
                .responsibleDelegate(DELEGATE_ID_GREEN)
                .build());
        devices.add(Device.builder()
                .id(DEVICE_ID_GREEN_WEB_BROWSER)
                .type("chrome")
                .name(format("Web browser at location \"%s\"", LOCATION_ID_GREEN))
                .responsibleDelegate(DELEGATE_ID_GREEN)
                .build());
        return devices;
    }
}
