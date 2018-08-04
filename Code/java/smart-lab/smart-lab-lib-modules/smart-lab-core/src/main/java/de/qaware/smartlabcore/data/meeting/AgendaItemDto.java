package de.qaware.smartlabcore.data.meeting;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.qaware.smartlabcore.data.generic.IDto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgendaItemDto implements IDto {

    private String content;

    public IAgendaItem toEntity() {
        return AgendaItem.of(this.content);
    }
}
