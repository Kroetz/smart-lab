package de.qaware.smartlab.actuator.adapter.adapters.remotecontrol;

import lombok.extern.slf4j.Slf4j;

import static java.lang.String.format;

@Slf4j
public abstract class AbstractInfraredRemoteControl implements IInfraredRemoteControl {

    @Override
    public void on(String deviceType) {
        log.info(format("Switching on device \"%s\"", deviceType));
    }

    @Override
    public void off(String deviceType) {
        log.info(format("Switching off device \"%s\"", deviceType));
    }

    @Override
    public void ok(String deviceType) {
        log.info(format("Confirming on device \"%s\"", deviceType));
    }

    @Override
    public void up(String deviceType) {
        log.info(format("Navigating up on device \"%s\"", deviceType));
    }

    @Override
    public void right(String deviceType) {
        log.info(format("Navigating right on device \"%s\"", deviceType));
    }

    @Override
    public void down(String deviceType) {
        log.info(format("Navigating down on device \"%s\"", deviceType));
    }

    @Override
    public void left(String deviceType) {
        log.info(format("Navigating left on device \"%s\"", deviceType));
    }
}
