package de.qaware.smartlab.actuator.adapter.adapters.remotecontrol;

public interface IInfraredRemoteControl {

    void on(String actuatorType);
    void off(String actuatorType);
    void ok(String actuatorType);
    void up(String actuatorType);
    void right(String actuatorType);
    void down(String actuatorType);
    void left(String actuatorType);
}
