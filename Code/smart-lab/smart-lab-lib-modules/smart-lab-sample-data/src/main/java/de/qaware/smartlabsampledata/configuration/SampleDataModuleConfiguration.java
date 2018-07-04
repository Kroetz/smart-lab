package de.qaware.smartlabsampledata.configuration;

import de.qaware.smartlabaction.annotation.EnableSmartLabActionInfos;
import de.qaware.smartlabassistance.annotation.EnableSmartLabAssistanceInfos;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import de.qaware.smartlabcore.data.ISampleDataProvider;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@ComponentScan(basePackageClasses = de.qaware.smartlabsampledata.ComponentScanMarker.class)
@EnableSmartLabCore
@EnableSmartLabAssistanceInfos
@EnableSmartLabActionInfos
public class SampleDataModuleConfiguration {

    private final ISampleDataProvider sampleDataProvider;

    public SampleDataModuleConfiguration(ISampleDataProvider sampleDataProvider) {
        this.sampleDataProvider = sampleDataProvider;
    }

    @Bean
    public Set<IMeeting> initialMeetings() {
        return this.sampleDataProvider.getMeetings();
    }

    @Bean
    public Set<IWorkgroup> initialWorkgroups() {
        return this.sampleDataProvider.getWorkgroups();
    }

    @Bean
    public Set<IPerson> initialPersons() {
        return this.sampleDataProvider.getWorkgroupMembers();
    }

    @Bean
    public Set<IRoom> initialRooms() {
        return this.sampleDataProvider.getRooms();
    }

    @Bean
    public Set<IDevice> initialDevices() {
        return this.sampleDataProvider.getDevices();
    }
}
