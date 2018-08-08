package de.qaware.smartlab.actuator.adapter.adapters.remotecontrol;

import lombok.extern.slf4j.Slf4j;

import static java.lang.String.format;

@Slf4j
public abstract class AbstractInfraredRemoteControl implements IInfraredRemoteControl {

    @Override
    public void on(String actuatorType) {
        log.info(format("Switching on actuator of type \"%s\"", actuatorType));
    }

    @Override
    public void off(String actuatorType) {
        log.info(format("Switching off actuator of type \"%s\"", actuatorType));
    }

    @Override
    public void ok(String actuatorType) {
        log.info(format("Confirming on actuator of type \"%s\"", actuatorType));
    }

    @Override
    public void up(String actuatorType) {
        log.info(format("Navigating up on actuator of type \"%s\"", actuatorType));
    }

    @Override
    public void right(String actuatorType) {
        log.info(format("Navigating right on actuator of type \"%s\"", actuatorType));
    }

    @Override
    public void down(String actuatorType) {
        log.info(format("Navigating down on actuator of type \"%s\"", actuatorType));
    }

    @Override
    public void left(String actuatorType) {
        log.info(format("Navigating left on actuator of type \"%s\"", actuatorType));
    }
}
