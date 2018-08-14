package de.qaware.smartlab.action.actions.callable.generic;

import de.qaware.smartlab.action.actions.info.generic.IActionInfo;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.exception.action.ActionException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractActionCallable<ActionArgsT extends IActionArgs, ReturnT> implements IActionCallable<ActionArgsT, ReturnT> {

    protected final IActionInfo actionInfo;

    protected AbstractActionCallable(IActionInfo actionInfo) {
        this.actionInfo = actionInfo;
    }

    public String getActionId() {
        return this.actionInfo.getActionId();
    }

    protected <CastT> CastT toSpecificResultType(Class<CastT> targetResultClass, IActionResult genericActionResult) {
        try {
            return targetResultClass.cast(genericActionResult);
        }
        catch(ClassCastException e) {
            String errorMessage = String.format(
                    "The action result %s must be of the type %s",
                    genericActionResult,
                    targetResultClass.getName());
            log.error(errorMessage);
            throw new ActionException(errorMessage, e);
        }
    }
}
