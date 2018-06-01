package de.qaware.smartlabcore.data.device.display;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DummyDisplay extends AbstractDisplayAdapter {

    public static final String DEVICE_TYPE = "dummy display";
    private static final boolean HAS_LOCAL_API = true;

    public DummyDisplay() {
        super(DEVICE_TYPE, HAS_LOCAL_API);
    }
}
