package de.qaware.smartlabcore.data.meeting;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.qaware.smartlabcore.data.generic.IDto;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
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
public class MeetingDto implements IDto {

    private MeetingId id;
    private String title;
    private WorkgroupId workgroupId;
    private List<AgendaItemDto> agenda;
    private Set<AssistanceConfigurationDto> assistanceConfigurations;
    private Instant start;
    private Instant end;
}
