package de.qaware.smartlab.assistance.assistances.controllable.miscellaneous.factory;

import de.qaware.smartlab.assistance.assistances.controllable.generic.IAssistanceControllable;

import java.util.Set;

public interface IAssistanceControllableFactory {

    String getAssociatedAssistanceId();
    Set<String> getAssociatedAssistanceIdAliases();
    IAssistanceControllable newInstance();
}
