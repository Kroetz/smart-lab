package de.qaware.smartlab.action.submittable.generic;

import de.qaware.smartlab.action.info.generic.IActionInfo;
import de.qaware.smartlabapi.service.connector.action.IActionService;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;

public interface IActionSubmittable<ActionArgsT extends IActionArgs, ReturnT> extends IActionInfo {

    ReturnT submitExecution(IActionService actionService, ActionArgsT actionArgs);
}
