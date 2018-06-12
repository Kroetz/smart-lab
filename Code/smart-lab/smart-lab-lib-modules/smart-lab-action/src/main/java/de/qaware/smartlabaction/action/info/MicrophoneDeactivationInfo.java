package de.qaware.smartlabaction.action.info;

import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
