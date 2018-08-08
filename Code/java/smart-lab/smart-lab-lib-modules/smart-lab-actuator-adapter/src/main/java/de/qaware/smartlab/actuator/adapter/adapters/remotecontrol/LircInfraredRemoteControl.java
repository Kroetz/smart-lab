package de.qaware.smartlab.actuator.adapter.adapters.remotecontrol;

import de.qaware.smartlab.core.exception.LocalActuatorException;
import lombok.extern.slf4j.Slf4j;

import static java.lang.String.format;

/**
 * Infrared remote control implementation that uses the Linux package LIRC.
 */
@Slf4j
public class LircInfraredRemoteControl extends AbstractInfraredRemoteControl {

    private static final String IRSEND = "irsend";
    private static final String SEND_ONCE = "SEND_ONCE";
    private static final String KEY_POWER = "KEY_POWER";
    private static final String KEY_ON = KEY_POWER;
    private static final String KEY_OFF = KEY_POWER;
    private static final String KEY_OK = "KEY_OK";
    private static final String KEY_UP = "KEY_UP";
    private static final String KEY_RIGHT = "KEY_RIGHT";
    private static final String KEY_DOWN = "KEY_DOWN";
    private static final String KEY_LEFT = "KEY_LEFT";

    private LircInfraredRemoteControl() { }

    public static LircInfraredRemoteControl newInstance() {
        return new LircInfraredRemoteControl();
    }

    private void executeLircCommand(String key, String actuatorType) {
        log.info(format("Executing LIRC command \"%s %s %s %s\"", IRSEND, SEND_ONCE, actuatorType, key));
        ProcessBuilder processBuilder = new ProcessBuilder();
        try {
            processBuilder.command(IRSEND, SEND_ONCE, actuatorType, key).start().waitFor();
        }
        catch(Exception e) {
            String errorMessage = "Could not execute LIRC command";
            log.error(errorMessage, e);
            throw new LocalActuatorException(errorMessage, e);
        }
    }

    @Override
    public void on(String actuatorType) {
        super.on(actuatorType);
        executeLircCommand(KEY_ON, actuatorType);
    }

    @Override
    public void off(String actuatorType) {
        super.off(actuatorType);
        executeLircCommand(KEY_OFF, actuatorType);
    }

    @Override
    public void ok(String actuatorType) {
        super.ok(actuatorType);
        executeLircCommand(KEY_OK, actuatorType);
    }

    @Override
    public void up(String actuatorType) {
        super.up(actuatorType);
        executeLircCommand(KEY_UP, actuatorType);
    }

    @Override
    public void right(String actuatorType) {
        super.right(actuatorType);
        executeLircCommand(KEY_RIGHT, actuatorType);
    }

    @Override
    public void down(String actuatorType) {
        super.down(actuatorType);
        executeLircCommand(KEY_DOWN, actuatorType);
    }

    @Override
    public void left(String actuatorType) {
        super.left(actuatorType);
        executeLircCommand(KEY_LEFT, actuatorType);
    }
}
