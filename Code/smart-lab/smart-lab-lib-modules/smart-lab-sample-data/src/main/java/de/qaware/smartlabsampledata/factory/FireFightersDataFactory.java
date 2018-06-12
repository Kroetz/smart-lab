package de.qaware.smartlabsampledata.factory;

import com.jcabi.github.Coordinates;
import de.qaware.smartlabaction.action.external.github.GithubKnowledgeBaseInfo;
import de.qaware.smartlabassistance.assistance.info.MinuteTakingInfo;
import de.qaware.smartlabassistance.assistance.info.RoomUnlockingInfo;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.device.entity.Device;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.device.microphone.DummyMicrophone;
import de.qaware.smartlabcore.data.meeting.AgendaItem;
import de.qaware.smartlabcore.data.meeting.IAgendaItem;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.Meeting;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.Person;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.room.Room;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.workgroup.Workgroup;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FireFightersDataFactory extends AbstractSampleDataFactory {

    public static final String WORKGROUP_ID_FIRE_FIGHTERS = "fire-fighters";
    public static final String MEMBER_ID_ANTHONY = "fire-fighter-anthony";
    public static final String MEMBER_ID_BRUCE = "fire-fighter-bruce";
    public static final String MEMBER_ID_CARLOS = "fire-fighter-carlos";
    public static final String MEETING_ID_TRUCK = "truck";
    public static final String ROOM_ID_RED = "red";
    public static final String DEVICE_ID_RED_MICROPHONE = "red-microphone";
    public static final String DELEGATE_ID_RED = "red-delegate";

    public FireFightersDataFactory() {
        super();
    }

    @Override
    public List<IWorkgroup> createWorkgroupList() {
        List<IWorkgroup> workgroups = new ArrayList<>();
        Set<String> fireFighterMembers = new HashSet<>();
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
    public List<IPerson> createWorkgroupMemberList() {
        List<IPerson> workgroupMembers = new ArrayList<>();
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
    public List<IMeeting> createMeetingList() {
        List<IMeeting> meetings = new ArrayList<>();
        List<IAgendaItem> fireFightersMeetingAgenda = new ArrayList<>();
        fireFightersMeetingAgenda.add(AgendaItem.builder().text("Show how bad the old truck is").build());
        fireFightersMeetingAgenda.add(AgendaItem.builder().text("Show how great the new truck is").build());
        fireFightersMeetingAgenda.add(AgendaItem.builder().text("Discuss how to pay for the new truck").build());
        Set<String> fireFightersMeetingAssistances = new HashSet<>();
        fireFightersMeetingAssistances.add(MinuteTakingInfo.ASSISTANCE_ID);
        fireFightersMeetingAssistances.add(RoomUnlockingInfo.ASSISTANCE_ID);
        Map<String, IAssistanceConfiguration> fireFightersMeetingAssistanceConfigurations = new HashMap<>();
        fireFightersMeetingAssistanceConfigurations.put(MinuteTakingInfo.ASSISTANCE_ID, new MinuteTakingInfo.Configuration(DEVICE_ID_RED_MICROPHONE));
        fireFightersMeetingAssistanceConfigurations.put(RoomUnlockingInfo.ASSISTANCE_ID, new RoomUnlockingInfo.Configuration("dummy ID"));
        meetings.add(Meeting.builder()
                .id(MEETING_ID_TRUCK)
                .title("Meeting about the new fire truck \"Fire Exterminator 3000\"")
                .workgroupId(WORKGROUP_ID_FIRE_FIGHTERS)
                .roomId(ROOM_ID_RED)
                .agenda(fireFightersMeetingAgenda)
                .assistanceIds(fireFightersMeetingAssistances)
                .assistanceConfigurationsById(fireFightersMeetingAssistanceConfigurations)
                .start(timeBase.plusSeconds(240))
                .end(timeBase.plusSeconds(540)).build());
        return meetings;
    }

    @Override
    public List<IRoom> createRoomList() {
        List<IRoom> rooms = new ArrayList<>();
        List<String> redRoomDevices = new ArrayList<>();
        redRoomDevices.add(DEVICE_ID_RED_MICROPHONE);
        rooms.add(Room.builder()
                .id(ROOM_ID_RED)
                .name("Room Red")
                .deviceIds(redRoomDevices)
                .build());
        return rooms;
    }

    @Override
    public List<IDevice> createDeviceList() {
        List<IDevice> devices = new ArrayList<>();
        devices.add(Device.builder()
                .id(DEVICE_ID_RED_MICROPHONE)
                .type(DummyMicrophone.DEVICE_TYPE)
                .name("Microphone in Room Red")
                .responsibleDelegate(DELEGATE_ID_RED)
                .build());
        return devices;
    }
}
