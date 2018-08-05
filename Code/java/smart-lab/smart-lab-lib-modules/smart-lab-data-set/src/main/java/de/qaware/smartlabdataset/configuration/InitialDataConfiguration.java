package de.qaware.smartlabdataset.configuration;

import de.qaware.smartlabaction.annotation.EnableSmartLabActionInfos;
import de.qaware.smartlabassistance.annotation.EnableSmartLabAssistanceInfos;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import de.qaware.smartlabcore.data.IDataSetProvider;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.location.ILocation;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabdataconversion.annotation.EnableSmartLabDtoConverters;
import de.qaware.smartlabdataset.ComponentScanMarker;
import de.qaware.smartlabdataset.provider.DataSetProvider;
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
@ComponentScan(basePackageClasses = ComponentScanMarker.class)
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
                properties.getInitialMeetings(),
                properties.getInitialLocations(),
                properties.getInitialDevices(),
                properties.getInitialWorkgroups(),
                properties.getInitialPersons());
    }

    @Bean
    public Set<IMeeting> initialMeetings() {
        return this.dataSetProvider.getMeetings();
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
    public Set<IDevice> initialDevices() {
        return this.dataSetProvider.getDevices();
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "smart-lab.app.data", ignoreInvalidFields = true)
    public static class Properties {

        private static final List<String> DEFAULT_INITIAL_MEETINGS = Collections.emptyList();
        private static final List<String> DEFAULT_INITIAL_LOCATIONS = Collections.emptyList();
        private static final List<String> DEFAULT_INITIAL_DEVICES = Collections.emptyList();
        private static final List<String> DEFAULT_INITIAL_WORKGROUPS = Collections.emptyList();
        private static final List<String> DEFAULT_INITIAL_PERSONS = Collections.emptyList();

        private List<String> initialMeetings;
        private List<String> initialLocations;
        private List<String> initialDevices;
        private List<String> initialWorkgroups;
        private List<String> initialPersons;

        public Properties() {
            this.initialMeetings = DEFAULT_INITIAL_MEETINGS;
            this.initialLocations = DEFAULT_INITIAL_LOCATIONS;
            this.initialDevices = DEFAULT_INITIAL_DEVICES;
            this.initialWorkgroups = DEFAULT_INITIAL_WORKGROUPS;
            this.initialPersons = DEFAULT_INITIAL_PERSONS;
        }

        public List<String> getInitialMeetings() {
            return initialMeetings;
        }

        public void setInitialMeetings(List<String> initialMeetings) {
            this.initialMeetings = initialMeetings;
        }

        public List<String> getInitialLocations() {
            return initialLocations;
        }

        public void setInitialLocations(List<String> initialLocations) {
            this.initialLocations = initialLocations;
        }

        public List<String> getInitialDevices() {
            return initialDevices;
        }

        public void setInitialDevices(List<String> initialDevices) {
            this.initialDevices = initialDevices;
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
