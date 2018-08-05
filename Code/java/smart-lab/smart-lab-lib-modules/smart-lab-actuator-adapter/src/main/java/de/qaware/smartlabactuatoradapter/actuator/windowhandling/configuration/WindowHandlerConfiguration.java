package de.qaware.smartlabactuatoradapter.actuator.windowhandling.configuration;

import de.qaware.smartlabactuatoradapter.actuator.windowhandling.windowhandler.LinuxWindowHandler;
import de.qaware.smartlabactuatoradapter.actuator.windowhandling.windowhandler.WindowsWindowHandler;
import de.qaware.smartlabcore.exception.ConfigurationException;
import de.qaware.smartlabactuatoradapter.actuator.windowhandling.windowhandler.IWindowHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import java.nio.file.Path;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.Files.exists;
import static java.nio.file.Paths.get;
import static org.apache.commons.lang3.SystemUtils.IS_OS_LINUX;
import static org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS;

@Configuration
@Slf4j
@EnableConfigurationProperties(WindowHandlerConfiguration.Properties.class)
public class WindowHandlerConfiguration {

    private final Properties properties;

    public WindowHandlerConfiguration(Properties properties) {
        this.properties = properties;
    }

    @Bean
    public static Validator configurationPropertiesValidator() {
        return new Properties.PropertiesValidator();
    }

    @Bean
    public IWindowHandler windowHandler() {
        if(IS_OS_WINDOWS) {
            return new WindowsWindowHandler(
                    this.properties.getWindowHandlingDll(),
                    this.properties.getDisplays(),
                    this.properties.getFindWindowTimeoutInSeconds());
        }
        else if(IS_OS_LINUX) {
            return new LinuxWindowHandler(
                    this.properties.getDisplays(),
                    this.properties.getFindWindowTimeoutInSeconds());
        }
        throw new ConfigurationException("The operating system is not supported");
    }

    @Bean
    // TODO: String literal
    @Qualifier("localDisplaysBySmartLabDisplayIds")
    public Map<String, String> localDisplaysBySmartLabDisplayIds() {
        return this.properties.getDisplays();
    }

    @Bean
    public Duration findWindowTimeout() {
        return this.properties.getFindWindowTimeoutInSeconds();
    }

    @Bean
    public Path windowHandlingDll() {
        return this.properties.getWindowHandlingDll();
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "smart-lab.delegate", ignoreInvalidFields = true)
    @Validated
    public static class Properties {

        private static final int DEFAULT_FIND_WINDOW_TIMEOUT_IN_SECONDS = 5;
        private static final Path DEFAULT_WINDOW_HANDLING_DLL = get(System.getProperty("user.home"), "smart-lab", "smart-lab-window-handling.dll");

        private Map<String, String> displays;
        private int findWindowTimeoutInSeconds;
        private Path windowHandlingDll;

        public Properties() {
            this.displays = new HashMap<>();
            this.findWindowTimeoutInSeconds = DEFAULT_FIND_WINDOW_TIMEOUT_IN_SECONDS;
            this.windowHandlingDll = DEFAULT_WINDOW_HANDLING_DLL;
        }

        public Map<String, String> getDisplays() {
            return this.displays;
        }

        public void setDisplays(Map<String, String> displays) {
            this.displays = displays;
        }

        public Duration getFindWindowTimeoutInSeconds() {
            return Duration.ofSeconds(this.findWindowTimeoutInSeconds);
        }

        public void setFindWindowTimeoutInSeconds(int findWindowTimeoutInSeconds) {
            this.findWindowTimeoutInSeconds = findWindowTimeoutInSeconds;
        }

        public Path getWindowHandlingDll() {
            return this.windowHandlingDll;
        }

        public void setWindowHandlingDll(String windowHandlingDll) {
            this.windowHandlingDll = get(windowHandlingDll);
        }

        @Slf4j
        public static class PropertiesValidator implements Validator {

            @Override
            public boolean supports(@NonNull Class<?> type) {
                return Properties.class.equals(type);
            }

            @Override
            public void validate(Object o, @NonNull Errors errors) {
                Properties properties = (Properties) o;
                if(!exists(properties.getWindowHandlingDll())) {
                    // TODO: String literals
                    String errorMessage = "The path of the window handling .dll file must be valid";
                    log.error(errorMessage);
                    errors.rejectValue(
                            "windowHandlingDll",
                            errorMessage);
                }
            }
        }
    }
}
