package de.qaware.smartlab.core.data.actuator;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlab.core.data.generic.IEntity;
import de.qaware.smartlab.core.miscellaneous.Constants;

public interface IActuator extends IEntity<ActuatorId> {

    String getType();
    String getName();
    String getResponsibleDelegate();
}
