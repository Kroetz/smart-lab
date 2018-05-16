package de.qaware.smartlabcommons.data.action;

public abstract class AbstractAction implements IAction {

    protected final String actionId;

    public AbstractAction(String actionId) {
        this.actionId = actionId;
    }

    @Override
    public String getActionId() {
        return this.actionId;
    }
}
