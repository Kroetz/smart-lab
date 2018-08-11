package de.qaware.smartlab.actuator.adapter.adapters.remotecontrol;

import de.qaware.smartlab.core.exception.actuator.ActuatorException;

public interface IInfraredRemoteControl {

    void on(String actuatorType) throws ActuatorException;
    void off(String actuatorType) throws ActuatorException;
    void ok(String actuatorType) throws ActuatorException;
    void up(String actuatorType) throws ActuatorException;
    void right(String actuatorType) throws ActuatorException;
    void down(String actuatorType) throws ActuatorException;
    void left(String actuatorType) throws ActuatorException;
}
