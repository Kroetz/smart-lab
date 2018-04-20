package de.qaware.smartlabcore.data.sample;

import de.qaware.smartlabcommons.Constants;
import de.qaware.smartlabcommons.data.device.AcmeDisplay;
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
public class CoastGuardDataFactory extends AbstractSampleDataFactory {

    public static final String WORKGROUP_ID_COAST_GUARD = "coast-guard";
    public static final String MEMBER_ID_ALICE = "coast-guard-alice";
    public static final String MEMBER_ID_BEN = "coast-guard-ben";
    public static final String MEMBER_ID_CHARLIE = "coast-guard-charlie";
    public static final String MEETING_ID_WHALES = "whales";
    public static final String ROOM_ID_BLUE = "blue";
    public static final String DEVICE_ID_BLUE_DISPLAY = "blue-display";

    public CoastGuardDataFactory() {
        super();
    }

    @Override
    public Map<String, IWorkgroup> createWorkgroups() throws MalformedURLException {
        val workgroups = new HashMap<String, IWorkgroup>();
        val coastGuardMembers = new ArrayList<String>();
        coastGuardMembers.add(MEMBER_ID_ALICE);
        coastGuardMembers.add(MEMBER_ID_BEN);
        coastGuardMembers.add(MEMBER_ID_CHARLIE);
        workgroups.put(WORKGROUP_ID_COAST_GUARD, Workgroup.builder()
                .id(WORKGROUP_ID_COAST_GUARD)
                .name("Coast Guard")
                .memberIds(coastGuardMembers)
                .knowledgeBase(new URL("http", "coast-guard.com", 80, "/wiki"))
                .codeRepository(new URL("http", "coast-guard.com", 80, "/git"))
                .build());
        return workgroups;
    }

    @Override
    public Map<String, IPerson> createWorkgroupMembers() {
        val workgroupMembers = new HashMap<String, IPerson>();
        workgroupMembers.put(MEMBER_ID_ALICE, Person.builder()
                .id(MEMBER_ID_ALICE)
                .name("Coast Guard Alice")
                .email("alice@coast-guard.com")
                .build());
        workgroupMembers.put(MEMBER_ID_BEN, Person.builder()
                .id(MEMBER_ID_BEN)
                .name("Coast Guard Ben")
                .email("ben@coast-guard.com")
                .build());
        workgroupMembers.put(MEMBER_ID_CHARLIE, Person.builder()
                .id(MEMBER_ID_CHARLIE)
                .name("Coast Guard Charlie")
                .email("charlie@coast-guard.com")
                .build());
        return workgroupMembers;
    }

    @Override
    public Map<String, IMeeting> createMeetings() {
        val meetings = new HashMap<String, IMeeting>();
        val coastGuardMeetingAgenda = new ArrayList<IAgendaItem>();
        coastGuardMeetingAgenda.add(AgendaItem.builder().text("Show critical areas").build());
        coastGuardMeetingAgenda.add(AgendaItem.builder().text("Explain whale anatomy").build());
        coastGuardMeetingAgenda.add(AgendaItem.builder().text("Drink coffee").build());
        val coastGuardMeetingAssistances = new HashSet<IAssistanceDao>();
        coastGuardMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.MINUTE_TAKING).build());
        coastGuardMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.ROOM_UNLOCKING).build());
        meetings.put(MEETING_ID_WHALES, Meeting.builder()
                .id(MEETING_ID_WHALES)
                .title("Meeting about preventing illegal whale hunting")
                .workgroupId(WORKGROUP_ID_COAST_GUARD)
                .roomId(ROOM_ID_BLUE)
                .agenda(coastGuardMeetingAgenda)
                .assistances(coastGuardMeetingAssistances)
                .start(timeBase.plusSeconds(0))
                .end(timeBase.plusSeconds(300)).build());
        return meetings;
    }

    @Override
    public Map<String, IRoom> createRooms() {
        val rooms = new HashMap<String, IRoom>();
        val blueRoomDevices = new ArrayList<String>();
        blueRoomDevices.add(DEVICE_ID_BLUE_DISPLAY);
        rooms.put(ROOM_ID_BLUE, Room.builder()
                .id(ROOM_ID_BLUE)
                .name("Room Blue")
                .deviceIds(blueRoomDevices)
                .build());
        return rooms;
    }

    @Override
    public Map<String, IDevice> createDevices() {
        val devices = new HashMap<String, IDevice>();
        devices.put(DEVICE_ID_BLUE_DISPLAY, AcmeDisplay.builder()
                .id(DEVICE_ID_BLUE_DISPLAY)
                .name("Display in Room Blue")
                .dummyDisplayProperty("Dummy property of Display in Room Blue")
                .build());
        return devices;
    }
}
