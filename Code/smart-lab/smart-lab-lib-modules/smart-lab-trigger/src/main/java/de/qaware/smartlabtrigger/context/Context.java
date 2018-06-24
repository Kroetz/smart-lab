package de.qaware.smartlabtrigger.context;

import de.qaware.smartlabapi.service.meeting.IMeetingManagementService;
import de.qaware.smartlabapi.service.person.IPersonManagementService;
import de.qaware.smartlabapi.service.room.IRoomManagementService;
import de.qaware.smartlabapi.service.workgroup.IWorkgroupManagementService;
import de.qaware.smartlabcore.data.assistance.IAssistanceInfo;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.context.IContext;
import de.qaware.smartlabcore.data.context.IContextFactory;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.PersonId;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
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
    private IAssistanceConfiguration assistanceConfiguration;

    private Context() { }

    @Override
    public Optional<IMeeting> getMeeting() {
        return Optional.ofNullable(this.meeting);
    }

    @Override
    public Optional<IWorkgroup> getWorkgroup() {
        return Optional.ofNullable(this.workgroup);
    }

    @Override
    public Optional<Set<IPerson>> getPersons() {
        return Optional.ofNullable(this.persons);
    }

    @Override
    public Optional<IRoom> getRoom() {
        return Optional.ofNullable(this.room);
    }

    @Override
    public Optional<IAssistanceConfiguration> getAssistanceConfiguration() {
        return Optional.ofNullable(this.assistanceConfiguration);
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

        public IContext of(IMeeting meeting) {
            return addToContext(new Context(), meeting);
        }

        public IContext of(IMeeting meeting, IAssistanceInfo assistance) {
            return addToContext(new Context(), meeting, assistance);
        }

        public IContext of(IWorkgroup workgroup) {
            return addToContext(new Context(), workgroup);
        }

        public IContext of(Set<IPerson> persons) {
            return addToContext(new Context(), persons);
        }

        public IContext of(IRoom room) {
            return addToContext(new Context(), room);
        }

        // TODO: better would be "addBasedOnAssistance" but this would require the assistance to know which meeting it belongs to --> assistance would also hold data and not only function
        private Context addToContext(Context context, IMeeting meeting, IAssistanceInfo assistance) {
            // TODO. More specific exception
            context.assistanceConfiguration = meeting.getAssistanceConfiguration(assistance.getAssistanceId()).orElseThrow(RuntimeException::new);
            return addToContext(context, meeting);
        }

        private Context addToContext(Context context, IMeeting meeting) {
            context.setMeeting(meeting);
            IWorkgroup workgroup = workgroupManagementService.findOne(meeting.getWorkgroupId());
            IRoom room = roomManagementService.findOne(meeting.getRoomId());
            return addToContext(addToContext(context, workgroup), room);
        }

        private Context addToContext(Context context, IWorkgroup workgroup) {
            context.setWorkgroup(workgroup);
            Set<PersonId> workgroupMemberIds = workgroup.getMemberIds();
            Set<IPerson> workgroupMembers = personManagementService.findMultiple(
                    workgroup.getMemberIds().toArray(new PersonId[workgroupMemberIds.size()]));
            return addToContext(context, workgroupMembers);
        }

        private Context addToContext(Context context, Set<IPerson> persons) {
            context.setPersons(persons);
            return context;
        }

        private Context addToContext(Context context, IRoom room) {
            context.setRoom(room);
            return context;
        }
    }
}
