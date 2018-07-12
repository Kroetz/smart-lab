package de.qaware.smartlabassistance.context;

import de.qaware.smartlabapi.service.connector.meeting.IMeetingManagementService;
import de.qaware.smartlabapi.service.connector.person.IPersonManagementService;
import de.qaware.smartlabapi.service.connector.room.IRoomManagementService;
import de.qaware.smartlabapi.service.connector.workgroup.IWorkgroupManagementService;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import de.qaware.smartlabcore.data.context.IAssistanceContextFactory;
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
public class AssistanceContext implements IAssistanceContext {

    private IAssistanceConfiguration assistanceConfiguration;
    private IMeeting meeting;
    private IWorkgroup workgroup;
    private Set<IPerson> persons;
    private IRoom room;

    private AssistanceContext() { }

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

    @Component
    public static class Factory implements IAssistanceContextFactory {

        private IMeetingManagementService meetingManagementService;
        private IWorkgroupManagementService workgroupManagementService;
        private IPersonManagementService personManagementService;
        private IRoomManagementService roomManagementService;

        public Factory(
                IMeetingManagementService meetingManagementService,
                IWorkgroupManagementService workgroupManagementService,
                IPersonManagementService personManagementService,
                IRoomManagementService roomManagementService) {
            this.meetingManagementService = meetingManagementService;
            this.workgroupManagementService = workgroupManagementService;
            this.personManagementService = personManagementService;
            this.roomManagementService = roomManagementService;
        }

        public IAssistanceContext of(IAssistanceConfiguration config, IMeeting meeting) {
            if(!meeting.getAssistanceConfigurations().contains(config)) {
                throw new IllegalStateException("The specified assistance configuration must be part of the specified meeting");
            }
            return addToContext(addToContext(new AssistanceContext(), config), meeting);
        }

        public IAssistanceContext of(IAssistanceConfiguration config, IWorkgroup workgroup) {
            return addToContext(addToContext(new AssistanceContext(), config), workgroup);
        }

        public IAssistanceContext of(IAssistanceConfiguration config, Set<IPerson> persons) {
            return addToContext(addToContext(new AssistanceContext(), config), persons);
        }

        public IAssistanceContext of(IAssistanceConfiguration config, IRoom room) {
            return addToContext(addToContext(new AssistanceContext(), config), room);
        }

        private AssistanceContext addToContext(AssistanceContext context, IAssistanceConfiguration config) {
            context.setAssistanceConfiguration(config);
            return context;
        }

        private AssistanceContext addToContext(AssistanceContext context, IMeeting meeting) {
            context.setMeeting(meeting);
            IWorkgroup workgroup = workgroupManagementService.findOne(meeting.getWorkgroupId());
            IRoom room = roomManagementService.findOne(meeting.getRoomId());
            return addToContext(addToContext(context, workgroup), room);
        }

        private AssistanceContext addToContext(AssistanceContext context, IWorkgroup workgroup) {
            context.setWorkgroup(workgroup);
            Set<PersonId> workgroupMemberIds = workgroup.getMemberIds();
            Set<IPerson> workgroupMembers = personManagementService.findMultiple(
                    workgroup.getMemberIds().toArray(new PersonId[workgroupMemberIds.size()]));
            return addToContext(context, workgroupMembers);
        }

        private AssistanceContext addToContext(AssistanceContext context, Set<IPerson> persons) {
            context.setPersons(persons);
            return context;
        }

        private AssistanceContext addToContext(AssistanceContext context, IRoom room) {
            context.setRoom(room);
            return context;
        }
    }
}
