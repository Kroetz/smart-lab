package de.qaware.smartlabcore.data.device.microphone;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@Slf4j
public class DummyMicrophone extends AbstractMicrophoneAdapter {

    public static final String DEVICE_TYPE = "dummy microphone";
    private static final boolean HAS_LOCAL_API = true;

    public DummyMicrophone() {
        super(DEVICE_TYPE, HAS_LOCAL_API);
    }

    @Override
    public void activate(Path recordingTargetFile) {
        log.info("Dummy microphone activated");
    }

    @Override
    public Path deactivate() {
        log.info("Dummy microphone deactivated");
        return null;        // TODO: Do not return null but rather the path to a predefined audio sample
    }
}
