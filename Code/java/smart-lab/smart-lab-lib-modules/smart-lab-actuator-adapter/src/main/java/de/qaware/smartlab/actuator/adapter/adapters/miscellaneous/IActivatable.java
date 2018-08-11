package de.qaware.smartlab.actuator.adapter.adapters.miscellaneous;

import de.qaware.smartlab.core.data.actuator.IActuatorAdapter;
import de.qaware.smartlab.core.exception.actuator.ActuatorException;

public interface IActivatable extends IActuatorAdapter {

    void activate() throws ActuatorException;
}
