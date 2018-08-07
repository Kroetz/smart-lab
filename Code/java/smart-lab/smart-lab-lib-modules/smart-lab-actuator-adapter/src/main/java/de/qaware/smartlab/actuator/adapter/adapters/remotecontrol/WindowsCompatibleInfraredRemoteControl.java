package de.qaware.smartlab.actuator.adapter.adapters.remotecontrol;

import lombok.extern.slf4j.Slf4j;

/**
 * Dummy implementation of an infrared remote control on Microsoft Windows operating systems.
 */
@Slf4j
public class WindowsCompatibleInfraredRemoteControl extends AbstractInfraredRemoteControl {

    private void executeCommand(String deviceType) {
        // TODO: Implement infrared remote control for Microsoft Windows operating systems
        log.warn("Infrared remote control commands are not yet implemented for Microsoft Windows");
    }

    @Override
    public void on(String deviceType) {
        super.on(deviceType);
        executeCommand(deviceType);
    }

    @Override
    public void off(String deviceType) {
        super.off(deviceType);
        executeCommand(deviceType);
    }

    @Override
    public void ok(String deviceType) {
        super.ok(deviceType);
        executeCommand(deviceType);
    }

    @Override
    public void up(String deviceType) {
        super.up(deviceType);
        executeCommand(deviceType);
    }

    @Override
    public void right(String deviceType) {
        super.right(deviceType);
        executeCommand(deviceType);
    }

    @Override
    public void down(String deviceType) {
        super.down(deviceType);
        executeCommand(deviceType);
    }

    @Override
    public void left(String deviceType) {
        super.left(deviceType);
        executeCommand(deviceType);
    }
}
