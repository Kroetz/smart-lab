package de.qaware.smartlabassistance.assistance.controllable.miscellaneous.factory;

import de.qaware.smartlabassistance.assistance.controllable.generic.IAssistanceControllable;

import java.util.Set;

public interface IAssistanceControllableFactory {

    String getAssociatedAssistanceId();
    Set<String> getAssociatedAssistanceAliases();
    IAssistanceControllable newInstance();
}
