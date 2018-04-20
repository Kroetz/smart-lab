package de.qaware.smartlabcore.data.sample;

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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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
    public Map<String, IWorkgroup> createWorkgroups() throws MalformedURLException {
        val workgroups = new HashMap<String, IWorkgroup>();
        val forestRangerMembers = new ArrayList<String>();
        forestRangerMembers.add(MEMBER_ID_ANNA);
        forestRangerMembers.add(MEMBER_ID_BARRY);
        forestRangerMembers.add(MEMBER_ID_CAROLINE);
        workgroups.put(WORKGROUP_ID_FOREST_RANGERS, Workgroup.builder()
                .id(WORKGROUP_ID_FOREST_RANGERS)
                .name("Forest Rangers")
                .memberIds(forestRangerMembers)
                .knowledgeBase(new URL("http", "forest-rangers.com", 80, "/wiki"))
                .codeRepository(new URL("http", "forest-rangers.com", 80, "/git"))
                .build());
        return workgroups;
    }

    @Override
    public Map<String, IPerson> createWorkgroupMembers() {
        val workgroupMembers = new HashMap<String, IPerson>();
        workgroupMembers.put(MEMBER_ID_ANNA, Person.builder()
                .id(MEMBER_ID_ANNA)
                .name("Forest Ranger Anna")
                .email("anna@forest-rangers.com")
                .build());
        workgroupMembers.put(MEMBER_ID_BARRY, Person.builder()
                .id(MEMBER_ID_BARRY)
                .name("Forest Ranger Barry")
                .email("barry@forest-rangers.com")
                .build());
        workgroupMembers.put(MEMBER_ID_CAROLINE, Person.builder()
                .id(MEMBER_ID_CAROLINE)
                .name("Forest Ranger Caroline")
                .email("caroline@forest-rangers.com")
                .build());
        return workgroupMembers;
    }

    @Override
    public Map<String, IMeeting> createMeetings() {
        val meetings = new HashMap<String, IMeeting>();
        val forestRangersMeetingAgenda = new ArrayList<IAgendaItem>();
        forestRangersMeetingAgenda.add(AgendaItem.builder().text("Show potential damage").build());
        forestRangersMeetingAgenda.add(AgendaItem.builder().text("Show increase in population").build());
        forestRangersMeetingAgenda.add(AgendaItem.builder().text("Laugh together").build());
        val forestRangersMeetingAssistances = new HashSet<IAssistanceDao>();
        forestRangersMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.MINUTE_TAKING).build());
        forestRangersMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.ROOM_UNLOCKING).build());
        meetings.put(MEETING_ID_BARK_BEETLE, Meeting.builder()
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
    public Map<String, IRoom> createRooms() {
        val rooms = new HashMap<String, IRoom>();
        val greenRoomDevices = new ArrayList<String>();
        greenRoomDevices.add(DEVICE_ID_GREEN_MICROPHONE);
        rooms.put(ROOM_ID_GREEN, Room.builder()
                .id(ROOM_ID_GREEN)
                .name("Room Green")
                .deviceIds(greenRoomDevices)
                .build());
        return rooms;
    }

    @Override
    public Map<String, IDevice> createDevices() {
        val devices = new HashMap<String, IDevice>();
        devices.put(DEVICE_ID_GREEN_MICROPHONE, AcmeMicrophone.builder()
                .id(DEVICE_ID_GREEN_MICROPHONE)
                .name("Microphone in Room Green")
                .dummyMicrophoneProperty("Dummy property of Microphone in Room Green")
                .build());
        return devices;
    }
}
