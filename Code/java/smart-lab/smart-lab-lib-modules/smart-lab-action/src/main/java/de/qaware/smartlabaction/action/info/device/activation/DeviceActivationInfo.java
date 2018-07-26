package de.qaware.smartlabaction.action.info.device.activation;

import de.qaware.smartlabaction.action.info.generic.AbstractActionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeviceActivationInfo extends AbstractActionInfo {

    public static final String ACTION_ID = "device activation";

    public DeviceActivationInfo() {
        super(ACTION_ID);
    }
}
