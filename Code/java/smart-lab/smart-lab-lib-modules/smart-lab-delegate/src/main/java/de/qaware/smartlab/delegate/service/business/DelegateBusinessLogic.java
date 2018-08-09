package de.qaware.smartlab.delegate.service.business;

import de.qaware.smartlab.action.actions.executable.generic.IActionExecutable;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.generic.IResolver;
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
    public IActionResult executeAction(String actionId, String actuatorType, IActionArgs actionArgs) {
        log.info("Executing action (ID: \"{}\") with actuator \"{}\"", actionId, actuatorType);
        IActionExecutable action = this.actionResolver.resolve(actionId);
        IActionResult actionResult = action.executeLocally(actuatorType, actionArgs);
        log.info("Executed action (ID: \"{}\") with actuator \"{}\"", actionId, actuatorType);
        return actionResult;
    }
}
