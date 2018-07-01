package de.qaware.smartlabsampledata.factory;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.Coordinates;
import de.qaware.smartlabaction.action.executable.dataupload.github.GithubKnowledgeBaseInfo;
import de.qaware.smartlabassistance.assistance.info.MinuteTakingInfo;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.assistance.IAssistanceInfo;
import de.qaware.smartlabcore.data.device.entity.Device;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.device.microphone.DummyMicrophone;
import de.qaware.smartlabcore.data.meeting.*;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.Person;
import de.qaware.smartlabcore.data.person.PersonId;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.room.Room;
import de.qaware.smartlabcore.data.room.RoomId;
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
    public static final RoomId ROOM_ID_RED = RoomId.of("red");
    public static final DeviceId DEVICE_ID_RED_MICROPHONE = DeviceId.of("red-microphone");
    public static final MeetingId MEETING_ID_TRUCK = MeetingId.of("truck", ROOM_ID_RED);
    public static final String DELEGATE_ID_RED = "red-delegate";

    private final IAssistanceInfo minuteTakingInfo;
    private final IAssistanceInfo roomUnlockingInfo;

    public FireFightersDataFactory(
            IAssistanceInfo minuteTakingInfo,
            IAssistanceInfo roomUnlockingInfo) {
        super();
        this.minuteTakingInfo = minuteTakingInfo;
        this.roomUnlockingInfo = roomUnlockingInfo;
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
        fireFightersMeetingAgenda.add(AgendaItem.builder().content("Show how bad the old truck is").build());
        fireFightersMeetingAgenda.add(AgendaItem.builder().content("Show how great the new truck is").build());
        fireFightersMeetingAgenda.add(AgendaItem.builder().content("Discuss how to pay for the new truck").build());
        Set<IAssistanceConfiguration> configs = new HashSet<>();
        configs.add(this.minuteTakingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(MinuteTakingInfo.Configuration.CONFIG_PROPERTY_KEY_SPOKEN_LANGUAGE, Language.EN_US.toString())
                .put(MinuteTakingInfo.Configuration.CONFIG_PROPERTY_KEY_UPLOAD_DIR, "/sampleDataMinutes")
                .put(MinuteTakingInfo.Configuration.CONFIG_PROPERTY_KEY_MICROPHONE_ID, DEVICE_ID_RED_MICROPHONE.getIdValue())
                .build()));
        configs.add(this.roomUnlockingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
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
    public Set<IRoom> createRoomSet() {
        Set<IRoom> rooms = new HashSet<>();
        Set<DeviceId> redRoomDevices = new HashSet<>();
        redRoomDevices.add(DEVICE_ID_RED_MICROPHONE);
        rooms.add(Room.builder()
                .id(ROOM_ID_RED)
                .name("Room Red")
                .deviceIds(redRoomDevices)
                .build());
        return rooms;
    }

    @Override
    public Set<IDevice> createDeviceSet() {
        Set<IDevice> devices = new HashSet<>();
        devices.add(Device.builder()
                .id(DEVICE_ID_RED_MICROPHONE)
                .type(DummyMicrophone.DEVICE_TYPE)
                .name("Microphone in Room Red")
                .responsibleDelegate(DELEGATE_ID_RED)
                .build());
        return devices;
    }
}
