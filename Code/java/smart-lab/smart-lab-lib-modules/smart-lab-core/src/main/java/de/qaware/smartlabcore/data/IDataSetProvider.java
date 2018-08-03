package de.qaware.smartlabcore.data;

import de.qaware.smartlabcore.data.device.Device;
import de.qaware.smartlabcore.data.location.Location;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.data.meeting.Meeting;
import de.qaware.smartlabcore.data.person.Person;
import de.qaware.smartlabcore.data.workgroup.Workgroup;
import de.qaware.smartlabcore.exception.DataSetException;

import java.util.Map;
import java.util.Set;

public interface IDataSetProvider {

    Set<Meeting> getMeetings() throws DataSetException ;
    Map<LocationId, Set<Meeting>> getMeetingsByLocation() throws DataSetException ;
    Set<Location> getLocations() throws DataSetException ;
    Set<Device> getDevices() throws DataSetException ;
    Set<Workgroup> getWorkgroups() throws DataSetException;
    Set<Person> getWorkgroupMembers() throws DataSetException ;
}
