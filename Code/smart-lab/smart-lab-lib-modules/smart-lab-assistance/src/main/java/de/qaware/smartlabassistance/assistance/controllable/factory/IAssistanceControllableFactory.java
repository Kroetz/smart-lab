package de.qaware.smartlabassistance.assistance.controllable.factory;

import de.qaware.smartlabassistance.assistance.controllable.IAssistanceControllable;

import java.util.Set;

public interface IAssistanceControllableFactory {

    String getAssociatedAssistanceId();
    Set<String> getAssociatedAssistanceAliases();
    IAssistanceControllable newInstance();
}
