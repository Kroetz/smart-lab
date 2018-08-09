package de.qaware.smartlab.action.actions.callable.generic;

import de.qaware.smartlab.action.actions.info.generic.IActionInfo;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractActionCallable<ActionArgsT extends IActionArgs, ReturnT> implements IActionCallable<ActionArgsT, ReturnT> {

    protected final IActionInfo actionInfo;

    public AbstractActionCallable(IActionInfo actionInfo) {
        this.actionInfo = actionInfo;
    }

    public String getActionId() {
        return this.actionInfo.getActionId();
    }
}
