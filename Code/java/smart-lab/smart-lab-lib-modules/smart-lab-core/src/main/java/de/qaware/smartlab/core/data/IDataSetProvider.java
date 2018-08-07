package de.qaware.smartlab.core.data;

import de.qaware.smartlab.core.data.device.IDevice;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.meeting.IMeeting;
import de.qaware.smartlab.core.data.person.IPerson;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.exception.DataSetException;

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
