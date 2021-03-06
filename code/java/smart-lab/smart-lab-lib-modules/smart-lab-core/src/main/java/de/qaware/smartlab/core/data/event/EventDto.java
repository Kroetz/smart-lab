package de.qaware.smartlab.core.data.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.qaware.smartlab.core.data.generic.IDto;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDto implements IDto {

    private EventId id;
    private String title;
    private WorkgroupId workgroupId;
    private List<AgendaItemDto> agenda;
    private Set<AssistanceConfigurationDto> assistanceConfigurations;
    private Instant start;
    private Instant end;
}
