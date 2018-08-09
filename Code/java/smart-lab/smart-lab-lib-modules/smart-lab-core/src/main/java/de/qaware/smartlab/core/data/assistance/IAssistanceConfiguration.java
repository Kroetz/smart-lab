package de.qaware.smartlab.core.data.assistance;

import java.util.Map;

public interface IAssistanceConfiguration {

    String getAssistanceId();
    String getAssistanceCommand();
    Map<String, String> getConfigProperties();
    String toConfigLangString();
}
