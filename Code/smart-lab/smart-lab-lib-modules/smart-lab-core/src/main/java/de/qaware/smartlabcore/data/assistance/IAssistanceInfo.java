package de.qaware.smartlabcore.data.assistance;

import java.util.Map;
import java.util.Set;

public interface IAssistanceInfo {

    String getAssistanceId();
    Set<String> getAssistanceAliases();
    IAssistanceConfiguration createConfiguration(Map<String, String> configProperties);
}
