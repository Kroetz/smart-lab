package de.qaware.smartlabapi.service.connector.delegate;

import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;

public interface IDelegateService {

    IActionResult executeAction(String delegateId, String actionId, String deviceType, IActionArgs actionArgs);
}
