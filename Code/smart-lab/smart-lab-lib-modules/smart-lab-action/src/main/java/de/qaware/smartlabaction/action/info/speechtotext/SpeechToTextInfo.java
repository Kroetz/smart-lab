package de.qaware.smartlabaction.action.info.speechtotext;

import de.qaware.smartlabaction.action.info.generic.AbstractActionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpeechToTextInfo extends AbstractActionInfo {

    public static final String ACTION_ID = "speech to text";

    public SpeechToTextInfo() {
        super(ACTION_ID);
    }
}
