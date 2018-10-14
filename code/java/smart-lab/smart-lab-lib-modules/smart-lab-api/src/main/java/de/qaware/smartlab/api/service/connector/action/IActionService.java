package de.qaware.smartlab.api.service.connector.action;

import de.qaware.smartlab.core.action.generic.IActionArgs;
import de.qaware.smartlab.core.action.generic.IActionResult;

public interface IActionService {

    IActionResult executeAction(String actionId, IActionArgs actionArgs);
}
