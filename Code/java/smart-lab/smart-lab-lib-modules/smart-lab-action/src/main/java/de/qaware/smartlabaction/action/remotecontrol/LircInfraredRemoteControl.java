package de.qaware.smartlabaction.action.remotecontrol;

import de.qaware.smartlabcore.exception.LocalDeviceException;
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

    private void executeLircCommand(String key, String deviceType) {
        log.info(format("Executing LIRC command \"%s %s %s %s\"", IRSEND, SEND_ONCE, deviceType, key));
        ProcessBuilder processBuilder = new ProcessBuilder();
        try {
            processBuilder.command(IRSEND, SEND_ONCE, deviceType, key).start().waitFor();
        }
        catch(Exception e) {
            String errorMessage = "Could not execute LIRC command";
            log.error(errorMessage, e);
            throw new LocalDeviceException(errorMessage, e);
        }
    }

    @Override
    public void on(String deviceType) {
        super.on(deviceType);
        executeLircCommand(KEY_ON, deviceType);
    }

    @Override
    public void off(String deviceType) {
        super.off(deviceType);
        executeLircCommand(KEY_OFF, deviceType);
    }

    @Override
    public void ok(String deviceType) {
        super.ok(deviceType);
        executeLircCommand(KEY_OK, deviceType);
    }

    @Override
    public void up(String deviceType) {
        super.up(deviceType);
        executeLircCommand(KEY_UP, deviceType);
    }

    @Override
    public void right(String deviceType) {
        super.right(deviceType);
        executeLircCommand(KEY_RIGHT, deviceType);
    }

    @Override
    public void down(String deviceType) {
        super.down(deviceType);
        executeLircCommand(KEY_DOWN, deviceType);
    }

    @Override
    public void left(String deviceType) {
        super.left(deviceType);
        executeLircCommand(KEY_LEFT, deviceType);
    }
}
