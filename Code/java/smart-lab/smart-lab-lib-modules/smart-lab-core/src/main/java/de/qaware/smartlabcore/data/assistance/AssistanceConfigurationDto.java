package de.qaware.smartlabcore.data.assistance;

import de.qaware.smartlabcore.data.generic.IDto;
import lombok.*;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AssistanceConfigurationDto implements IDto {

    String assistanceId;
    private Map<String, String> configProperties;
}
