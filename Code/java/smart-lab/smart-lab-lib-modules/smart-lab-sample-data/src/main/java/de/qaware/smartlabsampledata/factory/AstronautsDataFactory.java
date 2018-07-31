package de.qaware.smartlabsampledata.factory;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.Coordinates;
import de.qaware.smartlabaction.action.actor.beamer.DummyBeamerAdapter;
import de.qaware.smartlabaction.action.actor.github.GithubKnowledgeBaseInfo;
import de.qaware.smartlabassistance.assistance.info.agendashowing.AgendaShowingInfo;
import de.qaware.smartlabassistance.assistance.info.devicepreparation.DevicePreparationInfo;
import de.qaware.smartlabassistance.assistance.info.generic.IAssistanceInfo;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.device.entity.Device;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.meeting.*;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.Person;
import de.qaware.smartlabcore.data.person.PersonId;
import de.qaware.smartlabcore.data.location.ILocation;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.data.location.Location;
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
public class AstronautsDataFactory extends AbstractSampleDataFactory {

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

    public AstronautsDataFactory(
            IAssistanceInfo agendaShowingInfo,
            IAssistanceInfo locationUnlockingInfo,
            IAssistanceInfo devicePreparationInfo) {
        super();
        this.agendaShowingInfo = agendaShowingInfo;
        this.locationUnlockingInfo = locationUnlockingInfo;
        this.devicePreparationInfo = devicePreparationInfo;
    }

    @Override
    public Set<IWorkgroup> createWorkgroupSet() {
        Set<IWorkgroup> workgroups = new HashSet<>();
        Set<PersonId> astronautsMembers = new HashSet<>();
        astronautsMembers.add(MEMBER_ID_ALEX);
        astronautsMembers.add(MEMBER_ID_BEVERLY);
        astronautsMembers.add(MEMBER_ID_CHARLOTTE);
        workgroups.add(Workgroup.builder()
                .id(WORKGROUP_ID_ASTRONAUTS)
                .name("Astronauts")
                .memberIds(astronautsMembers)
                .knowledgeBaseInfo(GithubKnowledgeBaseInfo.builder().repository(new Coordinates.Simple(
                        "Kroetz",
                        "astronautsRepo")).build())
                .build());
        return workgroups;
    }

    @Override
    public Set<IPerson> createWorkgroupMemberSet() {
        Set<IPerson> workgroupMembers = new HashSet<>();
        workgroupMembers.add(Person.builder()
                .id(MEMBER_ID_ALEX)
                .name("Astronaut Alex")
                .email("alex@astronauts.com")
                .build());
        workgroupMembers.add(Person.builder()
                .id(MEMBER_ID_BEVERLY)
                .name("Astronaut Beverly")
                .email("beverly@astronauts.com")
                .build());
        workgroupMembers.add(Person.builder()
                .id(MEMBER_ID_CHARLOTTE)
                .name("Astronaut Charlotte")
                .email("charlotte@astronauts.com")
                .build());
        return workgroupMembers;
    }

    @Override
    public Set<IMeeting> createMeetingSet() {
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
        meetings.add(Meeting.builder()
                .id(MEETING_ID_MARS)
                .title("Meeting about travelling to Mars")
                .workgroupId(WORKGROUP_ID_ASTRONAUTS)
                .agenda(astronautsMeetingAgenda)
                .assistanceConfigurations(configs)
                .start(timeBase.plusSeconds(0))
                .end(timeBase.plusSeconds(300)).build());
        return meetings;
    }

    @Override
    public Set<ILocation> createLocationSet() {
        Set<ILocation> locations = new HashSet<>();
        Set<DeviceId> blackLocationDevices = new HashSet<>();
        blackLocationDevices.add(DEVICE_ID_BLACK_DISPLAY_BEAMER);
        locations.add(Location.builder()
                .id(LOCATION_ID_BLACK)
                .name("Astronauts workplace")
                .deviceIds(blackLocationDevices)
                .build());
        return locations;
    }

    @Override
    public Set<IDevice> createDeviceSet() {
        Set<IDevice> devices = new HashSet<>();
        devices.add(Device.builder()
                .id(DEVICE_ID_BLACK_DISPLAY_BEAMER)
                .type(DummyBeamerAdapter.DEVICE_TYPE)
                .name(format("Beamer at location \"%s\"", LOCATION_ID_BLACK))
                .responsibleDelegate(DELEGATE_ID_BLACK)
                .build());
        return devices;
    }
}
