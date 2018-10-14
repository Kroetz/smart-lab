package de.qaware.smartlab.delegate.service.business;

import de.qaware.smartlab.core.action.generic.IActionArgs;
import de.qaware.smartlab.core.action.generic.IActionResult;

public interface IDelegateBusinessLogic {

    IActionResult executeAction(String actionId, String actuatorType, IActionArgs actionArgs);
}
