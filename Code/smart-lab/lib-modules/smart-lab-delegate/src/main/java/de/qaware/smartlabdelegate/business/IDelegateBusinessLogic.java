package de.qaware.smartlabdelegate.business;

import de.qaware.smartlabcommons.data.action.generic.IActionArgs;
import de.qaware.smartlabcommons.data.action.generic.result.IActionResult;

public interface IDelegateBusinessLogic {

    IActionResult executeAction(String actionId, String deviceType, IActionArgs actionArgs);
}
