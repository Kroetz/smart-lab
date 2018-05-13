package de.qaware.smartlabcommons.data.device.microphone;

import de.qaware.smartlabcommons.api.service.delegate.IDelegateService;
import de.qaware.smartlabcommons.data.device.entity.IDevice;
import de.qaware.smartlabcommons.data.room.IRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DummyMicrophone extends AbstractMicrophoneAdapter {

    public static final String DEVICE_TYPE = "dummy microphone";
    private final IDelegateService delegateService;

    public DummyMicrophone(IDelegateService delegateService) {
        super(DEVICE_TYPE);
        this.delegateService = delegateService;
    }

    @Override
    public void activate(IRoom room, IDevice device, boolean executeLocally) {
        log.info("Dummy microphone (ID: {}) in room with ID {} activated", device.getId(), room.getId());
    }

    @Override
    public void deactivate(IRoom room, IDevice device, boolean executeLocally) {
        log.info("Dummy microphone (ID: {}) in room with ID {} deactivated", device.getId(), room.getId());
    }
}
