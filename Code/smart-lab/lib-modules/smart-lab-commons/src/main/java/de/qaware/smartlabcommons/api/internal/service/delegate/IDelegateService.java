package de.qaware.smartlabcommons.api.internal.service.delegate;

import de.qaware.smartlabaction.action.IActionArgs;
import de.qaware.smartlabaction.action.result.IActionResult;

public interface IDelegateService {

    IActionResult executeAction(String delegateId, String actionId, String deviceType, IActionArgs actionArgs);
}
