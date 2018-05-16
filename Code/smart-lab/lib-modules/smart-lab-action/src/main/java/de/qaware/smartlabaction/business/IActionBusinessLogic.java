package de.qaware.smartlabaction.business;

import de.qaware.smartlabcommons.data.action.IActionArgs;

public interface IActionBusinessLogic {

    void executeAction(String actionId, IActionArgs actionArgs);
}
