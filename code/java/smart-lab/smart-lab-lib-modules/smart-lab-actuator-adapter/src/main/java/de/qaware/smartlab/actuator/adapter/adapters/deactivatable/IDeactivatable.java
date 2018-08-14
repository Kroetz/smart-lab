package de.qaware.smartlab.actuator.adapter.adapters.deactivatable;

import de.qaware.smartlab.core.data.actuator.IActuatorAdapter;
import de.qaware.smartlab.core.exception.actuator.ActuatorException;

public interface IDeactivatable extends IActuatorAdapter {

    void deactivate() throws ActuatorException;
}
