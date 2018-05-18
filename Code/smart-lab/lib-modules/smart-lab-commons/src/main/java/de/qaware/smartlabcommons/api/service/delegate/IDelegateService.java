package de.qaware.smartlabcommons.api.service.delegate;

import de.qaware.smartlabcommons.data.action.IActionArgs;
import de.qaware.smartlabcommons.data.action.IActionResult;

public interface IDelegateService {

    IActionResult executeAction(String delegateId, String actionId, String deviceType, IActionArgs actionArgs);
}
