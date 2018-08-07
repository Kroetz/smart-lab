package de.qaware.smartlab.action.executable.generic;

import de.qaware.smartlab.action.info.generic.IActionInfo;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.exception.UnmatchingActionArgsTypeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractActionExecutable implements IActionExecutable {

    protected final IActionInfo actionInfo;

    public AbstractActionExecutable(IActionInfo actionInfo) {
        this.actionInfo = actionInfo;
    }

    public String getActionId() {
        return this.actionInfo.getActionId();
    }

    // TODO: better method name
    protected <U extends IActionArgs> U convertToSpecificActionArgs(
            Class<U> targetActionArgsClass,
            IActionArgs genericActionArgs) throws UnmatchingActionArgsTypeException {
        try {
            return targetActionArgsClass.cast(genericActionArgs);
        }
        catch(ClassCastException e) {
            throw new UnmatchingActionArgsTypeException(e);
        }
    }
}
