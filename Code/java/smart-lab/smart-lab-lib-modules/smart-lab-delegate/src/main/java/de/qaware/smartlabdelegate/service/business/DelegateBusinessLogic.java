package de.qaware.smartlabdelegate.service.business;

import de.qaware.smartlab.action.actions.executable.generic.IActionExecutable;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.data.generic.IResolver;
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
        IActionExecutable action = this.actionResolver.resolve(actionId);
        IActionResult actionResult = action.execute(deviceType, actionArgs);
        log.info("Executed action (ID: \"{}\") with device \"{}\"", actionId, deviceType);
        return actionResult;
    }
}
