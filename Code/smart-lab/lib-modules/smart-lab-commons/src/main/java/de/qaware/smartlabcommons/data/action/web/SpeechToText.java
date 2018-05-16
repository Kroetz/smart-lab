package de.qaware.smartlabcommons.data.action.web;

import de.qaware.smartlabcommons.data.action.AbstractAction;
import de.qaware.smartlabcommons.data.action.IActionArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpeechToText extends AbstractAction {

    public static final String ACTION_ID = "speech to text";

    public SpeechToText() {
        super(ACTION_ID);
    }

    @Override
    public void executeAction(IActionArgs actionArgs) {
        // TODO: Implementation
    }

    // TODO: ActionArgs definition
}
