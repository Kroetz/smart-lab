package de.qaware.smartlabcommons.api.internal.service.delegate;

import de.qaware.smartlabcommons.data.action.generic.IActionArgs;
import de.qaware.smartlabcommons.data.action.generic.result.IActionResult;

public interface IDelegateService {

    IActionResult executeAction(String delegateId, String actionId, String deviceType, IActionArgs actionArgs);
}
