package de.qaware.smartlabcommons.data.context;

import de.qaware.smartlabcommons.api.service.meeting.IMeetingManagementService;
import de.qaware.smartlabcommons.api.service.person.IPersonManagementService;
import de.qaware.smartlabcommons.api.service.room.IRoomManagementService;
import de.qaware.smartlabcommons.api.service.workgroup.IWorkgroupManagementService;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.person.IPerson;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Data
public class Context implements IContext {

    private IMeeting meeting;
    private IWorkgroup workgroup;
    private Set<IPerson> persons;
    private IRoom room;

    private Context() { }

    @Override
    public Optional<IMeeting> getMeeting() {
        return Optional.ofNullable(meeting);
    }

    @Override
    public Optional<IWorkgroup> getWorkgroup() {
        return Optional.ofNullable(workgroup);
    }

    @Override
    public Optional<Set<IPerson>> getPersons() {
        return Optional.ofNullable(persons);
    }

    @Override
    public Optional<IRoom> getRoom() {
        return Optional.ofNullable(room);
    }

    @Component
    public static class ContextFactory implements IContextFactory {

        private IMeetingManagementService meetingManagementService;
        private IWorkgroupManagementService workgroupManagementService;
        private IPersonManagementService personManagementService;
        private IRoomManagementService roomManagementService;

        public ContextFactory(
                IMeetingManagementService meetingManagementService,
                IWorkgroupManagementService workgroupManagementService,
                IPersonManagementService personManagementService,
                IRoomManagementService roomManagementService) {
            this.meetingManagementService = meetingManagementService;
            this.workgroupManagementService = workgroupManagementService;
            this.personManagementService = personManagementService;
            this.roomManagementService = roomManagementService;
        }

        public IContext ofMeeting(IMeeting meeting) {
            return addBasedOnMeeting(new Context(), meeting);
        }

        public IContext ofWorkgroup(IWorkgroup workgroup) {
            return addBasedOnWorkgroup(new Context(), workgroup);
        }

        public IContext ofPersons(Set<IPerson> persons) {
            return addBasedOnPersons(new Context(), persons);
        }

        public IContext ofRoom(IRoom room) {
            return addBasedOnRoom(new Context(), room);
        }

        private Context addBasedOnMeeting(Context context, IMeeting meeting) {
            context.setMeeting(meeting);
            IWorkgroup workgroup = workgroupManagementService.findOne(meeting.getWorkgroupId());
            IRoom room = roomManagementService.findOne(meeting.getRoomId());
            return addBasedOnRoom(addBasedOnWorkgroup(context, workgroup), room);
        }

        private Context addBasedOnWorkgroup(Context context, IWorkgroup workgroup) {
            context.setWorkgroup(workgroup);
            Set<String> workgroupMemberIds = workgroup.getMemberIds();
            Set<IPerson> workgroupMembers = personManagementService.findMultiple(
                    workgroup.getMemberIds().toArray(new String[workgroupMemberIds.size()]));
            return addBasedOnPersons(context, workgroupMembers);
        }

        private Context addBasedOnPersons(Context context, Set<IPerson> persons) {
            context.setPersons(persons);
            return context;
        }

        private Context addBasedOnRoom(Context context, IRoom room) {
            context.setRoom(room);
            return context;
        }
    }
}
