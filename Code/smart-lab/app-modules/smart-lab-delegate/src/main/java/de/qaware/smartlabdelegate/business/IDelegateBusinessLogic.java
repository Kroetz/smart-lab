package de.qaware.smartlabdelegate.business;

import de.qaware.smartlabcommons.data.action.IActionArgs;

public interface IDelegateBusinessLogic {

    void executeAction(String actionId, IActionArgs actionArgs);
}
