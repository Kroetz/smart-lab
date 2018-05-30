package de.qaware.smartlabcommons.data.action.generic;

import de.qaware.smartlabcommons.api.internal.service.action.IActionService;

public interface IActionSubmittable<ActionArgsT extends IActionArgs, ReturnT> extends IAction {

    ReturnT submitCall(IActionService actionService, ActionArgsT actionArgs);
}
