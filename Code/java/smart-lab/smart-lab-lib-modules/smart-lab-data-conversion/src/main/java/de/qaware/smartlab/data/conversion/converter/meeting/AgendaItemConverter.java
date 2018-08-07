package de.qaware.smartlab.data.conversion.converter.meeting;

import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.data.meeting.AgendaItem;
import de.qaware.smartlab.core.data.meeting.AgendaItemDto;
import de.qaware.smartlab.core.data.meeting.IAgendaItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AgendaItemConverter implements IDtoConverter<IAgendaItem, AgendaItemDto> {

    /*
     * TODO: Map fields automatically without breaking the immutability of the entity classes
     * Maybe http://modelmapper.org/ is appropriate for this task
     */

    @Override
    public AgendaItemDto toDto(IAgendaItem agendaItem) {
        return AgendaItemDto.builder()
                .content(agendaItem.getContent())
                .build();
    }

    @Override
    public IAgendaItem toEntity(AgendaItemDto agendaItem) {
        return AgendaItem.of(agendaItem.getContent());
    }
}
