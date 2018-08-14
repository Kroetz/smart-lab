package de.qaware.smartlab.action.actions.info.audio.recordingstop;

import de.qaware.smartlab.action.actions.info.generic.AbstractActionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AudioRecordingStopInfo extends AbstractActionInfo {

    public static final String ACTION_ID = "audio recording stop";

    public AudioRecordingStopInfo() {
        super(ACTION_ID);
    }
}
