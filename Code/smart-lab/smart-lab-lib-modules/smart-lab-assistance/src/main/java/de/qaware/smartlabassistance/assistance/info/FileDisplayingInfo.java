package de.qaware.smartlabassistance.assistance.info;

import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class FileDisplayingInfo extends AbstractAssistanceInfo {

    public static final String ASSISTANCE_ID = "fileDisplaying";
    // TODO: Simpler with Java 9 (see https://stackoverflow.com/questions/2041778/how-to-initialize-hashset-values-by-construction)
    public static final Set<String> ASSISTANCE_ALIASES = Stream.of(
            "file-displaying",
            "file displaying").collect(Collectors.toSet());

    public FileDisplayingInfo() {
        super(ASSISTANCE_ID, ASSISTANCE_ALIASES);
    }

    @Override
    public IAssistanceConfiguration createConfiguration(Map<String, String> configProperties) {
        return new Configuration(configProperties);
    }

    // TODO: Which annotation can be removed?
    @Getter
    @ToString
    @EqualsAndHashCode(callSuper = true)
    public class Configuration extends AbstractAssistanceInfo.AbstractConfiguration {

        private Configuration(Map<String, String> configProperties) {
            for(String key : configProperties.keySet()) {
                switch (key) {
                    default:
                        log.warn("Ignoring config property {} since it is not relevant for the assistance {}", key, getAssistanceId());
                        break;
                }
            }
        }

        @Override
        public String getAssistanceId() {
            return FileDisplayingInfo.this.getAssistanceId();
        }

        @Override
        public String toConfigLangString() {
            Map<String, String> configProperties = new HashMap<>();
            return toConfigLangString(configProperties);
        }
    }
}
