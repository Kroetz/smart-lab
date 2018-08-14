package de.qaware.smartlab.actuator.adapter.adapters.miscellaneous.remotecontrol;

import de.qaware.smartlab.core.exception.actuator.ActuatorException;

public interface IInfraredRemoteControl {

    void power(String actuatorType) throws ActuatorException;
    void standby(String actuatorType) throws ActuatorException;
    void ok(String actuatorType) throws ActuatorException;
    void up(String actuatorType) throws ActuatorException;
    void right(String actuatorType) throws ActuatorException;
    void down(String actuatorType) throws ActuatorException;
    void left(String actuatorType) throws ActuatorException;
}
