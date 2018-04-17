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
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
@Slf4j
public class ForestRangersDataFactory extends AbstractSampleDataFactory {

    @Override
    public List<IWorkgroup> createWorkgroups() throws MalformedURLException {
        val workgroups = new ArrayList<IWorkgroup>();
        val forestRangerMembers = new ArrayList<Long>();
        forestRangerMembers.add(3L);
        forestRangerMembers.add(4L);
        forestRangerMembers.add(5L);
        workgroups.add(Workgroup.builder()
                .id(1)
                .name("Forest Rangers")
                .memberIds(forestRangerMembers)
                .knowledgeBase(new URL("http", "forest-rangers.com", 80, "/wiki"))
                .codeRepository(new URL("http", "forest-rangers.com", 80, "/git"))
                .build());
        return workgroups;
    }

    @Override
    public List<IPerson> createWorkgroupMembers() {
        val workgroupMembers = new ArrayList<IPerson>();
        workgroupMembers.add(Person.builder()
                .id(3)
                .name("Forest Ranger Anna")
                .email("anna@forest-rangers.com")
                .build());
        workgroupMembers.add(Person.builder()
                .id(4)
                .name("Forest Ranger Barry")
                .email("barry@forest-rangers.com")
                .build());
        workgroupMembers.add(Person.builder()
                .id(5)
                .name("Forest Ranger Caroline")
                .email("caroline@forest-rangers.com")
                .build());
        return workgroupMembers;
    }

    @Override
    public List<IMeeting> createMeetings() {
        val meetings = new ArrayList<IMeeting>();
        val forestRangersMeetingAgenda = new ArrayList<IAgendaItem>();
        forestRangersMeetingAgenda.add(AgendaItem.builder().text("Show potential damage").build());
        forestRangersMeetingAgenda.add(AgendaItem.builder().text("Show increase in population").build());
        forestRangersMeetingAgenda.add(AgendaItem.builder().text("Laugh together").build());
        val forestRangersMeetingAssistances = new HashSet<IAssistanceDao>();
        forestRangersMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.MINUTE_TAKING).build());
        forestRangersMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.ROOM_UNLOCKING).build());
        meetings.add(Meeting.builder()
                .id(1)
                .title("Meeting about the danger of the bark beetle")
                .workgroupId(1)
                .roomId(1)
                .agenda(forestRangersMeetingAgenda)
                .assistances(forestRangersMeetingAssistances)
                .start(Instant.now().plusSeconds(60))
                .end(Instant.now().plusSeconds(360)).build());
        return meetings;
    }

    @Override
    public List<IRoom> createRooms() {
        val rooms = new ArrayList<IRoom>();
        val greenRoomDevices = new ArrayList<Long>();
        greenRoomDevices.add(1L);
        rooms.add(Room.builder()
                .id(1)
                .name("Room Green")
                .deviceIds(greenRoomDevices)
                .build());
        return rooms;
    }

    @Override
    public List<IDevice> createDevices() {
        val devices = new ArrayList<IDevice>();
        devices.add(AcmeMicrophone.builder()
                .id(1)
                .name("Microphone in Room Green")
                .dummyMicrophoneProperty("Dummy property of Microphone in Room Green")
                .build());
        return devices;
    }
}
