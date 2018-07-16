package de.qaware.smartlabsampledata.factory;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.Coordinates;
import de.qaware.smartlabaction.action.actor.github.GithubKnowledgeBaseInfo;
import de.qaware.smartlabassistance.assistance.info.agendashowing.AgendaShowingInfo;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabassistance.assistance.info.generic.IAssistanceInfo;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class AstronautsDataFactory extends AbstractSampleDataFactory {

    public static final WorkgroupId WORKGROUP_ID_ASTRONAUTS = WorkgroupId.of("astronauts");
    public static final PersonId MEMBER_ID_ALEX = PersonId.of("alex");
    public static final PersonId MEMBER_ID_BEVERLY = PersonId.of("beverly");
    public static final PersonId MEMBER_ID_CHARLOTTE = PersonId.of("charlotte");
    public static final RoomId ROOM_ID_BLACK = RoomId.of("black");
    public static final DeviceId DEVICE_ID_BLACK_DISPLAY = DeviceId.of("black-display");
    public static final DeviceId DEVICE_ID_BLACK_WEB_BROWSER = DeviceId.of("black-web-browser");
    public static final MeetingId MEETING_ID_MARS = MeetingId.of("mars", ROOM_ID_BLACK);
    public static final String DELEGATE_ID_BLACK = "black-delegate";

    private final IAssistanceInfo agendaShowingInfo;
    private final IAssistanceInfo roomUnlockingInfo;

    public AstronautsDataFactory(
            IAssistanceInfo agendaShowingInfo,
            IAssistanceInfo roomUnlockingInfo) {
        super();
        this.agendaShowingInfo = agendaShowingInfo;
        this.roomUnlockingInfo = roomUnlockingInfo;
    }

    @Override
    public Set<IWorkgroup> createWorkgroupSet() {
        Set<IWorkgroup> workgroups = new HashSet<>();
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
    public Set<IPerson> createWorkgroupMemberSet() {
        Set<IPerson> workgroupMembers = new HashSet<>();
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
    public Set<IMeeting> createMeetingSet() {
        Set<IMeeting> meetings = new HashSet<>();
        List<IAgendaItem> astronautsMeetingAgenda = new ArrayList<>();
        astronautsMeetingAgenda.add(AgendaItem.builder().content("Calculate journey duration").build());
        astronautsMeetingAgenda.add(AgendaItem.builder().content("Discuss who may press the launch button of the rocket").build());
        astronautsMeetingAgenda.add(AgendaItem.builder().content("Complain that this is all rocket science").build());
        Set<IAssistanceConfiguration> configs = new HashSet<>();
        configs.add(this.agendaShowingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .put(AgendaShowingInfo.Configuration.CONFIG_PROPERTY_KEY_WEB_BROWSER_ID, DEVICE_ID_BLACK_WEB_BROWSER.getIdValue())
                .build()));
        configs.add(this.roomUnlockingInfo.createConfiguration(ImmutableMap
                .<String, String>builder()
                .build()));
        meetings.add(Meeting.builder()
                .id(MEETING_ID_MARS)
                .title("Meeting about travelling to Mars")
                .workgroupId(WORKGROUP_ID_ASTRONAUTS)
                .agenda(astronautsMeetingAgenda)
                .assistanceConfigurations(configs)
                .start(timeBase.plusSeconds(0))
                .end(timeBase.plusSeconds(300)).build());
        return meetings;
    }

    @Override
    public Set<IRoom> createRoomSet() {
        Set<IRoom> rooms = new HashSet<>();
        Set<DeviceId> blackRoomDevices = new HashSet<>();
        blackRoomDevices.add(DEVICE_ID_BLACK_DISPLAY);
        rooms.add(Room.builder()
                .id(ROOM_ID_BLACK)
                .name("Room Black")
                .deviceIds(blackRoomDevices)
                .build());
        return rooms;
    }

    @Override
    public Set<IDevice> createDeviceSet() {
        Set<IDevice> devices = new HashSet<>();
        devices.add(Device.builder()
                .id(DEVICE_ID_BLACK_DISPLAY)
                .type(DummyDisplay.DEVICE_TYPE)
                .name("Display in Room Black")
                .responsibleDelegate(DELEGATE_ID_BLACK)
                .build());
        return devices;
    }
}
