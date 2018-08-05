package de.qaware.smartlabactuatoradapter.actuator.display;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DummyDisplayAdapter extends AbstractDisplayAdapter {

    public static final String DEVICE_TYPE = "dummy display";
    private static final boolean HAS_LOCAL_API = true;

    public DummyDisplayAdapter() {
        super(DEVICE_TYPE, HAS_LOCAL_API);
    }

    @Override
    public void activate() {
        log.info("Dummy display activated");
    }

    @Override
    public void deactivate() {
        log.info("Dummy display deactivated");
    }
}
