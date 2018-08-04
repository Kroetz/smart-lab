package de.qaware.smartlabassistance.assistance.info.minutetaking;

import com.google.common.collect.ImmutableMap;
import de.qaware.smartlabassistance.assistance.info.generic.AbstractAssistanceInfo;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.exception.ConfigurationException;
import de.qaware.smartlabcore.miscellaneous.Language;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

import static java.lang.String.format;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.of;

@Component
@Slf4j
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MinuteTakingInfo extends AbstractAssistanceInfo {

    public static final String ASSISTANCE_ID = "minuteTaking";
    // TODO: Simpler with Java 9 (see https://stackoverflow.com/questions/2041778/how-to-initialize-hashset-values-by-construction)
    public static final Set<String> ASSISTANCE_ID_ALIASES = of(
            "minute-taking",
            "minute taking").collect(toSet());
    public static final String ASSISTANCE_COMMAND = "takeMinutes";
    public static final Set<String> ASSISTANCE_COMMAND_ALIASES = of(
            "take-minutes",
            "take minutes").collect(toSet());

    public MinuteTakingInfo() {
        super(ASSISTANCE_ID, ASSISTANCE_ID_ALIASES, ASSISTANCE_COMMAND, ASSISTANCE_COMMAND_ALIASES);
    }

    @Override
    public IAssistanceConfiguration createConfiguration(Map<String, String> configProperties) {
        return new Configuration(this, configProperties);
    }

    @Getter
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    @Slf4j
    public static class Configuration extends AbstractAssistanceInfo.AbstractConfiguration {

        public static final String CONFIG_PROPERTY_KEY_SPOKEN_LANGUAGE = "spokenLanguage";
        public static final String CONFIG_PROPERTY_KEY_UPLOAD_DIR = "uploadDir";
        public static final String CONFIG_PROPERTY_KEY_MICROPHONE_ID = "microphoneId";

        private Language spokenLanguage;
        private String uploadDir;
        private DeviceId microphoneId;

        private Configuration(MinuteTakingInfo minuteTakingInfo, Map<String, String> configProperties) {
            super(minuteTakingInfo);
            for(String key : configProperties.keySet()) {
                switch (key) {
                    case CONFIG_PROPERTY_KEY_SPOKEN_LANGUAGE:
                        try {
                            this.spokenLanguage = Language.of(configProperties.get(key));
                        }
                        catch(IllegalArgumentException e) {
                            String errorMessage = format("Configured language \"%s\" for the assistance \"%s\" is invalid", configProperties.get(key), getAssistanceId());
                            log.error(errorMessage, e);
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
        public Map<String, String> getConfigProperties() {
            return ImmutableMap.<String, String>builder()
                    .put(CONFIG_PROPERTY_KEY_SPOKEN_LANGUAGE, this.spokenLanguage.toString())
                    .put(CONFIG_PROPERTY_KEY_UPLOAD_DIR, this.uploadDir)
                    .put(CONFIG_PROPERTY_KEY_MICROPHONE_ID, this.microphoneId.getIdValue())
                    .build();
        }
    }
}
