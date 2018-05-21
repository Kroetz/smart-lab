package de.qaware.smartlabcommons.data.action.web;

import de.qaware.smartlabcommons.api.internal.service.delegate.IDelegateService;
import de.qaware.smartlabcommons.data.action.AbstractAction;
import de.qaware.smartlabcommons.data.action.IActionArgs;
import de.qaware.smartlabcommons.data.action.IActionDispatching;
import de.qaware.smartlabcommons.data.action.result.VoidActionResult;
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
    public IActionDispatching dispatching(String deviceType, IActionArgs genericActionArgs) {
        // TODO: Implementation
        return () -> VoidActionResult.instance();
    }

    @Override
    public IActionDispatching dispatching(IActionArgs actionArgs, IDelegateService delegateService) {
        // TODO: Implementation
        return () -> VoidActionResult.instance();
    }

    // TODO: ActionArgs definition
}
