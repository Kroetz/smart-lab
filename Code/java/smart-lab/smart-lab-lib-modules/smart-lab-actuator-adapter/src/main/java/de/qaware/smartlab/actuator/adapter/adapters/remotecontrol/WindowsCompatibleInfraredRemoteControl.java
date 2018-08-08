package de.qaware.smartlab.actuator.adapter.adapters.remotecontrol;

import lombok.extern.slf4j.Slf4j;

/**
 * Dummy implementation of an infrared remote control on Microsoft Windows operating systems.
 */
@Slf4j
public class WindowsCompatibleInfraredRemoteControl extends AbstractInfraredRemoteControl {

    private void executeCommand(String actuatorType) {
        // TODO: Implement infrared remote control for Microsoft Windows operating systems
        log.warn("Infrared remote control commands are not yet implemented for Microsoft Windows");
    }

    @Override
    public void on(String actuatorType) {
        super.on(actuatorType);
        executeCommand(actuatorType);
    }

    @Override
    public void off(String actuatorType) {
        super.off(actuatorType);
        executeCommand(actuatorType);
    }

    @Override
    public void ok(String actuatorType) {
        super.ok(actuatorType);
        executeCommand(actuatorType);
    }

    @Override
    public void up(String actuatorType) {
        super.up(actuatorType);
        executeCommand(actuatorType);
    }

    @Override
    public void right(String actuatorType) {
        super.right(actuatorType);
        executeCommand(actuatorType);
    }

    @Override
    public void down(String actuatorType) {
        super.down(actuatorType);
        executeCommand(actuatorType);
    }

    @Override
    public void left(String actuatorType) {
        super.left(actuatorType);
        executeCommand(actuatorType);
    }
}
