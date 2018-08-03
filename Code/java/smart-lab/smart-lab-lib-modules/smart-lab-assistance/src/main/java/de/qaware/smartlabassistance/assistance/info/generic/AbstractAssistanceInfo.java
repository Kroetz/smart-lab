package de.qaware.smartlabassistance.assistance.info.generic;

import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static de.qaware.smartlabcore.miscellaneous.StringUtils.*;
import static java.lang.String.format;

@Slf4j
@ToString
@EqualsAndHashCode
public abstract class AbstractAssistanceInfo implements IAssistanceInfo {

    protected final String assistanceId;
    protected final Set<String> assistanceIdAliases;
    protected final String assistanceCommand;
    protected final Set<String> assistanceCommandAliases;

    public AbstractAssistanceInfo(
            String assistanceId,
            Set<String> assistanceIdAliases,
            String assistanceCommand,
            Set<String> assistanceCommandAliases) {
        this.assistanceId = assistanceId;
        this.assistanceIdAliases = assistanceIdAliases;
        this.assistanceCommand = assistanceCommand;
        this.assistanceCommandAliases = assistanceCommandAliases;
    }

    @Override
    public String getAssistanceId() {
        return this.assistanceId;
    }

    @Override
    public Set<String> getAssistanceIdAliases() {
        return this.assistanceIdAliases;
    }

    @Override
    public String getAssistanceCommand() {
        return this.assistanceCommand;
    }

    @Override
    public Set<String> getAssistanceCommandAliases() {
        return this.assistanceCommandAliases;
    }

    // TODO: Possible to force inner class for configuration?
    @ToString
    @EqualsAndHashCode
    @Slf4j
    protected abstract static class AbstractConfiguration implements IAssistanceConfiguration {

        private final IAssistanceInfo assistanceInfo;

        protected AbstractConfiguration(IAssistanceInfo assistanceInfo) {
            this.assistanceInfo = assistanceInfo;
        }

        @Override
        public String getAssistanceId() {
            return this.assistanceInfo.getAssistanceId();
        }

        @Override
        public String getAssistanceCommand() {
            return this.assistanceInfo.getAssistanceCommand();
        }

        @Override
        public String toConfigLangString() {
            return toConfigLangString(getConfigProperties());
        }

        protected String toConfigLangString(Map<String, String> configProperties) {
            return new StringBuilder()
                    .append(TAB).append(TAB)
                    .append(getAssistanceCommand())
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
