package de.qaware.smartlab.assistance.assistances.info.websitedisplaying;

import com.google.common.collect.ImmutableMap;
import de.qaware.smartlab.assistance.assistances.info.generic.AbstractAssistanceInfo;
import de.qaware.smartlab.core.assistance.IAssistanceConfiguration;
import de.qaware.smartlab.core.data.actuator.ActuatorId;
import de.qaware.smartlab.core.exception.configuration.ConfigurationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.of;

@Component
@Slf4j
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class WebsiteDisplayingInfo extends AbstractAssistanceInfo {

    public static final String ASSISTANCE_ID = "websiteDisplaying";
    // TODO: Simpler with Java 9 (see https://stackoverflow.com/questions/2041778/how-to-initialize-hashset-values-by-construction)
    public static final Set<String> ASSISTANCE_ID_ALIASES = of(
            "website-displaying",
            "website displaying").collect(toSet());
    public static final String ASSISTANCE_COMMAND = "displayWebsite";
    public static final Set<String> ASSISTANCE_COMMAND_ALIASES = of(
            "display-website",
            "display website").collect(toSet());

    public WebsiteDisplayingInfo() {
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

        public static final String CONFIG_PROPERTY_KEY_URL = "url";
        public static final String CONFIG_PROPERTY_KEY_WEB_BROWSER_ID = "webBrowserId";
        public static final String CONFIG_PROPERTY_KEY_DISPLAY_ID = "displayId";

        private URL url;
        private ActuatorId webBrowserId;
        private ActuatorId displayId;

        private Configuration(WebsiteDisplayingInfo websiteDisplayingInfo, Map<String, String> configProperties) {
            super(websiteDisplayingInfo);
            for(String key : configProperties.keySet()) {
                switch (key) {
                    case CONFIG_PROPERTY_KEY_URL:
                        try {
                            this.url = new URL(configProperties.get(key));
                        }
                        catch(MalformedURLException e) {
                            String errorMessage = format("Configured URL \"%s\" for the assistance \"%s\" is invalid", configProperties.get(key), getAssistanceId());
                            log.error(errorMessage, e);
                            throw new ConfigurationException(errorMessage, e);
                        }
                        break;
                    case CONFIG_PROPERTY_KEY_WEB_BROWSER_ID:
                        this.webBrowserId = ActuatorId.of(configProperties.get(key));
                        break;
                    case CONFIG_PROPERTY_KEY_DISPLAY_ID:
                        this.displayId = ActuatorId.of(configProperties.get(key));
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
                    .put(CONFIG_PROPERTY_KEY_URL, this.url.toString())
                    .put(CONFIG_PROPERTY_KEY_WEB_BROWSER_ID, this.webBrowserId.getIdValue())
                    .put(CONFIG_PROPERTY_KEY_DISPLAY_ID, this.displayId.getIdValue())
                    .build();
        }
    }
}
