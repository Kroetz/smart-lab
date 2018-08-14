package de.qaware.smartlab.assistance.assistances.generic;

import java.util.Set;

public interface IAssistanceCommandSupplier {

    String getAssistanceCommand();
    Set<String> getAssistanceCommandAliases();
}
