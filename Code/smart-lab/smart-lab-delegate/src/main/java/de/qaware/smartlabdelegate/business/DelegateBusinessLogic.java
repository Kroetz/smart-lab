package de.qaware.smartlabdelegate.business;

import de.qaware.smartlabcommons.data.action.IAction;
import de.qaware.smartlabcommons.data.action.IActionArgs;
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
    public void executeAction(String actionId, IActionArgs actionArgs) {
        log.info("Executing action (ID: \"{}\")", actionId);
        IAction action = this.actionResolver.resolve(actionId).orElseThrow(UnknownActionException::new);
        action.executeAction(actionArgs);
        log.info("Executed action (ID: \"{}\")", actionId);
    }
}
