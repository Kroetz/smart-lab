package de.qaware.smartlabassistance.assistance.info.websitedisplaying;

import de.qaware.smartlabassistance.assistance.info.generic.AbstractAssistanceInfo;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.exception.ConfigurationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;

@Component
@Slf4j
public class DisplayWebsiteInfo extends AbstractAssistanceInfo {

    public static final String ASSISTANCE_ID = "displayWebsite";
    // TODO: Simpler with Java 9 (see https://stackoverflow.com/questions/2041778/how-to-initialize-hashset-values-by-construction)
    public static final Set<String> ASSISTANCE_ALIASES = Stream.of(
            "display-website",
            "display website").collect(Collectors.toSet());

    public DisplayWebsiteInfo() {
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

        public static final String CONFIG_PROPERTY_KEY_URL = "url";
        public static final String CONFIG_PROPERTY_KEY_WEB_BROWSER_ID = "webBrowserId";

        private URL url;
        private DeviceId webBrowserId;

        private Configuration(Map<String, String> configProperties) {
            for(String key : configProperties.keySet()) {
                switch (key) {
                    case CONFIG_PROPERTY_KEY_URL:
                        try {
                            this.url = new URL(configProperties.get(key));
                        }
                        catch(MalformedURLException e) {
                            String errorMessage = format("Configured URL \"%s\" for the assistance \"%s\" is invalid", configProperties.get(key), getAssistanceId());
                            log.error(errorMessage);
                            throw new ConfigurationException(errorMessage, e);
                        }
                        break;
                    case CONFIG_PROPERTY_KEY_WEB_BROWSER_ID:
                        this.webBrowserId = DeviceId.of(configProperties.get(key));
                        break;
                    default:
                        log.warn("Ignoring config property {} since it is not relevant for the assistance {}", key, getAssistanceId());
                        break;
                }
            }
        }

        @Override
        public String getAssistanceId() {
            return DisplayWebsiteInfo.this.getAssistanceId();
        }

        @Override
        public String toConfigLangString() {
            Map<String, String> configProperties = new HashMap<>();
            configProperties.put(CONFIG_PROPERTY_KEY_URL, this.url.toString());
            configProperties.put(CONFIG_PROPERTY_KEY_WEB_BROWSER_ID, this.webBrowserId.getIdValue());
            return toConfigLangString(configProperties);
        }
    }
}
