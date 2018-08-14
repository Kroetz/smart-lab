package de.qaware.smartlab.core.data.actuator;

import de.qaware.smartlab.core.data.generic.IEntity;

public interface IActuator extends IEntity<ActuatorId> {

    String getType();
    String getName();
    String getResponsibleDelegate();
}
