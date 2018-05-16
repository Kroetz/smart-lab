package de.qaware.smartlabcommons.data.action;

public interface IAction {

    String getActionId();
    void executeAction(IActionArgs actionArgs);
}
