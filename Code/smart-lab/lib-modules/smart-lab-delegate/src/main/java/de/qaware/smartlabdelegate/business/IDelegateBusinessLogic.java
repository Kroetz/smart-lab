package de.qaware.smartlabdelegate.business;

import de.qaware.smartlabcommons.data.action.IActionArgs;
import de.qaware.smartlabcommons.data.action.IActionResult;

public interface IDelegateBusinessLogic {

    IActionResult executeAction(String actionId, String deviceType, IActionArgs actionArgs);
}
