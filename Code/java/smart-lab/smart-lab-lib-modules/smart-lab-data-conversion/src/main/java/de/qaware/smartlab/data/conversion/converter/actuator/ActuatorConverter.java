package de.qaware.smartlab.data.conversion.converter.actuator;

import de.qaware.smartlab.core.data.actuator.Actuator;
import de.qaware.smartlab.core.data.actuator.ActuatorDto;
import de.qaware.smartlab.core.data.actuator.IActuator;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ActuatorConverter implements IDtoConverter<IActuator, ActuatorDto> {

    /*
     * TODO: Map fields automatically without breaking the immutability of the entity classes
     * Maybe http://modelmapper.org/ is appropriate for this task
     */

    @Override
    public ActuatorDto toDto(IActuator actuator) {
        return ActuatorDto.builder()
                .id(actuator.getId())
                .type(actuator.getType())
                .name(actuator.getName())
                .responsibleDelegate(actuator.getResponsibleDelegate())
                .build();
    }

    @Override
    public IActuator toEntity(ActuatorDto actuator) {
        return Actuator.of(
                actuator.getId(),
                actuator.getType(),
                actuator.getName(),
                actuator.getResponsibleDelegate());
    }
}
