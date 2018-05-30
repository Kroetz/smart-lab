package de.qaware.smartlabcommons.data.action.generic;

import de.qaware.smartlabcommons.api.internal.service.delegate.IDelegateService;
import de.qaware.smartlabcommons.data.action.generic.result.IActionResult;

public interface IAction {

    String getActionId();
    IActionResult execute(String deviceType, IActionArgs genericActionArgs);
    IActionResult execute(IActionArgs actionArgs, IDelegateService delegateService);
}
