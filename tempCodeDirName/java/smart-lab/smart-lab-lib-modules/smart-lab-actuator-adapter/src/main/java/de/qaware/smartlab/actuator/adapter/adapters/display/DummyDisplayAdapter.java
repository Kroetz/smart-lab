package de.qaware.smartlab.actuator.adapter.adapters.display;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DummyDisplayAdapter extends AbstractDisplayAdapter {

    public static final String ACTUATOR_TYPE = "dummy display";
    private static final boolean HAS_LOCAL_API = true;

    public DummyDisplayAdapter() {
        super(ACTUATOR_TYPE, HAS_LOCAL_API);
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
