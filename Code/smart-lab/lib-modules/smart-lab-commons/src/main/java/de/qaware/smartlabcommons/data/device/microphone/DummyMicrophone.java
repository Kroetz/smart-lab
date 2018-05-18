package de.qaware.smartlabcommons.data.device.microphone;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DummyMicrophone extends AbstractMicrophoneAdapter {

    public static final String DEVICE_TYPE = "dummy microphone";
    private static final boolean HAS_LOCAL_API = true;

    public DummyMicrophone() {
        super(DEVICE_TYPE, HAS_LOCAL_API);
    }

    @Override
    public void activate() {
        log.info("Dummy microphone activated");
    }

    @Override
    public void deactivate() {
        log.info("Dummy microphone deactivated");
    }
}
