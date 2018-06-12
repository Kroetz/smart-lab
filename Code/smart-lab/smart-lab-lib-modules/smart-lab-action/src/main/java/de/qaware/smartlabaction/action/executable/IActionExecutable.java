package de.qaware.smartlabaction.action.executable;

import de.qaware.smartlabaction.action.info.IActionInfo;
import de.qaware.smartlabapi.service.delegate.IDelegateService;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;

public interface IActionExecutable extends IActionInfo {

    IActionResult execute(String deviceType, IActionArgs genericActionArgs);
    IActionResult execute(IActionArgs actionArgs, IDelegateService delegateService);
}
