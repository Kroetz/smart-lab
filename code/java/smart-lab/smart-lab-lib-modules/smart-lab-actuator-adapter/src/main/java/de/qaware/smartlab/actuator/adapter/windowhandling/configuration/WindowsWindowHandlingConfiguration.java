package de.qaware.smartlab.actuator.adapter.windowhandling.configuration;

import de.qaware.smartlab.actuator.adapter.windowhandling.condition.ConditionalOnWindowsAndWindowHandlingEnabled;
import de.qaware.smartlab.actuator.adapter.windowhandling.windowhandler.IWindowHandler;
import de.qaware.smartlab.actuator.adapter.windowhandling.windowhandler.WindowsWindowHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import java.nio.file.Path;
import java.time.Duration;
import java.util.Map;

import static java.nio.file.Files.exists;
import static java.nio.file.Paths.get;

@Configuration
@Conditional(ConditionalOnWindowsAndWindowHandlingEnabled.class)
@Slf4j
@EnableConfigurationProperties(WindowsWindowHandlingConfiguration.Properties.class)
public class WindowsWindowHandlingConfiguration {

    private final Properties properties;
    private final Map<String, String> localDisplaysBySmartLabDisplayId;
    private final Duration findWindowTimeout;

    public WindowsWindowHandlingConfiguration(
            Properties properties,
            @Qualifier(CommonWindowHandlingConfiguration.QUALIFIER_LOCAL_DISPLAYS_BY_SMART_LAB_DISPLAY_IDS) Map<String, String> localDisplaysBySmartLabDisplayId,
            @Qualifier(CommonWindowHandlingConfiguration.QUALIFIER_FIND_WINDOW_TIMEOUT) Duration findWindowTimeout) {
        this.properties = properties;
        this.localDisplaysBySmartLabDisplayId = localDisplaysBySmartLabDisplayId;
        this.findWindowTimeout = findWindowTimeout;
    }

    @Bean
    public static Validator configurationPropertiesValidator() {
        return new Properties.PropertiesValidator();
    }

    @Bean
    public IWindowHandler windowHandler() {
        return new WindowsWindowHandler(
                this.properties.getWindowHandlingDll(),
                this.localDisplaysBySmartLabDisplayId,
                this.findWindowTimeout);
    }

    @ConfigurationProperties(prefix = Properties.PREFIX, ignoreInvalidFields = true)
    @Validated
    public static class Properties {

        private static final String PREFIX = "smart-lab.delegate.window-handling";
        private static final String FIELD_NAME_WINDOW_HANDLING_DLL = "windowHandlingDll";
        private static final Path DEFAULT_WINDOW_HANDLING_DLL = get(System.getProperty("user.home"), "smart-lab", "smart-lab-window-handling.dll");

        private Path windowHandlingDll;

        public Properties() {
            this.windowHandlingDll = DEFAULT_WINDOW_HANDLING_DLL;
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
                    String errorMessage = "The path of the window handling .dll file must be valid";
                    log.error(errorMessage);
                    errors.rejectValue(
                            FIELD_NAME_WINDOW_HANDLING_DLL,
                            errorMessage);
                }
            }
        }
    }
}
