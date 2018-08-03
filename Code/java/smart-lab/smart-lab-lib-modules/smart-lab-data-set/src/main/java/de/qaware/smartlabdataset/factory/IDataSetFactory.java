package de.qaware.smartlabdataset.factory;

import de.qaware.smartlabcore.data.device.entity.Device;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.location.Location;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.data.meeting.Meeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.person.Person;
import de.qaware.smartlabcore.data.person.PersonId;
import de.qaware.smartlabcore.data.workgroup.Workgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.exception.DataSetException;

import java.util.Map;
import java.util.Set;

public interface IDataSetFactory {

    String getId();
    Set<Meeting> createMeetingSet() throws DataSetException;
    Map<MeetingId, Meeting> createMeetingMap() throws DataSetException ;
    Set<Location> createLocationSet() throws DataSetException ;
    Map<LocationId, Location> createLocationMap() throws DataSetException ;
    Set<Device> createDeviceSet() throws DataSetException ;
    Map<DeviceId, Device> createDeviceMap() throws DataSetException ;
    Set<Workgroup> createWorkgroupSet() throws DataSetException ;
    Map<WorkgroupId, Workgroup> createWorkgroupMap() throws DataSetException ;
    Set<Person> createWorkgroupMemberSet() throws DataSetException ;
    Map<PersonId, Person> createWorkgroupMemberMap() throws DataSetException ;
}
