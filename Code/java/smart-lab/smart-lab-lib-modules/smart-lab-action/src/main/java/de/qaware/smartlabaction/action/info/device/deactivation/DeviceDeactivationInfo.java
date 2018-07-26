package de.qaware.smartlabaction.action.info.device.deactivation;

import de.qaware.smartlabaction.action.info.generic.AbstractActionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeviceDeactivationInfo extends AbstractActionInfo {

    public static final String ACTION_ID = "device deactivation";

    public DeviceDeactivationInfo() {
        super(ACTION_ID);
    }
}
