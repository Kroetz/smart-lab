package de.qaware.smartlab.event.management.service.repository.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.exception.configuration.ConfigurationException;
import de.qaware.smartlab.core.miscellaneous.Property;
import de.qaware.smartlab.event.management.service.repository.IEventManagementRepository;
import de.qaware.smartlab.event.management.service.repository.parser.IEventParser;
import lombok.extern.slf4j.Slf4j;
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
        prefix = Property.Prefix.EVENT_MANAGEMENT_REPOSITORY,
        name = Property.Name.EVENT_MANAGEMENT_REPOSITORY,
        havingValue = Property.Value.EventManagementRepository.GOOGLE_CALENDAR)
@EnableConfigurationProperties(GoogleCalendarAdapterConfiguration.Properties.class)
public class GoogleCalendarAdapterConfiguration {

    private final Properties properties;
    private final IEventParser eventParser;
    private final Set<IEvent> initialEvents;

    public GoogleCalendarAdapterConfiguration(Properties properties, IEventParser eventParser, Set<IEvent> initialEvents) {
        this.properties = properties;
        this.eventParser = eventParser;
        this.initialEvents = initialEvents;
    }

    @Bean
    public static Validator configurationPropertiesValidator() {
        return new Properties.PropertiesValidator();
    }

    @Bean
    public IEventManagementRepository repo() {
        try {
            return new GoogleCalendarAdapter(
                    this.properties.getCredentialsFile(),
                    this.properties.getScopes(),
                    this.properties.getApplicationName(),
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    this.properties.getLocationMapping(),
                    this.eventParser,
                    this.initialEvents);
        } catch (Exception e) {
            // TODO: Exception message and logging
            throw new ConfigurationException(e);
        }
    }

    @ConfigurationProperties(prefix = Properties.PREFIX, ignoreInvalidFields = true)
    @Validated
    public static class Properties {

        private static final String PREFIX = "smart-lab.event-management.google-calendar";
        private static final String FIELD_NAME_CREDENTIALS_FILE = "credentialsFile";
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
                    String errorMessage = "The path of the Google calendar credentials file must be valid";
                    log.error(errorMessage);
                    errors.rejectValue(
                            FIELD_NAME_CREDENTIALS_FILE,
                            errorMessage);
                }
            }
        }
    }
}
