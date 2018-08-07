package de.qaware.smartlab.action.actions.submittable.generic;

import de.qaware.smartlab.action.actions.info.generic.IActionInfo;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;

public interface IActionSubmittable<ActionArgsT extends IActionArgs, ReturnT> extends IActionInfo {

    ReturnT submitExecution(IActionService actionService, ActionArgsT actionArgs);
}
