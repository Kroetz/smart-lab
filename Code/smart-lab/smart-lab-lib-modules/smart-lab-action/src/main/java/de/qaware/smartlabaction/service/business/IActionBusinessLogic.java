package de.qaware.smartlabaction.service.business;

import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;

public interface IActionBusinessLogic {

    IActionResult executeAction(String actionId, IActionArgs actionArgs);
}
