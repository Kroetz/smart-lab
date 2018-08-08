package de.qaware.smartlab.action.actions.executable.generic;

import de.qaware.smartlab.action.actions.info.generic.IActionInfo;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
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

    protected <ArgsT extends IActionArgs> ArgsT toSpecificArgsType(
            Class<ArgsT> targetArgsClass,
            IActionArgs genericArgs) throws IllegalStateException {
        try {
            return targetArgsClass.cast(genericArgs);
        }
        catch(ClassCastException e) {
            String errorMessage = String.format(
                    "The action args %s must be of the type %s",
                    genericArgs.toString(),
                    targetArgsClass.getName());
            log.error(errorMessage, e);
            throw new IllegalStateException(errorMessage, e);
        }
    }
}
