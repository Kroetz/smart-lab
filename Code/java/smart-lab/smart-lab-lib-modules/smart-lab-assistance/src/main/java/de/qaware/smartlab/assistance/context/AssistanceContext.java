package de.qaware.smartlab.assistance.context;

import de.qaware.smartlab.api.service.connector.event.IEventManagementService;
import de.qaware.smartlab.api.service.connector.person.IPersonManagementService;
import de.qaware.smartlab.api.service.connector.location.ILocationManagementService;
import de.qaware.smartlab.api.service.connector.workgroup.IWorkgroupManagementService;
import de.qaware.smartlab.core.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import de.qaware.smartlab.core.data.context.IAssistanceContextFactory;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.person.IPerson;
import de.qaware.smartlab.core.data.person.PersonId;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Data
public class AssistanceContext implements IAssistanceContext {

    private IAssistanceConfiguration assistanceConfiguration;
    private IEvent event;
    private IWorkgroup workgroup;
    private Set<IPerson> persons;
    private ILocation location;

    private AssistanceContext() { }

    @Override
    public Optional<IEvent> getEvent() {
        return Optional.ofNullable(this.event);
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
    public Optional<ILocation> getLocation() {
        return Optional.ofNullable(this.location);
    }

    @Component
    public static class Factory implements IAssistanceContextFactory {

        private IEventManagementService eventManagementService;
        private IWorkgroupManagementService workgroupManagementService;
        private IPersonManagementService personManagementService;
        private ILocationManagementService locationManagementService;

        public Factory(
                IEventManagementService eventManagementService,
                IWorkgroupManagementService workgroupManagementService,
                IPersonManagementService personManagementService,
                ILocationManagementService locationManagementService) {
            this.eventManagementService = eventManagementService;
            this.workgroupManagementService = workgroupManagementService;
            this.personManagementService = personManagementService;
            this.locationManagementService = locationManagementService;
        }

        public IAssistanceContext of(IAssistanceConfiguration config, IEvent event) {
            if(!event.getAssistanceConfigurations().contains(config)) {
                throw new IllegalStateException("The specified assistance configuration must be part of the specified event");
            }
            return addToContext(addToContext(new AssistanceContext(), config), event);
        }

        public IAssistanceContext of(IAssistanceConfiguration config, IWorkgroup workgroup) {
            return addToContext(addToContext(new AssistanceContext(), config), workgroup);
        }

        public IAssistanceContext of(IAssistanceConfiguration config, Set<IPerson> persons) {
            return addToContext(addToContext(new AssistanceContext(), config), persons);
        }

        public IAssistanceContext of(IAssistanceConfiguration config, ILocation location) {
            return addToContext(addToContext(new AssistanceContext(), config), location);
        }

        private AssistanceContext addToContext(AssistanceContext context, IAssistanceConfiguration config) {
            context.setAssistanceConfiguration(config);
            return context;
        }

        private AssistanceContext addToContext(AssistanceContext context, IEvent event) {
            context.setEvent(event);
            IWorkgroup workgroup = workgroupManagementService.findOne(event.getWorkgroupId());
            ILocation location = locationManagementService.findOne(event.getLocationId());
            return addToContext(addToContext(context, workgroup), location);
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

        private AssistanceContext addToContext(AssistanceContext context, ILocation location) {
            context.setLocation(location);
            return context;
        }
    }
}
