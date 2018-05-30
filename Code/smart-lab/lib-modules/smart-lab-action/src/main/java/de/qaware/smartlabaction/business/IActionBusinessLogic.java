package de.qaware.smartlabaction.business;

import de.qaware.smartlabcommons.data.action.generic.IActionArgs;
import de.qaware.smartlabcommons.data.action.generic.result.IActionResult;

public interface IActionBusinessLogic {

    IActionResult executeAction(String actionId, IActionArgs actionArgs);
}
