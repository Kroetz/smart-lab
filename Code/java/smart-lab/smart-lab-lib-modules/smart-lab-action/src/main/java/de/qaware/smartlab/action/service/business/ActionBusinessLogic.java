package de.qaware.smartlab.action.service.business;

import de.qaware.smartlab.api.service.connector.delegate.IDelegateService;
import de.qaware.smartlab.action.actions.executable.generic.IActionExecutable;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.generic.IResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ActionBusinessLogic implements IActionBusinessLogic {

    private final IDelegateService delegateService;
    private final IResolver<String, IActionExecutable> actionResolver;

    public ActionBusinessLogic(
            IDelegateService delegateService,
            IResolver<String, IActionExecutable> actionResolver) {
        this.delegateService = delegateService;
        this.actionResolver = actionResolver;
    }

    @Override
    public IActionResult executeAction(String actionId, IActionArgs actionArgs) {
        log.info("Executing action (ID: \"{}\")", actionId);
        IActionExecutable action = this.actionResolver.resolve(actionId);
        IActionResult actionResult = action.executeRemotely(actionArgs, this.delegateService);
        log.info("Executed action (ID: \"{}\")", actionId);
        return actionResult;
    }
}
