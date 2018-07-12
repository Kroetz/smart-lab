package de.qaware.smartlabdelegate.service.business;

import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;

public interface IDelegateBusinessLogic {

    IActionResult executeAction(String actionId, String deviceType, IActionArgs actionArgs);
}
