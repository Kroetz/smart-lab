package de.qaware.smartlabcommons.data.device.microphone;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Component
@Slf4j
public class ThinkpadP50InternalMicrophoneAdapter extends AbstractMicrophoneAdapter {

    public static final String DEVICE_TYPE = "thinkpad p50 internal microphone";
    private static final boolean HAS_LOCAL_API = true;

    public ThinkpadP50InternalMicrophoneAdapter() {
        super(DEVICE_TYPE, HAS_LOCAL_API);
    }

    @Override
    public void activate() {
        // TODO: only local functionality
    }

    @Override
    public void deactivate() {
        throw new NotImplementedException();
    }
}
