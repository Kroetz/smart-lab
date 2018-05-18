package de.qaware.smartlabdelegate.business;

import de.qaware.smartlabcommons.data.action.IAction;
import de.qaware.smartlabcommons.data.action.IActionArgs;
import de.qaware.smartlabcommons.data.action.IActionDispatching;
import de.qaware.smartlabcommons.data.action.IActionResult;
import de.qaware.smartlabcommons.data.generic.IResolver;
import de.qaware.smartlabcommons.exception.UnknownActionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DelegateBusinessLogic implements IDelegateBusinessLogic {

    private final IResolver<String, IAction> actionResolver;

    public DelegateBusinessLogic(IResolver<String, IAction> actionResolver) {
        this.actionResolver = actionResolver;
    }

    @Override
    public IActionResult executeAction(String actionId, String deviceType, IActionArgs actionArgs) {
        log.info("Executing action (ID: \"{}\") with device \"{}\"", actionId, deviceType);
        IAction action = this.actionResolver.resolve(actionId).orElseThrow(UnknownActionException::new);
        IActionDispatching actionDispatching = action.dispatching(deviceType, actionArgs);
        IActionResult actionResult = actionDispatching.dispatch();
        log.info("Executed action (ID: \"{}\") with device \"{}\"", actionId, deviceType);
        return actionResult;
    }
}
