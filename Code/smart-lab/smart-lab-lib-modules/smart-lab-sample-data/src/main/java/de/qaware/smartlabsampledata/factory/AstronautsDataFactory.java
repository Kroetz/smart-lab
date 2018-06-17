package de.qaware.smartlabsampledata.factory;

import com.jcabi.github.Coordinates;
import de.qaware.smartlabaction.action.executable.external.github.GithubKnowledgeBaseInfo;
import de.qaware.smartlabassistance.assistance.info.RoomUnlockingInfo;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.device.display.DummyDisplay;
import de.qaware.smartlabcore.data.device.entity.Device;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
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
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AstronautsDataFactory extends AbstractSampleDataFactory {

    public static final WorkgroupId WORKGROUP_ID_ASTRONAUTS = WorkgroupId.of("astronauts");
    public static final PersonId MEMBER_ID_ALEX = PersonId.of("alex");
    public static final PersonId MEMBER_ID_BEVERLY = PersonId.of("beverly");
    public static final PersonId MEMBER_ID_CHARLOTTE = PersonId.of("charlotte");
    public static final MeetingId MEETING_ID_MARS = MeetingId.of("mars");
    public static final RoomId ROOM_ID_BLACK = RoomId.of("black");
    public static final DeviceId DEVICE_ID_BLACK_DISPLAY = DeviceId.of("black-display");
    public static final String DELEGATE_ID_BLACK = "black-delegate";

    public AstronautsDataFactory() {
        super();
    }

    @Override
    public List<IWorkgroup> createWorkgroupList() {
        List<IWorkgroup> workgroups = new ArrayList<>();
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
    public List<IPerson> createWorkgroupMemberList() {
        List<IPerson> workgroupMembers = new ArrayList<>();
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
    public List<IMeeting> createMeetingList() {
        List<IMeeting> meetings = new ArrayList<>();
        List<IAgendaItem> astronautsMeetingAgenda = new ArrayList<>();
        astronautsMeetingAgenda.add(AgendaItem.builder().text("Calculate journey duration").build());
        astronautsMeetingAgenda.add(AgendaItem.builder().text("Discuss who may press the launch button of the rocket").build());
        astronautsMeetingAgenda.add(AgendaItem.builder().text("Complain that this is all rocket science").build());
        Set<String> astronautsMeetingAssistances = new HashSet<>();
        astronautsMeetingAssistances.add(RoomUnlockingInfo.ASSISTANCE_ID);
        Map<String, IAssistanceConfiguration> astronautsMeetingAssistanceConfigurations = new HashMap<>();
        astronautsMeetingAssistanceConfigurations.put(RoomUnlockingInfo.ASSISTANCE_ID, new RoomUnlockingInfo.Configuration(DeviceId.of("dummy ID")));   // TODO
        meetings.add(Meeting.builder()
                .id(MEETING_ID_MARS)
                .title("Meeting about travelling to Mars")
                .workgroupId(WORKGROUP_ID_ASTRONAUTS)
                .roomId(ROOM_ID_BLACK)
                .agenda(astronautsMeetingAgenda)
                .assistanceIds(astronautsMeetingAssistances)
                .assistanceConfigurationsById(astronautsMeetingAssistanceConfigurations)
                .start(timeBase.plusSeconds(0))
                .end(timeBase.plusSeconds(300)).build());
        return meetings;
    }

    @Override
    public List<IRoom> createRoomList() {
        List<IRoom> rooms = new ArrayList<>();
        List<DeviceId> blackRoomDevices = new ArrayList<>();
        blackRoomDevices.add(DEVICE_ID_BLACK_DISPLAY);
        rooms.add(Room.builder()
                .id(ROOM_ID_BLACK)
                .name("Room Black")
                .deviceIds(blackRoomDevices)
                .build());
        return rooms;
    }

    @Override
    public List<IDevice> createDeviceList() {
        List<IDevice> devices = new ArrayList<>();
        devices.add(Device.builder()
                .id(DEVICE_ID_BLACK_DISPLAY)
                .type(DummyDisplay.DEVICE_TYPE)
                .name("Display in Room Black")
                .responsibleDelegate(DELEGATE_ID_BLACK)
                .build());
        return devices;
    }
}
