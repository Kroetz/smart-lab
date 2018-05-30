package de.qaware.smartlabcommons.api.internal.service.action;

import de.qaware.smartlabcommons.data.action.generic.IActionArgs;
import de.qaware.smartlabcommons.data.action.generic.result.IActionResult;

public interface IActionService {

    IActionResult executeAction(String actionId, IActionArgs actionArgs);
}
