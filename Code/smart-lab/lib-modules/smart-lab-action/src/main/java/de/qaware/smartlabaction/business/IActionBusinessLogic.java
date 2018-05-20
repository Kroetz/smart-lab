package de.qaware.smartlabaction.business;

import de.qaware.smartlabcommons.data.action.IActionArgs;
import de.qaware.smartlabcommons.data.action.result.IActionResult;

public interface IActionBusinessLogic {

    IActionResult executeAction(String actionId, IActionArgs actionArgs);
}
