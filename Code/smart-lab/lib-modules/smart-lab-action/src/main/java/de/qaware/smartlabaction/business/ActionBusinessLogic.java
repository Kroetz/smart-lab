package de.qaware.smartlabaction.business;

import de.qaware.smartlabcommons.api.service.delegate.IDelegateService;
import de.qaware.smartlabcommons.data.action.IAction;
import de.qaware.smartlabcommons.data.action.IActionArgs;
import de.qaware.smartlabcommons.data.action.IActionDispatching;
import de.qaware.smartlabcommons.data.action.result.IActionResult;
import de.qaware.smartlabcommons.data.generic.IResolver;
import de.qaware.smartlabcommons.exception.UnknownActionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ActionBusinessLogic implements IActionBusinessLogic {

    private final IDelegateService delegateService;
    private final IResolver<String, IAction> actionResolver;

    public ActionBusinessLogic(
            IDelegateService delegateService,
            IResolver<String, IAction> actionResolver) {
        this.delegateService = delegateService;
        this.actionResolver = actionResolver;
    }

    @Override
    public IActionResult executeAction(String actionId, IActionArgs actionArgs) {
        log.info("Executing action (ID: \"{}\")", actionId);
        IAction action = this.actionResolver.resolve(actionId).orElseThrow(UnknownActionException::new);
        IActionDispatching actionDispatching = action.dispatching(actionArgs, this.delegateService);
        IActionResult actionResult = actionDispatching.dispatch();
        log.info("Executed action (ID: \"{}\")", actionId);
        return actionResult;
    }
}
