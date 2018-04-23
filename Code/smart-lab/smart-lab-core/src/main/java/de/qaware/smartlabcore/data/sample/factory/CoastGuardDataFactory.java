package de.qaware.smartlabcore.data.sample.factory;

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
import java.util.HashSet;
import java.util.List;

@Component
@Slf4j
public class CoastGuardDataFactory extends AbstractSampleDataFactory {

    public static final String WORKGROUP_ID_COAST_GUARD = "coast-guard";
    public static final String MEMBER_ID_ALICE = "coast-guard-alice";
    public static final String MEMBER_ID_BEN = "coast-guard-ben";
    public static final String MEMBER_ID_CHARLIE = "coast-guard-charlie";
    public static final String MEETING_ID_WHALES = "whales";
    public static final String MEETING_ID_WHIRLPOOLS = "whirlpools";
    public static final String ROOM_ID_BLUE = "blue";
    public static final String DEVICE_ID_BLUE_DISPLAY = "blue-display";

    public CoastGuardDataFactory() {
        super();
    }

    @Override
    public List<IWorkgroup> createWorkgroupList() {
        val workgroups = new ArrayList<IWorkgroup>();
        val coastGuardMembers = new ArrayList<String>();
        coastGuardMembers.add(MEMBER_ID_ALICE);
        coastGuardMembers.add(MEMBER_ID_BEN);
        coastGuardMembers.add(MEMBER_ID_CHARLIE);
        try {
            workgroups.add(Workgroup.builder()
                    .id(WORKGROUP_ID_COAST_GUARD)
                    .name("Coast Guard")
                    .memberIds(coastGuardMembers)
                    .knowledgeBase(new URL("http", "coast-guard.com", 80, "/wiki"))
                    .codeRepository(new URL("http", "coast-guard.com", 80, "/git"))
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
                .id(MEMBER_ID_ALICE)
                .name("Coast Guard Alice")
                .email("alice@coast-guard.com")
                .build());
        workgroupMembers.add(Person.builder()
                .id(MEMBER_ID_BEN)
                .name("Coast Guard Ben")
                .email("ben@coast-guard.com")
                .build());
        workgroupMembers.add(Person.builder()
                .id(MEMBER_ID_CHARLIE)
                .name("Coast Guard Charlie")
                .email("charlie@coast-guard.com")
                .build());
        return workgroupMembers;
    }

    @Override
    public List<IMeeting> createMeetingList() {
        val meetings = new ArrayList<IMeeting>();
        val whaleMeetingAgenda = new ArrayList<IAgendaItem>();
        whaleMeetingAgenda.add(AgendaItem.builder().text("Show critical areas").build());
        whaleMeetingAgenda.add(AgendaItem.builder().text("Explain whale anatomy").build());
        whaleMeetingAgenda.add(AgendaItem.builder().text("Drink coffee").build());
        val whaleMeetingAssistances = new HashSet<IAssistanceDao>();
        whaleMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.MINUTE_TAKING).build());
        whaleMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.ROOM_UNLOCKING).build());
        meetings.add(Meeting.builder()
                .id(MEETING_ID_WHALES)
                .title("Meeting about preventing illegal whale hunting")
                .workgroupId(WORKGROUP_ID_COAST_GUARD)
                .roomId(ROOM_ID_BLUE)
                .agenda(whaleMeetingAgenda)
                .assistances(whaleMeetingAssistances)
                .start(timeBase.plusSeconds(0))
                .end(timeBase.plusSeconds(300)).build());

        val whirlpoolMeetingAgenda = new ArrayList<IAgendaItem>();
        whirlpoolMeetingAgenda.add(AgendaItem.builder().text("Explain how whirlpools develop").build());
        whirlpoolMeetingAgenda.add(AgendaItem.builder().text("Show how you can escape whirlpools").build());
        whirlpoolMeetingAgenda.add(AgendaItem.builder().text("Admire the fine weather").build());
        val whirlpoolMeetingAssistances = new HashSet<IAssistanceDao>();
        whirlpoolMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.MINUTE_TAKING).build());
        whirlpoolMeetingAssistances.add(AssistanceDao.builder().assistance(Constants.ROOM_UNLOCKING).build());
        meetings.add(Meeting.builder()
                .id(MEETING_ID_WHIRLPOOLS)
                .title("Meeting about dangers of whirlpools")
                .workgroupId(WORKGROUP_ID_COAST_GUARD)
                .roomId(ROOM_ID_BLUE)
                .agenda(whirlpoolMeetingAgenda)
                .assistances(whirlpoolMeetingAssistances)
                .start(timeBase.plusSeconds(360))
                .end(timeBase.plusSeconds(660)).build());

        return meetings;
    }

    @Override
    public List<IRoom> createRoomList() {
        val rooms = new ArrayList<IRoom>();
        val blueRoomDevices = new ArrayList<String>();
        blueRoomDevices.add(DEVICE_ID_BLUE_DISPLAY);
        rooms.add(Room.builder()
                .id(ROOM_ID_BLUE)
                .name("Room Blue")
                .deviceIds(blueRoomDevices)
                .build());
        return rooms;
    }

    @Override
    public List<IDevice> createDeviceList() {
        val devices = new ArrayList<IDevice>();
        devices.add(AcmeDisplay.builder()
                .id(DEVICE_ID_BLUE_DISPLAY)
                .name("Display in Room Blue")
                .dummyDisplayProperty("Dummy property of Display in Room Blue")
                .build());
        return devices;
    }
}
