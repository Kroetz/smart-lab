package de.qaware.smartlabactuatoradapter.actuator.fileassociatedprogram.powerpoint;

import de.qaware.smartlabcore.miscellaneous.Property;
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
        prefix = Property.Prefix.POWER_POINT,
        name = Property.Name.POWER_POINT,
        havingValue = Property.Value.TRUE)
@EnableConfigurationProperties(PowerPointConfiguration.Properties.class)
public class PowerPointConfiguration {

    private final Properties properties;

    public PowerPointConfiguration(Properties properties) {
        this.properties = properties;
    }

    @Bean
    public static Validator configurationPropertiesValidator() {
        return new Properties.PropertiesValidator();
    }

    @Bean
    // TODO: String literals
    @Qualifier("powerPointExecutable")
    public Path powerPointExecutable() {
        return this.properties.getExecutableFile();
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "smart-lab.actuator.power-point", ignoreInvalidFields = true)
    @Validated
    public static class Properties {

        private static final Path DEFAULT_EXECUTABLE_FILE = get("C:\\Program Files\\Microsoft Office\\root\\Office16\\POWERPNT.EXE");

        private Path executableFile;

        public Properties() {
            this.executableFile = DEFAULT_EXECUTABLE_FILE;
        }

        public Path getExecutableFile() {
            return this.executableFile;
        }

        public void setExecutableFile(String executableFile) {
            this.executableFile = get(executableFile);
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
                if(!exists(properties.getExecutableFile())) {
                    // TODO: String literals
                    String errorMessage = "The path of the PowerPoint executable file must be valid";
                    log.error(errorMessage);
                    errors.rejectValue(
                            "executableFile",
                            errorMessage);
                }
            }
        }
    }
}
