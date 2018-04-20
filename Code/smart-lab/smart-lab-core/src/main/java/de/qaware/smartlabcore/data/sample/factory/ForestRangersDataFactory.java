package de.qaware.smartlabcore.data.sample.factory;

import de.qaware.smartlabcommons.Constants;
import de.qaware.smartlabcommons.data.device.AcmeMicrophone;
import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcommons.data.meeting.AgendaItem;
import de.qaware.smartlabcommons.data.meeting.IAgendaItem;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.meeting.Meeting;
import de.qaware.smartlabcommons.data.meeting.assistance.AssistanceDao;
import de.qaware.smartlabcommons.data.meeting.assistance.IAssistanceDao;
import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcommons.data.person.Person;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.data.room.Room;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
@Slf4j
public class ForestRangersDataFactory extends AbstractSampleDataFactory {

    public static final String WORKGROUP_ID_FOREST_RANGERS = "forest-rangers";
    public static final String MEMBER_ID_ANNA = "forest-ranger-anna";
    public static final String MEMBER_ID_BARRY = "forest-ranger-barry";
    public static final String MEMBER_ID_CAROLINE = "forest-ranger-caroline";
    public static final String MEETING_ID_BARK_BEETLE = "bark-beetle";
    public static final String ROOM_ID_GREEN = "green";
    public static final String DEVICE_ID_GREEN_MICROPHONE = "green-microphone";

    public ForestRangersDataFactory() {
        super();
    }

    @Override
    public List<IWorkgroup> createWorkgroupList() {
        val workgroups = new ArrayList<IWorkgroup>();
        val forestRangerMembers = new ArrayList<String>();
        forestRangerMembers.add(MEMBER_ID_ANNA);
        forestRangerMembers.add(MEMBER_ID_BARRY);
        forestRangerMembers.add(MEMBER_ID_CAROLINE);
        try {
            workgroups.add(Workgroup.builder()
                    .id(WORKGROUP_ID_FOREST_RANGERS)
                    .name("Forest Rangers")
                    .memberIds(forestRangerMembers)
                    .knowledgeBase(new URL("http", "forest-rangers.com", 80, "/wiki"))
                    .codeRepository(new URL("http", "forest-rangers.com", 80, "/git"))
                    .build());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            assert false;
        }
        return workgroups;
    }

    @Override
    public List<IPerson> createWorkgroupMemberList() {
        val workgroupMembers = new ArrayList<IPerson>();
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
        val meetings = new ArrayList<IMeeting>();
        val forestRangersMeetingAgenda = new ArrayList<IAgendaItem>();
        forestRangersMeetingAgenda.add(AgendaItem.builder().text("Show potential damage").build());
        forestRangersMeetingAgenda.add(AgendaItem.builder().text("Show increase in population").build());
        forestRangersMeetingAgenda.add(AgendaItem.builder().text("Laugh together").build());
        val forestRangersMeetingAssistances = new HashSet<IAssistanceDao>();
        forestRangersMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.MINUTE_TAKING).build());
        forestRangersMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.ROOM_UNLOCKING).build());
        meetings.add(Meeting.builder()
                .id(MEETING_ID_BARK_BEETLE)
                .title("Meeting about the danger of the bark beetle")
                .workgroupId(WORKGROUP_ID_FOREST_RANGERS)
                .roomId(ROOM_ID_GREEN)
                .agenda(forestRangersMeetingAgenda)
                .assistances(forestRangersMeetingAssistances)
                .start(timeBase.plusSeconds(60))
                .end(timeBase.plusSeconds(360)).build());
        return meetings;
    }

    @Override
    public List<IRoom> createRoomList() {
        val rooms = new ArrayList<IRoom>();
        val greenRoomDevices = new ArrayList<String>();
        greenRoomDevices.add(DEVICE_ID_GREEN_MICROPHONE);
        rooms.add(Room.builder()
                .id(ROOM_ID_GREEN)
                .name("Room Green")
                .deviceIds(greenRoomDevices)
                .build());
        return rooms;
    }

    @Override
    public List<IDevice> createDeviceList() {
        val devices = new ArrayList<IDevice>();
        devices.add(AcmeMicrophone.builder()
                .id(DEVICE_ID_GREEN_MICROPHONE)
                .name("Microphone in Room Green")
                .dummyMicrophoneProperty("Dummy property of Microphone in Room Green")
                .build());
        return devices;
    }
}
