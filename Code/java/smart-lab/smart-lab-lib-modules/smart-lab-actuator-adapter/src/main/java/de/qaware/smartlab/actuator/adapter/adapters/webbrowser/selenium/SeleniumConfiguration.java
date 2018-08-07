package de.qaware.smartlab.actuator.adapter.adapters.webbrowser.selenium;

import de.qaware.smartlab.core.miscellaneous.Property;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import java.nio.file.Path;

import static java.nio.file.Files.exists;
import static java.nio.file.Paths.get;

@Configuration
@ConditionalOnProperty(
        prefix = Property.Prefix.SELENIUM,
        name = Property.Name.SELENIUM,
        havingValue = Property.Value.TRUE)
@EnableConfigurationProperties(SeleniumConfiguration.Properties.class)
public class SeleniumConfiguration {

    private final Properties properties;

    public SeleniumConfiguration(Properties properties) {
        this.properties = properties;
    }

    @Bean
    public static Validator configurationPropertiesValidator() {
        return new Properties.PropertiesValidator();
    }

    @Bean
    // TODO: String literals
    @Qualifier("seleniumGeckoDriverFile")
    public Path seleniumGeckoDriverFile() {
        return this.properties.getGeckoDriverFile();
    }

    @Bean
    // TODO: String literals
    @Qualifier("seleniumChromeDriverFile")
    public Path seleniumChromeDriverFile() {
        return this.properties.getChromeDriverFile();
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "smart-lab.actuator.selenium", ignoreInvalidFields = true)
    @Validated
    public static class Properties {

        private static final Path DEFAULT_GECKO_DRIVER_FILE = get(System.getProperty("user.home"), "smart-lab", "geckodriver.exe");
        private static final Path DEFAULT_CHROME_DRIVER_FILE = get(System.getProperty("user.home"), "smart-lab", "chromedriver.exe");

        private Path geckoDriverFile;
        private Path chromeDriverFile;

        public Properties() {
            this.geckoDriverFile = DEFAULT_GECKO_DRIVER_FILE;
            this.chromeDriverFile = DEFAULT_CHROME_DRIVER_FILE;
        }

        public Path getGeckoDriverFile() {
            return this.geckoDriverFile;
        }

        public void setGeckoDriverFile(String geckoDriverFile) {
            this.geckoDriverFile = get(geckoDriverFile);
        }

        public Path getChromeDriverFile() {
            return this.chromeDriverFile;
        }

        public void setChromeDriverFile(String chromeDriverFile) {
            this.chromeDriverFile = get(chromeDriverFile);
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
                if(!exists(properties.getGeckoDriverFile())) {
                    // TODO: String literals
                    String errorMessage = "The path of the Gecko driver file must be valid";
                    log.error(errorMessage);
                    errors.rejectValue(
                            "geckoDriverFile",
                            errorMessage);
                }
                if(!exists(properties.getChromeDriverFile())) {
                    // TODO: String literals
                    String errorMessage = "The path of the Chrome driver file must be valid";
                    log.error(errorMessage);
                    errors.rejectValue(
                            "chromeDriverFile",
                            errorMessage);
                }
            }
        }
    }
}