package de.qaware.smartlab.core.data.actuator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.qaware.smartlab.core.data.generic.IDto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActuatorDto implements IDto {

    private ActuatorId id;
    private String type;
    private String name;
    private String responsibleDelegate;
}
