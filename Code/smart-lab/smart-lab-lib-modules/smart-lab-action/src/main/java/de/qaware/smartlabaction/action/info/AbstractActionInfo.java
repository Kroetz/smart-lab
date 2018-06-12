package de.qaware.smartlabaction.action.info;

import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.exception.UnmatchingActionArgsTypeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractActionInfo implements IActionInfo {

    protected final String actionId;

    public AbstractActionInfo(String actionId) {
        this.actionId = actionId;
    }

    @Override
    public String getActionId() {
        return this.actionId;
    }
}
