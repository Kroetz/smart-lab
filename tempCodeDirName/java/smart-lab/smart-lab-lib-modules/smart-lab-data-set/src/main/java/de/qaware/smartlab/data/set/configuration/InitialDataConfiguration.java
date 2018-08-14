package de.qaware.smartlab.data.set.configuration;

import de.qaware.smartlab.action.annotation.EnableSmartLabActionInfos;
import de.qaware.smartlab.assistance.annotation.EnableSmartLabAssistanceInfos;
import de.qaware.smartlab.core.annotation.EnableSmartLabCore;
import de.qaware.smartlab.core.data.IDataSetProvider;
import de.qaware.smartlab.core.data.actuator.IActuator;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.person.IPerson;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.data.conversion.annotation.EnableSmartLabDtoConverters;
import de.qaware.smartlab.data.set.provider.DataSetProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Configuration
@ComponentScan(basePackageClasses = de.qaware.smartlab.data.set.ComponentScanMarker.class)
@EnableSmartLabCore
@EnableSmartLabDtoConverters
@EnableSmartLabAssistanceInfos
@EnableSmartLabActionInfos
@EnableConfigurationProperties(InitialDataConfiguration.Properties.class)
public class InitialDataConfiguration {

    private final Properties properties;
    private final DataSetProvider.Factory dataSetProviderFactory;
    private IDataSetProvider dataSetProvider;

    public InitialDataConfiguration(
            Properties properties,
            DataSetProvider.Factory dataSetProviderFactory) {
        this.properties = properties;
        this.dataSetProviderFactory = dataSetProviderFactory;
    }

    @PostConstruct
    private void createDataSetProvider() {
        this.dataSetProvider = dataSetProviderFactory.of(
                properties.getInitialEvents(),
                properties.getInitialLocations(),
                properties.getInitialActuators(),
                properties.getInitialWorkgroups(),
                properties.getInitialPersons());
    }

    @Bean
    public Set<IEvent> initialEvents() {
        return this.dataSetProvider.getEvents();
    }

    @Bean
    public Set<IWorkgroup> initialWorkgroups() {
        return this.dataSetProvider.getWorkgroups();
    }

    @Bean
    public Set<IPerson> initialPersons() {
        return this.dataSetProvider.getWorkgroupMembers();
    }

    @Bean
    public Set<ILocation> initialLocations() {
        return this.dataSetProvider.getLocations();
    }

    @Bean
    public Set<IActuator> initialActuators() {
        return this.dataSetProvider.getActuators();
    }

    @ConfigurationProperties(prefix = Properties.PREFIX, ignoreInvalidFields = true)
    public static class Properties {

        private static final String PREFIX = "smart-lab.app.data";
        private static final List<String> DEFAULT_INITIAL_EVENTS = Collections.emptyList();
        private static final List<String> DEFAULT_INITIAL_LOCATIONS = Collections.emptyList();
        private static final List<String> DEFAULT_INITIAL_ACTUATORS = Collections.emptyList();
        private static final List<String> DEFAULT_INITIAL_WORKGROUPS = Collections.emptyList();
        private static final List<String> DEFAULT_INITIAL_PERSONS = Collections.emptyList();

        private List<String> initialEvents;
        private List<String> initialLocations;
        private List<String> initialActuators;
        private List<String> initialWorkgroups;
        private List<String> initialPersons;

        public Properties() {
            this.initialEvents = DEFAULT_INITIAL_EVENTS;
            this.initialLocations = DEFAULT_INITIAL_LOCATIONS;
            this.initialActuators = DEFAULT_INITIAL_ACTUATORS;
            this.initialWorkgroups = DEFAULT_INITIAL_WORKGROUPS;
            this.initialPersons = DEFAULT_INITIAL_PERSONS;
        }

        public List<String> getInitialEvents() {
            return initialEvents;
        }

        public void setInitialEvents(List<String> initialEvents) {
            this.initialEvents = initialEvents;
        }

        public List<String> getInitialLocations() {
            return initialLocations;
        }

        public void setInitialLocations(List<String> initialLocations) {
            this.initialLocations = initialLocations;
        }

        public List<String> getInitialActuators() {
            return initialActuators;
        }

        public void setInitialActuators(List<String> initialActuators) {
            this.initialActuators = initialActuators;
        }

        public List<String> getInitialWorkgroups() {
            return initialWorkgroups;
        }

        public void setInitialWorkgroups(List<String> initialWorkgroups) {
            this.initialWorkgroups = initialWorkgroups;
        }

        public List<String> getInitialPersons() {
            return initialPersons;
        }

        public void setInitialPersons(List<String> initialPersons) {
            this.initialPersons = initialPersons;
        }
    }
}
