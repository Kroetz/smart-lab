package de.qaware.smartlab.monolith.service.connector.workgroup;

import de.qaware.smartlab.api.service.connector.workgroup.IWorkgroupManagementService;
import de.qaware.smartlab.core.data.event.EventDto;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.data.workgroup.WorkgroupDto;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.exception.EntityConflictException;
import de.qaware.smartlab.core.exception.EntityNotFoundException;
import de.qaware.smartlab.core.exception.MaximalDurationReachedException;
import de.qaware.smartlab.core.exception.UnknownErrorException;
import de.qaware.smartlab.core.miscellaneous.Property;
import de.qaware.smartlab.core.service.url.AbstractMonolithicBaseUrlGetter;
import de.qaware.smartlab.monolith.service.connector.generic.AbstractBasicEntityManagementMonolithicServiceConnector;
import de.qaware.smartlab.workgroup.management.service.controller.WorkgroupManagementController;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;
import static org.springframework.http.HttpStatus.*;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class WorkgroupManagementMonolithicServiceConnector extends AbstractBasicEntityManagementMonolithicServiceConnector<IWorkgroup, WorkgroupId, WorkgroupDto> implements IWorkgroupManagementService {

    private final WorkgroupManagementController workgroupManagementController;
    private final IDtoConverter<IEvent, EventDto> eventConverter;

    public WorkgroupManagementMonolithicServiceConnector(
            WorkgroupManagementController workgroupManagementController,
            IDtoConverter<IWorkgroup, WorkgroupDto> workgroupConverter,
            IDtoConverter<IEvent, EventDto> eventConverter) {
        super(workgroupManagementController, workgroupConverter);
        this.workgroupManagementController = workgroupManagementController;
        this.eventConverter = eventConverter;
    }

    @Override
    public Set<IEvent> getEventsOfWorkgroup(WorkgroupId workgroupId) {
        ResponseEntity<Set<EventDto>> response = this.workgroupManagementController.getEventsOfWorkgroup(workgroupId.getIdValue());
        if(response.getStatusCode() == OK) return requireNonNull(response.getBody()).stream().map(this.eventConverter::toEntity).collect(toSet());
        // TODO: Meaningful exception message
        if(response.getStatusCode() == NOT_FOUND) throw new EntityNotFoundException();
        throw new UnknownErrorException();
    }

    @Override
    public IEvent getCurrentEvent(WorkgroupId workgroupId) {
        ResponseEntity<EventDto> response = this.workgroupManagementController.getCurrentEvent(workgroupId.getIdValue());
        if(response.getStatusCode() == OK) return this.eventConverter.toEntity(requireNonNull(response.getBody()));
        // TODO: Meaningful exception message
        if(response.getStatusCode() == NOT_FOUND) throw new EntityNotFoundException();
        throw new UnknownErrorException();
    }

    @Override
    public void extendCurrentEvent(WorkgroupId workgroupId, Duration extension) {
        ResponseEntity<Void> response = this.workgroupManagementController.extendCurrentEvent(workgroupId.getIdValue(), extension.toMinutes());
        if(response.getStatusCode() == OK) return;
        // TODO: Meaningful exception messages
        if(response.getStatusCode() == NOT_FOUND) throw new EntityNotFoundException();
        if(response.getStatusCode() == CONFLICT) throw new EntityConflictException();
        if(response.getStatusCode() == UNPROCESSABLE_ENTITY) throw new MaximalDurationReachedException();
        throw new UnknownErrorException();
    }

    @Component
    // TODO: String literal
    @Qualifier("workgroupManagementServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MONOLITH)
    public static class BaseUrlGetter extends AbstractMonolithicBaseUrlGetter {

        public BaseUrlGetter(WorkgroupManagementController.BaseUrlController baseUrlController) {
            super(baseUrlController);
        }
    }
}
