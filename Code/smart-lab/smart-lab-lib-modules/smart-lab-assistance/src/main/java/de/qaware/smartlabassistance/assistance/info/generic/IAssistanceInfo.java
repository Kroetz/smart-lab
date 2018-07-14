package de.qaware.smartlabassistance.assistance.info.generic;

import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;

import java.util.Map;
import java.util.Set;

public interface IAssistanceInfo {

    String getAssistanceId();
    Set<String> getAssistanceAliases();
    IAssistanceConfiguration createConfiguration(Map<String, String> configProperties);
}