package de.qaware.smartlabassistance.assistance.info;

import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.exception.ConfigurationException;
import de.qaware.smartlabcore.miscellaneous.Language;
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

import static java.lang.String.format;

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
    @EqualsAndHashCode(callSuper = true)
    public class Configuration extends AbstractAssistanceInfo.AbstractConfiguration {

        public static final String CONFIG_PROPERTY_KEY_SPOKEN_LANGUAGE = "spokenLanguage";
        public static final String CONFIG_PROPERTY_KEY_UPLOAD_DIR = "uploadDir";
        public static final String CONFIG_PROPERTY_KEY_MICROPHONE_ID = "microphoneId";

        private Language spokenLanguage;
        private String uploadDir;
        private DeviceId microphoneId;

        private Configuration(Map<String, String> configProperties) {
            for(String key : configProperties.keySet()) {
                switch (key) {
                    case CONFIG_PROPERTY_KEY_SPOKEN_LANGUAGE:
                        try {
                            this.spokenLanguage = Language.of(configProperties.get(key));
                        }
                        catch(IllegalArgumentException e) {
                            String errorMessage = format("Configured language \"%s\" for the assistance \"%s\" is invalid", configProperties.get(key), getAssistanceId());
                            log.error(errorMessage);
                            throw new ConfigurationException(errorMessage, e);
                        }
                        break;
                    case CONFIG_PROPERTY_KEY_MICROPHONE_ID:
                        this.microphoneId = DeviceId.of(configProperties.get(key));
                        break;
                    case CONFIG_PROPERTY_KEY_UPLOAD_DIR:
                        this.uploadDir = configProperties.get(key);
                        break;
                    default:
                        log.warn("Ignoring config property {} since it is not relevant for the assistance {}", key, getAssistanceId());
                        break;
                }
            }
        }

        @Override
        public String getAssistanceId() {
            return MinuteTakingInfo.this.getAssistanceId();
        }

        @Override
        public String toConfigLangString() {
            Map<String, String> configProperties = new HashMap<>();
            configProperties.put(CONFIG_PROPERTY_KEY_SPOKEN_LANGUAGE, this.spokenLanguage.toString());
            configProperties.put(CONFIG_PROPERTY_KEY_UPLOAD_DIR, this.uploadDir);
            configProperties.put(CONFIG_PROPERTY_KEY_MICROPHONE_ID, this.microphoneId.getIdValue());
            return toConfigLangString(configProperties);
        }
    }
}
