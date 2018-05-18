package de.qaware.smartlabcommons.data.action;

import de.qaware.smartlabcommons.api.service.delegate.IDelegateService;

public interface IAction {

    String getActionId();
    IActionDispatching dispatching(String deviceType, IActionArgs genericActionArgs);
    IActionDispatching dispatching(IActionArgs actionArgs, IDelegateService delegateService);
}
