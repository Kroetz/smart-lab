package de.qaware.smartlab.core.data.assistance;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlab.core.miscellaneous.Constants;

import java.util.Map;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = Constants.JSON_TYPE_PROPERTY_NAME)
public interface IAssistanceConfiguration {

    String getAssistanceId();
    String getAssistanceCommand();
    Map<String, String> getConfigProperties();
    String toConfigLangString();
}