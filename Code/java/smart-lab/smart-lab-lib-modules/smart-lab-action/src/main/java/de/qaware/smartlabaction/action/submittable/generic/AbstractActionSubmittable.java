package de.qaware.smartlabaction.action.submittable.generic;

import de.qaware.smartlabaction.action.info.generic.IActionInfo;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractActionSubmittable<ActionArgsT extends IActionArgs, ReturnT> implements IActionSubmittable<ActionArgsT, ReturnT> {

    protected final IActionInfo actionInfo;

    public AbstractActionSubmittable(IActionInfo actionInfo) {
        this.actionInfo = actionInfo;
    }

    public String getActionId() {
        return this.actionInfo.getActionId();
    }
}
