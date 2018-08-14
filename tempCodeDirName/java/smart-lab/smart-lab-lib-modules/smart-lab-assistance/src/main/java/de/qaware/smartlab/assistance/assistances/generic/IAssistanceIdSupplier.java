package de.qaware.smartlab.assistance.assistances.generic;

import java.util.Set;

public interface IAssistanceIdSupplier {

    String getAssistanceId();
    Set<String> getAssistanceIdAliases();
}
