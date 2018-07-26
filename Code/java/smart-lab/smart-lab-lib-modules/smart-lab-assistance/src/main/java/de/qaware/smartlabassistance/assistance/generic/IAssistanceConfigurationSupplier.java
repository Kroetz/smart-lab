package de.qaware.smartlabassistance.assistance.generic;

import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;

import java.util.Map;

public interface IAssistanceConfigurationSupplier {

    IAssistanceConfiguration createConfiguration(Map<String, String> configProperties);
}
