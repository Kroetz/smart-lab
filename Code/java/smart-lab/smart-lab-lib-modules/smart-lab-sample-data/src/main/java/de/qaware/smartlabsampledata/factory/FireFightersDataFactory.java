package de.qaware.smartlabsampledata.factory;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.Coordinates;
import de.qaware.smartlabaction.action.actor.github.GithubKnowledgeBaseInfo;
import de.qaware.smartlabassistance.assistance.info.agendashowing.AgendaShowingInfo;
import de.qaware.smartlabassistance.assistance.info.devicepreparation.DevicePreparationInfo;
import de.qaware.smartlabassistance.assistance.info.generic.IAssistanceInfo;
import de.qaware.smartlabassistance.assistance.info.minutetaking.MinuteTakingInfo;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabaction.action.actor.display.DummyDisplayAdapter;
import de.qaware.smartlabcore.data.device.entity.Device;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabaction.action.actor.microphone.DummyMicrophoneAdapter;
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
import de.qaware.smartlabcore.miscellaneous.Language;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class FireFightersDataFactory extends AbstractSampleDataFactory {

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

    public FireFightersDataFactory(
            IAssistanceInfo minuteTakingInfo,
            IAssistanceInfo agendaShowingInfo,
            IAssistanceInfo locationUnlockingInfo,
            IAssistanceInfo devicePreparationInfo) {
        super();
        this.minuteTakingInfo = minuteTakingInfo;
        this.agendaShowingInfo = agendaShowingInfo;
        this.locationUnlockingInfo = locationUnlockingInfo;
        this.devicePreparationInfo = devicePreparationInfo;
    }

    @Override
    public Set<IWorkgroup> createWorkgroupSet() {
        Set<IWorkgroup> workgroups = new HashSet<>();
        Set<PersonId> fireFighterMembers = new HashSet<>();
        fireFighterMembers.add(MEMBER_ID_ANTHONY);
        fireFighterMembers.add(MEMBER_ID_BRUCE);
        fireFighterMembers.add(MEMBER_ID_CARLOS);
        workgroups.add(Workgroup.builder()
                .id(WORKGROUP_ID_FIRE_FIGHTERS)
                .name("Fire Fighters")
                .memberIds(fireFighterMembers)
                .knowledgeBaseInfo(GithubKnowledgeBaseInfo.builder().repository(new Coordinates.Simple(
                        "Kroetz",
                        "fireFightersRepo")).build())
                .build());
        return workgroups;
    }

    @Override
    public Set<IPerson> createWorkgroupMemberSet() {
        Set<IPerson> workgroupMembers = new HashSet<>();
        workgroupMembers.add(Person.builder()
                .id(MEMBER_ID_ANTHONY)
                .name("Fire Fighter Anthony")
                .email("anthony@fire-fighters.com")
                .build());
        workgroupMembers.add(Person.builder()
                .id(MEMBER_ID_BRUCE)
                .name("Fire Fighter Bruce")
                .email("bruce@fire-fighters.com")
                .build());
        workgroupMembers.add(Person.builder()
                .id(MEMBER_ID_CARLOS)
                .name("Fire Fighter Carlos")
                .email("carlos@fire-fighters.com")
                .build());
        return workgroupMembers;
    }

    @Override
    public Set<IMeeting> createMeetingSet() {
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
        meetings.add(Meeting.builder()
                .id(MEETING_ID_TRUCK)
                .title("Meeting about the new fire truck \"Fire Exterminator 3000\"")
                .workgroupId(WORKGROUP_ID_FIRE_FIGHTERS)
                .agenda(fireFightersMeetingAgenda)
                .assistanceConfigurations(configs)
                .start(timeBase.plusSeconds(120))
                .end(timeBase.plusSeconds(420)).build());
        return meetings;
    }

    @Override
    public Set<ILocation> createLocationSet() {
        Set<ILocation> locations = new HashSet<>();
        Set<DeviceId> redLocationDevices = new HashSet<>();
        redLocationDevices.add(DEVICE_ID_RED_MICROPHONE);
        locations.add(Location.builder()
                .id(LOCATION_ID_RED)
                .name("Fire fighters workplace")
                .deviceIds(redLocationDevices)
                .build());
        return locations;
    }

    @Override
    public Set<IDevice> createDeviceSet() {
        Set<IDevice> devices = new HashSet<>();
        devices.add(Device.builder()
                .id(DEVICE_ID_RED_DISPLAY_BIG)
                .type(DummyDisplayAdapter.DEVICE_TYPE)
                .name(String.format("Big display at location \"%s\"", LOCATION_ID_RED))
                .responsibleDelegate(DELEGATE_ID_RED)
                .build());
        devices.add(Device.builder()
                .id(DEVICE_ID_RED_MICROPHONE)
                .type(DummyMicrophoneAdapter.DEVICE_TYPE)
                .name(String.format("Microphone at location \"%s\"", LOCATION_ID_RED))
                .responsibleDelegate(DELEGATE_ID_RED)
                .build());
        return devices;
    }
}
