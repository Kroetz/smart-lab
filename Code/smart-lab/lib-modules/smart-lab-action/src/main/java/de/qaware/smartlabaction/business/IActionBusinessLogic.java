package de.qaware.smartlabaction.business;

import de.qaware.smartlabaction.action.generic.IActionArgs;
import de.qaware.smartlabaction.action.generic.result.IActionResult;

public interface IActionBusinessLogic {

    IActionResult executeAction(String actionId, IActionArgs actionArgs);
}
