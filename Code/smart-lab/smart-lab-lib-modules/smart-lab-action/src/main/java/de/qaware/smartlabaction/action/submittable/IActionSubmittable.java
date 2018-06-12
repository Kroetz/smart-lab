package de.qaware.smartlabaction.action.submittable;

import de.qaware.smartlabaction.action.info.IActionInfo;
import de.qaware.smartlabapi.service.action.IActionService;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;

public interface IActionSubmittable<ActionArgsT extends IActionArgs, ReturnT> extends IActionInfo {

    ReturnT submitExecution(IActionService actionService, ActionArgsT actionArgs);
}
