package de.qaware.smartlabcommons.api.service.delegate;

import de.qaware.smartlabcommons.data.action.IActionArgs;

public interface IDelegateService {

    void executeAction(String delegateId, String actionId, IActionArgs actionArgs);
}
