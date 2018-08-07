package de.qaware.smartlab.assistance.assistances.generic;

import de.qaware.smartlab.core.data.assistance.IAssistanceConfiguration;

import java.util.Map;

public interface IAssistanceConfigurationSupplier {

    IAssistanceConfiguration createConfiguration(Map<String, String> configProperties);
}
