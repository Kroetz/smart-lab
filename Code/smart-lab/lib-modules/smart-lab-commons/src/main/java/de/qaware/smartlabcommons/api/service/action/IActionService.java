package de.qaware.smartlabcommons.api.service.action;

import de.qaware.smartlabcommons.data.action.IActionArgs;
import de.qaware.smartlabcommons.data.action.result.IActionResult;

public interface IActionService {

    IActionResult executeAction(String actionId, IActionArgs actionArgs);
}
