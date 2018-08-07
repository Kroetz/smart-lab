package de.qaware.smartlab.core.data.meeting;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.qaware.smartlab.core.data.generic.IDto;
import lombok.*;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssistanceConfigurationDto implements IDto {

    String assistanceId;
    private Map<String, String> configProperties;
}
