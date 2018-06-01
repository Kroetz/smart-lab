package de.qaware.smartlabdelegate.business;

import de.qaware.smartlabaction.action.generic.IActionExecutable;
import de.qaware.smartlabcommons.data.action.generic.IActionArgs;
import de.qaware.smartlabcommons.data.action.generic.result.IActionResult;
import de.qaware.smartlabcommons.data.generic.IResolver;
import de.qaware.smartlabcommons.exception.UnknownActionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DelegateBusinessLogic implements IDelegateBusinessLogic {

    private final IResolver<String, IActionExecutable> actionResolver;

    public DelegateBusinessLogic(IResolver<String, IActionExecutable> actionResolver) {
        this.actionResolver = actionResolver;
    }

    @Override
    public IActionResult executeAction(String actionId, String deviceType, IActionArgs actionArgs) {
        log.info("Executing action (ID: \"{}\") with device \"{}\"", actionId, deviceType);
        IActionExecutable action = this.actionResolver.resolve(actionId).orElseThrow(UnknownActionException::new);
        IActionResult actionResult = action.execute(deviceType, actionArgs);
        log.info("Executed action (ID: \"{}\") with device \"{}\"", actionId, deviceType);
        return actionResult;
    }
}
