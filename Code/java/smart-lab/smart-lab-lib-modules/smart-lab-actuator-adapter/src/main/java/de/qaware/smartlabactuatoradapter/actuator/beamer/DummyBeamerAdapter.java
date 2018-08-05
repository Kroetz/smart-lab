package de.qaware.smartlabactuatoradapter.actuator.beamer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DummyBeamerAdapter extends AbstractBeamerAdapter {

    public static final String DEVICE_TYPE = "dummy beamer";
    private static final boolean HAS_LOCAL_API = true;

    public DummyBeamerAdapter() {
        super(DEVICE_TYPE, HAS_LOCAL_API);
    }

    @Override
    public void activate() {
        log.info("Dummy beamer activated");
    }

    @Override
    public void deactivate() {
        log.info("Dummy beamer deactivated");
    }
}
