package de.qaware.smartlabaction.action.info.microphone.activation;

import de.qaware.smartlabaction.action.info.generic.AbstractActionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MicrophoneActivationInfo extends AbstractActionInfo {

    public static final String ACTION_ID = "microphone activation";

    public MicrophoneActivationInfo() {
        super(ACTION_ID);
    }
}
