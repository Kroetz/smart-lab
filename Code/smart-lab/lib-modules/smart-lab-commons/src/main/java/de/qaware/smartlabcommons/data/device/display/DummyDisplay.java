package de.qaware.smartlabcommons.data.device.display;

import de.qaware.smartlabcommons.api.service.delegate.IDelegateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DummyDisplay extends AbstractDisplayAdapter {

    public static final String DEVICE_TYPE = "dummy display";
    private final IDelegateService delegateService;

    public DummyDisplay(IDelegateService delegateService) {
        super(DEVICE_TYPE);
        this.delegateService = delegateService;
    }
}
