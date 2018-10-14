package de.qaware.smartlab.api.service.connector.delegate;

import de.qaware.smartlab.core.action.generic.IActionArgs;
import de.qaware.smartlab.core.action.generic.IActionResult;

public interface IDelegateService {

    IActionResult executeAction(String delegateId, String actionId, String actuatorType, IActionArgs actionArgs);
}
