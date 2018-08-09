package de.qaware.smartlab.action.actions.callable.generic;

import de.qaware.smartlab.action.actions.info.generic.IActionInfo;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;

public interface IActionCallable<ActionArgsT extends IActionArgs, ReturnT> extends IActionInfo {

    ReturnT call(IActionService actionService, ActionArgsT actionArgs);
}
