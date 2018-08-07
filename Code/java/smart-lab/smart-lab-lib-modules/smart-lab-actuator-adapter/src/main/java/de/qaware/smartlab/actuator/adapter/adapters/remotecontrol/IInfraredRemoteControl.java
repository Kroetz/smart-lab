package de.qaware.smartlab.actuator.adapter.adapters.remotecontrol;

public interface IInfraredRemoteControl {

    void on(String deviceType);
    void off(String deviceType);
    void ok(String deviceType);
    void up(String deviceType);
    void right(String deviceType);
    void down(String deviceType);
    void left(String deviceType);
}