package de.qaware.smartlab.monolith.service.connector.workgroup;

import de.qaware.smartlab.api.service.connector.workgroup.IWorkgroupManagementService;
import de.qaware.smartlab.core.data.event.EventDto;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.data.workgroup.WorkgroupDto;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.constant.Property;
import de.qaware.smartlab.core.service.url.IServiceBaseUrlGetter;
import de.qaware.smartlab.monolith.service.url.AbstractMonolithicBaseUrlGetter;
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
        return requireNonNull(response.getBody()).stream().map(this.eventConverter::toEntity).collect(toSet());
    }

    @Override
    public IEvent getCurrentEvent(WorkgroupId workgroupId) {
        ResponseEntity<EventDto> response = this.workgroupManagementController.getCurrentEvent(workgroupId.getIdValue());
        return this.eventConverter.toEntity(requireNonNull(response.getBody()));
    }

    @Override
    public void extendCurrentEvent(WorkgroupId workgroupId, Duration extension) {
        this.workgroupManagementController.extendCurrentEvent(workgroupId.getIdValue(), extension.toMinutes());
    }

    @Component
    @Qualifier(IServiceBaseUrlGetter.QUALIFIER_WORKGROUP_MANAGEMENT_SERVICE_BASE_URL_GETTER)
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
