package de.qaware.smartlabassistance.assistance.generic;

import java.util.Set;

public interface IAssistanceIdSupplier {

    String getAssistanceId();
    Set<String> getAssistanceIdAliases();
}
