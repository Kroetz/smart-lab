package de.qaware.smartlabaction.action.generic;

import de.qaware.smartlabapi.service.delegate.IDelegateService;
import de.qaware.smartlabcore.data.action.generic.IAction;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;

public interface IActionExecutable extends IAction {

    IActionResult execute(String deviceType, IActionArgs genericActionArgs);
    IActionResult execute(IActionArgs actionArgs, IDelegateService delegateService);
}
