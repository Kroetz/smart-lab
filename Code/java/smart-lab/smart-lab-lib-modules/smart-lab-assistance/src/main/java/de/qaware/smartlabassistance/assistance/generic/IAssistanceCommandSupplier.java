package de.qaware.smartlabassistance.assistance.generic;

import java.util.Set;

public interface IAssistanceCommandSupplier {

    String getAssistanceCommand();
    Set<String> getAssistanceCommandAliases();
}
