package de.qaware.smartlabdelegate.business;

import de.qaware.smartlabaction.action.IActionArgs;
import de.qaware.smartlabaction.action.result.IActionResult;

public interface IDelegateBusinessLogic {

    IActionResult executeAction(String actionId, String deviceType, IActionArgs actionArgs);
}
