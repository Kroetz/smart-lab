package de.qaware.smartlabassistance.assistance.info.filedisplaying;

import com.google.common.collect.ImmutableMap;
import de.qaware.smartlabassistance.assistance.info.generic.AbstractAssistanceInfo;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.of;

@Component
@Slf4j
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FileDisplayingInfo extends AbstractAssistanceInfo {

    public static final String ASSISTANCE_ID = "fileDisplaying";
    // TODO: Simpler with Java 9 (see https://stackoverflow.com/questions/2041778/how-to-initialize-hashset-values-by-construction)
    public static final Set<String> ASSISTANCE_ID_ALIASES = of(
            "file-displaying",
            "file displaying").collect(toSet());
    public static final String ASSISTANCE_COMMAND = "displayFile";
    public static final Set<String> ASSISTANCE_COMMAND_ALIASES = of(
            "display-file",
            "display file").collect(toSet());

    public FileDisplayingInfo() {
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

        public static final String CONFIG_PROPERTY_KEY_FILE = "file";
        public static final String CONFIG_PROPERTY_KEY_PROGRAM_ID = "programId";
        public static final String CONFIG_PROPERTY_KEY_DISPLAY_ID = "displayId";

        private String filePath;
        private DeviceId programId;
        private DeviceId displayId;

        private Configuration(FileDisplayingInfo fileDisplayingInfo, Map<String, String> configProperties) {
            super(fileDisplayingInfo);
            for(String key : configProperties.keySet()) {
                switch (key) {
                    case CONFIG_PROPERTY_KEY_FILE:
                        this.filePath = configProperties.get(key);
                        break;
                    case CONFIG_PROPERTY_KEY_PROGRAM_ID:
                        this.programId = DeviceId.of(configProperties.get(key));
                        break;
                    case CONFIG_PROPERTY_KEY_DISPLAY_ID:
                        this.displayId = DeviceId.of(configProperties.get(key));
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
                    .put(CONFIG_PROPERTY_KEY_FILE, this.filePath)
                    .put(CONFIG_PROPERTY_KEY_PROGRAM_ID, this.programId.getIdValue())
                    .put(CONFIG_PROPERTY_KEY_DISPLAY_ID, this.displayId.getIdValue())
                    .build();
        }
    }
}
