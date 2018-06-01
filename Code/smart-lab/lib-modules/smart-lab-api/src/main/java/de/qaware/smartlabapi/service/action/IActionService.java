package de.qaware.smartlabapi.service.action;

import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;

public interface IActionService {

    IActionResult executeAction(String actionId, IActionArgs actionArgs);
}
