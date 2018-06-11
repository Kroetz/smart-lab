package de.qaware.smartlabaction.action.generic;

import de.qaware.smartlabapi.service.action.IActionService;
import de.qaware.smartlabcore.data.action.generic.IAction;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;

public interface IActionSubmittable<ActionArgsT extends IActionArgs, ReturnT> extends IAction {

    ReturnT submitCall(IActionService actionService, ActionArgsT actionArgs);
}