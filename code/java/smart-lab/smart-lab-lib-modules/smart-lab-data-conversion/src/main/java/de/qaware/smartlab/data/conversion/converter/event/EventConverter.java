package de.qaware.smartlab.data.conversion.converter.event;

import de.qaware.smartlab.core.assistance.IAssistanceConfiguration;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.data.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Component
@Slf4j
public class EventConverter implements IDtoConverter<IEvent, EventDto> {

    /*
     * TODO: Map fields automatically without breaking the immutability of the entity classes
     * Maybe http://modelmapper.org/ is appropriate for this task
     */

    private final IDtoConverter<IAgendaItem, AgendaItemDto> agendaItemConverter;
    private final IDtoConverter<IAssistanceConfiguration, AssistanceConfigurationDto> assistanceConfigurationConverter;

    public EventConverter(
            IDtoConverter<IAgendaItem, AgendaItemDto> agendaItemConverter,
            IDtoConverter<IAssistanceConfiguration, AssistanceConfigurationDto> assistanceConfigurationConverter) {
        this.agendaItemConverter = agendaItemConverter;
        this.assistanceConfigurationConverter = assistanceConfigurationConverter;
    }

    @Override
    public EventDto toDto(IEvent event) {
        return EventDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .workgroupId(event.getWorkgroupId())
                .agenda(event.getAgenda().stream()
                        .map(this.agendaItemConverter::toDto)
                        .collect(toList()))
                .assistanceConfigurations(event.getAssistanceConfigurations().stream()
                        .map(this.assistanceConfigurationConverter::toDto)
                        .collect(toSet()))
                .start(event.getStart())
                .end(event.getEnd())
                .build();
    }

    @Override
    public IEvent toEntity(EventDto event) {
        return Event.of(
                event.getId(),
                event.getTitle(),
                event.getWorkgroupId(),
                event.getAgenda().stream()
                        .map(this.agendaItemConverter::toEntity)
                        .collect(toList()),
                event.getAssistanceConfigurations().stream()
                        .map(this.assistanceConfigurationConverter::toEntity)
                        .collect(toSet()),
                event.getStart(),
                event.getEnd());
    }
}
