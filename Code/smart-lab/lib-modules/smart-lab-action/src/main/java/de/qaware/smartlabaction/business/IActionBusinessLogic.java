package de.qaware.smartlabaction.business;

import de.qaware.smartlabaction.action.IActionArgs;
import de.qaware.smartlabaction.action.result.IActionResult;

public interface IActionBusinessLogic {

    IActionResult executeAction(String actionId, IActionArgs actionArgs);
}
