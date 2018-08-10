package de.qaware.smartlab.monolith.service.connector.event;

import de.qaware.smartlab.api.service.connector.event.IEventManagementService;
import de.qaware.smartlab.core.data.event.EventDto;
import de.qaware.smartlab.core.data.event.EventId;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.miscellaneous.Property;
import de.qaware.smartlab.core.service.url.IServiceBaseUrlGetter;
import de.qaware.smartlab.monolith.service.url.AbstractMonolithicBaseUrlGetter;
import de.qaware.smartlab.event.management.service.controller.EventManagementController;
import de.qaware.smartlab.monolith.service.connector.generic.AbstractBasicEntityManagementMonolithicServiceConnector;
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
public class EventManagementMonolithicServiceConnector extends AbstractBasicEntityManagementMonolithicServiceConnector<IEvent, EventId, EventDto> implements IEventManagementService {

    private final EventManagementController eventManagementController;

    public EventManagementMonolithicServiceConnector(
            EventManagementController eventManagementController,
            IDtoConverter<IEvent, EventDto> eventConverter) {
        super(eventManagementController, eventConverter);
        this.eventManagementController = eventManagementController;
    }

    @Override
    public Set<IEvent> findAll(LocationId locationId) {
        return this.eventManagementController.findAllByLocationId(locationId.getIdValue()).stream()
                .map(this.converter::toEntity)
                .collect(toSet());
    }

    @Override
    public Set<IEvent> findAll(WorkgroupId workgroupId) {
        return this.eventManagementController.findAllByWorkgroupId(workgroupId.getIdValue()).stream()
                .map(this.converter::toEntity)
                .collect(toSet());
    }

    @Override
    public Set<IEvent> findAllCurrent() {
        return this.eventManagementController.findAllCurrent().stream()
                .map(this.converter::toEntity)
                .collect(toSet());
    }

    @Override
    public IEvent findCurrent(LocationId locationId) {
        ResponseEntity<EventDto> response = this.eventManagementController.findCurrentByLocationId(locationId.getIdValue());
        return this.converter.toEntity(requireNonNull(response.getBody()));
    }

    @Override
    public IEvent findCurrent(WorkgroupId workgroupId) {
        ResponseEntity<EventDto> response = this.eventManagementController.findCurrentByWorkgroupId(workgroupId.getIdValue());
        return this.converter.toEntity(requireNonNull(response.getBody()));
    }

    @Override
    public void shortenEvent(EventId eventId, Duration shortening) {
        this.eventManagementController.shortenEvent(
                eventId.getIdValue(),
                shortening.toMinutes());
    }

    @Override
    public void extendEvent(EventId eventId, Duration extension) {
        this.eventManagementController.extendEvent(
                eventId.getIdValue(),
                extension.toMinutes());
    }

    @Override
    public void shiftEvent(EventId eventId, Duration shift) {
        this.eventManagementController.shiftEvent(
                eventId.getIdValue(),
                shift.toMinutes());
    }

    @Component
    @Qualifier(IServiceBaseUrlGetter.QUALIFIER_EVENT_MANAGEMENT_SERVICE_BASE_URL_GETTER)
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MONOLITH)
    public static class BaseUrlGetter extends AbstractMonolithicBaseUrlGetter {

        public BaseUrlGetter(EventManagementController.BaseUrlController baseUrlController) {
            super(baseUrlController);
        }
    }
}
