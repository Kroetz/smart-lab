package de.qaware.smartlab.api.service.connector.action;

import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;

public interface IActionService {

    IActionResult executeAction(String actionId, IActionArgs actionArgs);
}