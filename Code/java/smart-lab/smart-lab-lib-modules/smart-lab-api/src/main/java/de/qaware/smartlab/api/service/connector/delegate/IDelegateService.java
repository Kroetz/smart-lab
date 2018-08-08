package de.qaware.smartlab.api.service.connector.delegate;

import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;

public interface IDelegateService {

    IActionResult executeAction(String delegateId, String actionId, String actuatorType, IActionArgs actionArgs);
}
