package de.qaware.smartlabcore.data;

import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.location.ILocation;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.exception.DataSetException;

import java.util.Map;
import java.util.Set;

public interface IDataSetProvider {

    Set<IMeeting> getMeetings() throws DataSetException ;
    Map<LocationId, Set<IMeeting>> getMeetingsByLocation() throws DataSetException ;
    Set<ILocation> getLocations() throws DataSetException ;
    Set<IDevice> getDevices() throws DataSetException ;
    Set<IWorkgroup> getWorkgroups() throws DataSetException;
    Set<IPerson> getWorkgroupMembers() throws DataSetException ;
}
