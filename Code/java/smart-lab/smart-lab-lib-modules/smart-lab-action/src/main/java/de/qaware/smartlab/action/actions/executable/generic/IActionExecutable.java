package de.qaware.smartlab.action.actions.executable.generic;

import de.qaware.smartlab.action.actions.info.generic.IActionInfo;
import de.qaware.smartlab.api.service.connector.delegate.IDelegateService;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.exception.action.ActionException;

public interface IActionExecutable extends IActionInfo {

    IActionResult executeLocally(String actuatorType, IActionArgs genericActionArgs) throws ActionException;
    IActionResult executeRemotely(IActionArgs actionArgs, IDelegateService delegateService) throws ActionException;
}
