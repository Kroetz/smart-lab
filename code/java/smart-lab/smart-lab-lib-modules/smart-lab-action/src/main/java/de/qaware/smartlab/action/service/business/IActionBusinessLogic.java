package de.qaware.smartlab.action.service.business;

import de.qaware.smartlab.core.action.generic.IActionArgs;
import de.qaware.smartlab.core.action.generic.IActionResult;

public interface IActionBusinessLogic {

    IActionResult executeAction(String actionId, IActionArgs actionArgs);
}
