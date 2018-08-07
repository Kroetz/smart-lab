package de.qaware.smartlab.action.actions.info.generic;

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
