package de.qaware.smartlabaction.action.generic;

import de.qaware.smartlabcommons.data.action.generic.IActionExecutable;
import de.qaware.smartlabcommons.data.action.generic.IActionArgs;
import de.qaware.smartlabcommons.data.action.generic.IActionSubmittable;
import de.qaware.smartlabcommons.exception.UnmatchingActionArgsTypeException;

public abstract class AbstractAction<ActionArgsT extends IActionArgs, ReturnT> implements IActionSubmittable<ActionArgsT, ReturnT>, IActionExecutable {

    protected final String actionId;

    public AbstractAction(String actionId) {
        this.actionId = actionId;
    }

    @Override
    public String getActionId() {
        return this.actionId;
    }

    // TODO: better method name
    protected <U extends IActionArgs> U convertToSpecific(
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
