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
import de.qaware.smartlabcore.miscellaneous.Constants;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ForestRangersDataFactory extends AbstractSampleDataFactory {

    public static final WorkgroupId WORKGROUP_ID_FOREST_RANGERS = WorkgroupId.of("forest-rangers");
    public static final PersonId MEMBER_ID_ANNA = PersonId.of("forest-ranger-anna");
    public static final PersonId MEMBER_ID_BARRY = PersonId.of("forest-ranger-barry");
    public static final PersonId MEMBER_ID_CAROLINE = PersonId.of("forest-ranger-caroline");
    public static final MeetingId MEETING_ID_BARK_BEETLE = MeetingId.of("bark-beetle");
    public static final RoomId ROOM_ID_GREEN = RoomId.of("green");
    public static final DeviceId DEVICE_ID_GREEN_DISPLAY = DeviceId.of("green-display");
    public static final String DELEGATE_ID_GREEN = "green-delegate";

    public ForestRangersDataFactory() {
        super();
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
        forestRangersMeetingAgenda.add(AgendaItem.builder().text("Show potential damage").build());
        forestRangersMeetingAgenda.add(AgendaItem.builder().text("Show increase in population").build());
        forestRangersMeetingAgenda.add(AgendaItem.builder().text("Laugh together").build());
        Set<String> forestRangersMeetingAssistances = new HashSet<>();
        forestRangersMeetingAssistances.add(Constants.ROOM_UNLOCKING);
        Map<String, IAssistanceConfiguration> forestRangersMeetingAssistanceConfigurations = new HashMap<>();
        forestRangersMeetingAssistanceConfigurations.put(RoomUnlockingInfo.ASSISTANCE_ID, new RoomUnlockingInfo.Configuration(DeviceId.of("dummy ID")));    // TODO
        meetings.add(Meeting.builder()
                .id(MEETING_ID_BARK_BEETLE)
                .title("Meeting about the danger of the bark beetle")
                .workgroupId(WORKGROUP_ID_FOREST_RANGERS)
                .roomId(ROOM_ID_GREEN)
                .agenda(forestRangersMeetingAgenda)
                .assistanceIds(forestRangersMeetingAssistances)
                .assistanceConfigurationsById(forestRangersMeetingAssistanceConfigurations)
                .start(timeBase.plusSeconds(180))
                .end(timeBase.plusSeconds(480)).build());
        return meetings;
    }

    @Override
    public Set<IRoom> createRoomSet() {
        Set<IRoom> rooms = new HashSet<>();
        Set<DeviceId> greenRoomDevices = new HashSet<>();
        greenRoomDevices.add(DEVICE_ID_GREEN_DISPLAY);
        rooms.add(Room.builder()
                .id(ROOM_ID_GREEN)
                .name("Room Green")
                .deviceIds(greenRoomDevices)
                .build());
        return rooms;
    }

    @Override
    public Set<IDevice> createDeviceSet() {
        Set<IDevice> devices = new HashSet<>();
        devices.add(Device.builder()
                .id(DEVICE_ID_GREEN_DISPLAY)
                .type(DummyDisplay.DEVICE_TYPE)
                .name("Display in Room Green")
                .responsibleDelegate(DELEGATE_ID_GREEN)
                .build());
        return devices;
    }
}
