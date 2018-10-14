package de.qaware.smartlab.core.assistance;

import java.util.Map;

public interface IAssistanceConfiguration {

    String getAssistanceId();
    String getAssistanceCommand();
    Map<String, String> getConfigProperties();
    String toConfigLangString();
}
