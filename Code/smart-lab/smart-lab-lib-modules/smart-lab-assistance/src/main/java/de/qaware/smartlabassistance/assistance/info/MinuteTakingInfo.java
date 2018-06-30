package de.qaware.smartlabassistance.assistance.info;

import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.qaware.smartlabcore.miscellaneous.StringUtils.NEW_LINE;
import static de.qaware.smartlabcore.miscellaneous.StringUtils.PARENTHESES_TEMPLATE;
import static de.qaware.smartlabcore.miscellaneous.StringUtils.TAB;
import static java.lang.String.*;

@Component
@Slf4j
public class MinuteTakingInfo extends AbstractAssistanceInfo {

    public static final String ASSISTANCE_ID = "minuteTaking";
    // TODO: Simpler with Java 9 (see https://stackoverflow.com/questions/2041778/how-to-initialize-hashset-values-by-construction)
    public static final Set<String> ASSISTANCE_ALIASES = Stream.of(
            "minute-taking",
            "minute taking").collect(Collectors.toSet());

    public MinuteTakingInfo() {
        super(ASSISTANCE_ID, ASSISTANCE_ALIASES);
    }

    @Override
    public IAssistanceConfiguration createConfiguration(Map<String, String> configProperties) {
        return new Configuration(configProperties);
    }

    // TODO: Which annotation can be removed?
    @Getter
    @ToString
    @EqualsAndHashCode
    public class Configuration implements IAssistanceConfiguration {

        public static final String CONFIG_PROPERTY_KEY_MICROPHONE_ID = "microphoneId";
        private DeviceId microphoneId;

        private Configuration(Map<String, String> configProperties) {
            // TODO: process config properties
        }

        @Override
        public String getAssistanceId() {
            return MinuteTakingInfo.this.getAssistanceId();
        }

        @Override
        public String toConfigLangString() {
            // TODO
            return TAB + TAB + getAssistanceId() + format(PARENTHESES_TEMPLATE, "") + NEW_LINE;
        }
    }
}
