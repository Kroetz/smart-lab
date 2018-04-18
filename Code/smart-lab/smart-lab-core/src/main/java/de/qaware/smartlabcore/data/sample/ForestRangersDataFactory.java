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

    public ForestRangersDataFactory() {
        super();
    }

    @Override
    public Map<Long, IWorkgroup> createWorkgroups() throws MalformedURLException {
        val workgroups = new HashMap<Long, IWorkgroup>();
        val forestRangerMembers = new ArrayList<Long>();
        forestRangerMembers.add(3L);
        forestRangerMembers.add(4L);
        forestRangerMembers.add(5L);
        workgroups.put(1L, Workgroup.builder()
                .id(1L)
                .name("Forest Rangers")
                .memberIds(forestRangerMembers)
                .knowledgeBase(new URL("http", "forest-rangers.com", 80, "/wiki"))
                .codeRepository(new URL("http", "forest-rangers.com", 80, "/git"))
                .build());
        return workgroups;
    }

    @Override
    public Map<Long, IPerson> createWorkgroupMembers() {
        val workgroupMembers = new HashMap<Long, IPerson>();
        workgroupMembers.put(3L, Person.builder()
                .id(3L)
                .name("Forest Ranger Anna")
                .email("anna@forest-rangers.com")
                .build());
        workgroupMembers.put(4L, Person.builder()
                .id(4L)
                .name("Forest Ranger Barry")
                .email("barry@forest-rangers.com")
                .build());
        workgroupMembers.put(5L, Person.builder()
                .id(5L)
                .name("Forest Ranger Caroline")
                .email("caroline@forest-rangers.com")
                .build());
        return workgroupMembers;
    }

    @Override
    public Map<Long, IMeeting> createMeetings() {
        val meetings = new HashMap<Long, IMeeting>();
        val forestRangersMeetingAgenda = new ArrayList<IAgendaItem>();
        forestRangersMeetingAgenda.add(AgendaItem.builder().text("Show potential damage").build());
        forestRangersMeetingAgenda.add(AgendaItem.builder().text("Show increase in population").build());
        forestRangersMeetingAgenda.add(AgendaItem.builder().text("Laugh together").build());
        val forestRangersMeetingAssistances = new HashSet<IAssistanceDao>();
        forestRangersMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.MINUTE_TAKING).build());
        forestRangersMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.ROOM_UNLOCKING).build());
        meetings.put(1L, Meeting.builder()
                .id(1L)
                .title("Meeting about the danger of the bark beetle")
                .workgroupId(1L)
                .roomId(1L)
                .agenda(forestRangersMeetingAgenda)
                .assistances(forestRangersMeetingAssistances)
                .start(timeBase.plusSeconds(60))
                .end(timeBase.plusSeconds(360)).build());
        return meetings;
    }

    @Override
    public Map<Long, IRoom> createRooms() {
        val rooms = new HashMap<Long, IRoom>();
        val greenRoomDevices = new ArrayList<Long>();
        greenRoomDevices.add(1L);
        rooms.put(1L, Room.builder()
                .id(1L)
                .name("Room Green")
                .deviceIds(greenRoomDevices)
                .build());
        return rooms;
    }

    @Override
    public Map<Long, IDevice> createDevices() {
        val devices = new HashMap<Long, IDevice>();
        devices.put(1L, AcmeMicrophone.builder()
                .id(1L)
                .name("Microphone in Room Green")
                .dummyMicrophoneProperty("Dummy property of Microphone in Room Green")
                .build());
        return devices;
    }
}
