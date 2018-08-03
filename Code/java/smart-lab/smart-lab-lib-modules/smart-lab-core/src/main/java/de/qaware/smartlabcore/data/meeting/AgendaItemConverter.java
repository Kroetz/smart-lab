package de.qaware.smartlabcore.data.meeting;

import de.qaware.smartlabcore.data.generic.IDtoConverter;
import de.qaware.smartlabcore.data.meeting.dto.AgendaItemDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AgendaItemConverter implements IDtoConverter<IAgendaItem, AgendaItemDto> {

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
