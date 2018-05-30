package de.qaware.smartlabsampledata.factory;

import com.jcabi.github.Coordinates;
import de.qaware.smartlabassistance.assistance.RoomUnlocking;
import de.qaware.smartlabcommons.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcommons.data.device.display.DummyDisplay;
import de.qaware.smartlabcommons.data.device.entity.Device;
import de.qaware.smartlabcommons.data.device.entity.IDevice;
import de.qaware.smartlabcommons.data.meeting.AgendaItem;
import de.qaware.smartlabcommons.data.meeting.IAgendaItem;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.meeting.Meeting;
import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcommons.data.person.Person;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.data.room.Room;
import de.qaware.smartlabcommons.data.workgroup.GithubKnowledgeBaseInfo;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import de.qaware.smartlabcommons.miscellaneous.Constants;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ForestRangersDataFactory extends AbstractSampleDataFactory {

    public static final String WORKGROUP_ID_FOREST_RANGERS = "forest-rangers";
    public static final String MEMBER_ID_ANNA = "forest-ranger-anna";
    public static final String MEMBER_ID_BARRY = "forest-ranger-barry";
    public static final String MEMBER_ID_CAROLINE = "forest-ranger-caroline";
    public static final String MEETING_ID_BARK_BEETLE = "bark-beetle";
    public static final String ROOM_ID_GREEN = "green";
    public static final String DEVICE_ID_GREEN_DISPLAY = "green-display";
    public static final String DELEGATE_ID_GREEN = "green-delegate";

    public ForestRangersDataFactory() {
        super();
    }

    @Override
    public List<IWorkgroup> createWorkgroupList() {
        List<IWorkgroup> workgroups = new ArrayList<>();
        Set<String> forestRangerMembers = new HashSet<>();
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
    public List<IPerson> createWorkgroupMemberList() {
        List<IPerson> workgroupMembers = new ArrayList<>();
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
    public List<IMeeting> createMeetingList() {
        List<IMeeting> meetings = new ArrayList<>();
        List<IAgendaItem> forestRangersMeetingAgenda = new ArrayList<>();
        forestRangersMeetingAgenda.add(AgendaItem.builder().text("Show potential damage").build());
        forestRangersMeetingAgenda.add(AgendaItem.builder().text("Show increase in population").build());
        forestRangersMeetingAgenda.add(AgendaItem.builder().text("Laugh together").build());
        Set<String> forestRangersMeetingAssistances = new HashSet<>();
        forestRangersMeetingAssistances.add(Constants.ROOM_UNLOCKING);
        Map<String, IAssistanceConfiguration> forestRangersMeetingAssistanceConfigurations = new HashMap<>();
        forestRangersMeetingAssistanceConfigurations.put(RoomUnlocking.ASSISTANCE_ID, new RoomUnlocking.Configuration("dummy ID"));
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
    public List<IRoom> createRoomList() {
        List<IRoom> rooms = new ArrayList<>();
        List<String> greenRoomDevices = new ArrayList<>();
        greenRoomDevices.add(DEVICE_ID_GREEN_DISPLAY);
        rooms.add(Room.builder()
                .id(ROOM_ID_GREEN)
                .name("Room Green")
                .deviceIds(greenRoomDevices)
                .build());
        return rooms;
    }

    @Override
    public List<IDevice> createDeviceList() {
        List<IDevice> devices = new ArrayList<>();
        devices.add(Device.builder()
                .id(DEVICE_ID_GREEN_DISPLAY)
                .type(DummyDisplay.DEVICE_TYPE)
                .name("Display in Room Green")
                .responsibleDelegate(DELEGATE_ID_GREEN)
                .build());
        return devices;
    }
}
