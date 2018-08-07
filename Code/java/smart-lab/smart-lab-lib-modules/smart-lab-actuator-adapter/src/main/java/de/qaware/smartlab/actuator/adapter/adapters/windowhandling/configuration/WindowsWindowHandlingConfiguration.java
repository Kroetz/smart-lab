package de.qaware.smartlab.actuator.adapter.adapters.windowhandling.configuration;

import de.qaware.smartlab.actuator.adapter.adapters.windowhandling.condition.ConditionalOnWindowsAndWindowHandlingEnabled;
import de.qaware.smartlab.actuator.adapter.adapters.windowhandling.windowhandler.IWindowHandler;
import de.qaware.smartlab.actuator.adapter.adapters.windowhandling.windowhandler.WindowsWindowHandler;
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
            // TODO: String literal
            @Qualifier("localDisplaysBySmartLabDisplayIds") Map<String, String> localDisplaysBySmartLabDisplayId,
            // TODO: String literals
            @Qualifier("findWindowTimeout") Duration findWindowTimeout) {
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

    @Bean
    // TODO: String literals
    @Qualifier("windowHandlingDll")
    public Path windowHandlingDll() {
        return this.properties.getWindowHandlingDll();
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "smart-lab.delegate.window-handling", ignoreInvalidFields = true)
    @Validated
    public static class Properties {

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
