package de.qaware.smartlabaction.service.business;

import de.qaware.smartlabapi.service.connector.delegate.IDelegateService;
import de.qaware.smartlabaction.action.executable.generic.IActionExecutable;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.data.generic.IResolver;
import de.qaware.smartlabcore.exception.UnknownActionException;
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
        IActionExecutable action = this.actionResolver.resolve(actionId).orElseThrow(UnknownActionException::new);
        IActionResult actionResult = action.execute(actionArgs, this.delegateService);
        log.info("Executed action (ID: \"{}\")", actionId);
        return actionResult;
    }
}
