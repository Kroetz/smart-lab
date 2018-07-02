package de.qaware.smartlabassistance.assistance.info;

import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.assistance.IAssistanceInfo;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static de.qaware.smartlabcore.miscellaneous.StringUtils.*;
import static java.lang.String.format;

@Slf4j
public abstract class AbstractAssistanceInfo implements IAssistanceInfo {

    protected final String assistanceId;
    protected final Set<String> assistanceAliases;

    public AbstractAssistanceInfo(
            String assistanceId,
            Set<String> assistanceAliases) {
        this.assistanceId = assistanceId;
        this.assistanceAliases = assistanceAliases;
    }

    @Override
    public String getAssistanceId() {
        return this.assistanceId;
    }

    @Override
    public Set<String> getAssistanceAliases() {
        return this.assistanceAliases;
    }

    // TODO: Possible to force inner class for configuration?
    @EqualsAndHashCode
    protected abstract static class AbstractConfiguration implements IAssistanceConfiguration {

        protected String toConfigLangString(Map<String, String> configProperties) {
            return new StringBuilder()
                    .append(TAB).append(TAB)
                    .append(getAssistanceId())
                    .append(format(PARENTHESES_TEMPLATE, configPropertiesToConfigLangString(configProperties)))
                    .append(NEW_LINE)
                    .toString();
        }

        private String configPropertiesToConfigLangString(Map<String, String> configProperties) {
            StringBuilder builder = new StringBuilder();
            Iterator<Map.Entry<String, String>> iterator = configProperties.entrySet().iterator();
            while(iterator.hasNext()) {
                builder.append(configPropertyToConfigLangString(iterator.next()));
                if(iterator.hasNext()) builder.append(COMMA);
            }
            return builder.toString();
        }

        private String configPropertyToConfigLangString(Map.Entry<String, String> configProperty) {
            return new StringBuilder()
                    .append(NEW_LINE)
                    .append(TAB).append(TAB).append(TAB)
                    .append(configProperty.getKey())
                    .append(COLON)
                    .append(SPACE)
                    .append(format(DOUBLE_QUOTED_TEMPLATE, configProperty.getValue()))
                    .toString();
        }
    }
}
