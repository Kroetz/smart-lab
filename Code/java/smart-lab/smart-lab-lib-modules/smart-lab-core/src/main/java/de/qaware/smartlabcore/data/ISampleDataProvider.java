package de.qaware.smartlabcore.data;

import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.location.ILocation;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;

import java.util.Map;
import java.util.Set;

public interface ISampleDataProvider {

    Set<IWorkgroup> getWorkgroups();
    Set<IPerson> getWorkgroupMembers();
    Set<IMeeting> getMeetings();
    Map<LocationId, Set<IMeeting>> getMeetingsByLocation();
    Set<ILocation> getLocations();
    Set<IDevice> getDevices();
}
