package de.qaware.smartlab.action.actions.info.audio.recordingstart;

import de.qaware.smartlab.action.actions.info.generic.AbstractActionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AudioRecordingStartInfo extends AbstractActionInfo {

    public static final String ACTION_ID = "audio recording start";

    public AudioRecordingStartInfo() {
        super(ACTION_ID);
    }
}
