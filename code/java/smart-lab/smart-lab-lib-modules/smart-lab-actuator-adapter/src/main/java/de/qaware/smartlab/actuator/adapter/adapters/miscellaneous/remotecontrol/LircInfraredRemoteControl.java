package de.qaware.smartlab.actuator.adapter.adapters.miscellaneous.remotecontrol;

import de.qaware.smartlab.core.exception.actuator.ActuatorException;
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
    private static final String KEY_POWER2 = "KEY_POWER2";
    private static final String KEY_STANDBY = KEY_POWER2;
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
            log.error(errorMessage);
            throw new ActuatorException(errorMessage, e);
        }
    }

    @Override
    public void power(String actuatorType) throws ActuatorException {
        super.power(actuatorType);
        executeLircCommand(KEY_POWER, actuatorType);
    }

    @Override
    public void standby(String actuatorType) throws ActuatorException {
        super.standby(actuatorType);
        executeLircCommand(KEY_STANDBY, actuatorType);
    }

    @Override
    public void ok(String actuatorType) throws ActuatorException {
        super.ok(actuatorType);
        executeLircCommand(KEY_OK, actuatorType);
    }

    @Override
    public void up(String actuatorType) throws ActuatorException {
        super.up(actuatorType);
        executeLircCommand(KEY_UP, actuatorType);
    }

    @Override
    public void right(String actuatorType) throws ActuatorException {
        super.right(actuatorType);
        executeLircCommand(KEY_RIGHT, actuatorType);
    }

    @Override
    public void down(String actuatorType) throws ActuatorException {
        super.down(actuatorType);
        executeLircCommand(KEY_DOWN, actuatorType);
    }

    @Override
    public void left(String actuatorType) throws ActuatorException {
        super.left(actuatorType);
        executeLircCommand(KEY_LEFT, actuatorType);
    }
}
