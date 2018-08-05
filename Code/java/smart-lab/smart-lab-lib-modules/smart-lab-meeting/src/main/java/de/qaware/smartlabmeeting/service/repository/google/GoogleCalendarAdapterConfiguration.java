package de.qaware.smartlabmeeting.service.repository.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.exception.ConfigurationException;
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
import java.util.*;

import static java.nio.file.Files.exists;
import static java.nio.file.Paths.get;

@Configuration
@ConditionalOnProperty(
        prefix = Property.Prefix.MEETING_MANAGEMENT_REPOSITORY,
        name = Property.Name.MEETING_MANAGEMENT_REPOSITORY,
        havingValue = Property.Value.MeetingManagementRepository.GOOGLE_CALENDAR)
@EnableConfigurationProperties(GoogleCalendarAdapterConfiguration.Properties.class)
public class GoogleCalendarAdapterConfiguration {

    private final Properties properties;

    public GoogleCalendarAdapterConfiguration(Properties properties) {
        this.properties = properties;
    }

    @Bean
    public static Validator configurationPropertiesValidator() {
        return new Properties.PropertiesValidator();
    }

    @Bean
    public Path googleCalendarCredentialFile() {
        return this.properties.getCredentialsFile();
    }

    @Bean
    // TODO: String literal
    @Qualifier("googleCalendarScopes")
    public Collection<String> googleCalendarScopes() {
        return this.properties.getScopes();
    }

    @Bean
    public String googleCalendarApplicationName() {
        return this.properties.getApplicationName();
    }

    @Bean
    public HttpTransport googleCalendarHttpTransport() {
        try {
            return GoogleNetHttpTransport.newTrustedTransport();
        } catch (Exception e) {
            throw new ConfigurationException(e);
        }
    }

    @Bean
    public JsonFactory googleCalendarJsonFactory() {
        return JacksonFactory.getDefaultInstance();
    }

    @Bean
    // TODO: String literal
    @Qualifier("googleCalendarLocationMapping")
    public BiMap<LocationId, String> googleCalendarLocationMapping() {
        return this.properties.getLocationMapping();
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "smart-lab.meeting-management.google-calendar")
    @Validated
    public static class Properties {

        private static final Path DEFAULT_CREDENTIALS_FILE = get(System.getProperty("user.home"), "smart-lab", "google_calendar_credentials.json");
        private static final Collection<String> DEFAULT_SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
        private static final String DEFAULT_APPLICATION_NAME = "Default Google Calendar application name";

        private Path credentialsFile;
        private Collection<String> scopes;
        private String applicationName;
        private Map<String, String> locationMapping;

        public Properties() {
            this.credentialsFile = DEFAULT_CREDENTIALS_FILE;
            this.scopes = DEFAULT_SCOPES;
            this.applicationName = DEFAULT_APPLICATION_NAME;
            this.locationMapping = new HashMap<>();
        }

        public Path getCredentialsFile() {
            return this.credentialsFile;
        }

        public void setCredentialsFile(String credentialsFile) {
            this.credentialsFile = get(credentialsFile);
        }

        public Collection<String> getScopes() {
            return this.scopes;
        }

        public void setScopes(List<String> scopes) {
            this.scopes = scopes;
        }

        public String getApplicationName() {
            return this.applicationName;
        }

        public void setApplicationName(String applicationName) {
            this.applicationName = applicationName;
        }

        public BiMap<LocationId, String> getLocationMapping() {
            return this.locationMapping.keySet().stream().collect(
                    HashBiMap::create,
                    (biMap, locationId) -> biMap.put(LocationId.of(locationId), this.locationMapping.get(locationId)),
                    BiMap::putAll);
        }

        public void setLocationMapping(Map<String, String> locationMapping) {
            this.locationMapping = locationMapping;
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
                if(!exists(properties.getCredentialsFile())) {
                    // TODO: String literals
                    errors.rejectValue(
                            "credentialsFile",
                            "The path of the Google calendar credentials file must be valid");
                }
            }
        }
    }
}
