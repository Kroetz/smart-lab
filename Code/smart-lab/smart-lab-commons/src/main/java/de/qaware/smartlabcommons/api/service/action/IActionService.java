package de.qaware.smartlabcommons.api.service.action;

import de.qaware.smartlabcommons.data.action.IActionArgs;

public interface IActionService {

    void executeAction(String actionId, IActionArgs actionArgs);
}
