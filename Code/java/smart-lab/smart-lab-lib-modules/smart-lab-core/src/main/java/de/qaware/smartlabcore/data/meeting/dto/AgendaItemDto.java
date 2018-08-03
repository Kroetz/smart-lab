package de.qaware.smartlabcore.data.meeting.dto;

import de.qaware.smartlabcore.data.generic.IDto;
import de.qaware.smartlabcore.data.meeting.AgendaItem;
import de.qaware.smartlabcore.data.meeting.IAgendaItem;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AgendaItemDto implements IDto {

    private String content;

    public IAgendaItem toEntity() {
        return AgendaItem.of(this.content);
    }
}
