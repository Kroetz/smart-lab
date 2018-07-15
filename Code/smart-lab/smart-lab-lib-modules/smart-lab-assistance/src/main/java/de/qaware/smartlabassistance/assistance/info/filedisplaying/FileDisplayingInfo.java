package de.qaware.smartlabassistance.assistance.info.filedisplaying;

import de.qaware.smartlabassistance.assistance.info.generic.AbstractAssistanceInfo;
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
    public static final Set<String> ASSISTANCE_ID_ALIASES = Stream.of(
            "file-displaying",
            "file displaying").collect(Collectors.toSet());
    public static final String ASSISTANCE_COMMAND = "displayFile";
    public static final Set<String> ASSISTANCE_COMMAND_ALIASES = Stream.of(
            "display-file",
            "display file").collect(Collectors.toSet());

    public FileDisplayingInfo() {
        super(ASSISTANCE_ID, ASSISTANCE_ID_ALIASES, ASSISTANCE_COMMAND, ASSISTANCE_COMMAND_ALIASES);
    }

    @Override
    public IAssistanceConfiguration createConfiguration(Map<String, String> configProperties) {
        return new Configuration(this, configProperties);
    }

    // TODO: Which annotation can be removed?
    @Getter
    @ToString
    @EqualsAndHashCode(callSuper = true)
    @Slf4j
    public static class Configuration extends AbstractAssistanceInfo.AbstractConfiguration {

        private Configuration(FileDisplayingInfo fileDisplayingInfo, Map<String, String> configProperties) {
            super(fileDisplayingInfo);
            for(String key : configProperties.keySet()) {
                switch (key) {
                    default:
                        log.warn("Ignoring config property {} since it is not relevant for the assistance {}", key, getAssistanceId());
                        break;
                }
            }
        }

        @Override
        public String toConfigLangString() {
            Map<String, String> configProperties = new HashMap<>();
            return toConfigLangString(configProperties);
        }
    }
}
