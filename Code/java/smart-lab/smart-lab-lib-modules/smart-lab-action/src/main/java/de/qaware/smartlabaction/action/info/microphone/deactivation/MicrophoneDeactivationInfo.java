package de.qaware.smartlabaction.action.info.microphone.deactivation;

import de.qaware.smartlabaction.action.info.generic.AbstractActionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MicrophoneDeactivationInfo extends AbstractActionInfo {

    public static final String ACTION_ID = "microphone deactivation";

    public MicrophoneDeactivationInfo() {
        super(ACTION_ID);
    }
}
