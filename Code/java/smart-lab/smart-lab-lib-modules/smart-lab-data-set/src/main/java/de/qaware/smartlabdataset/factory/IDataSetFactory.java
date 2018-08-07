package de.qaware.smartlabdataset.factory;

import de.qaware.smartlab.core.data.device.DeviceId;
import de.qaware.smartlab.core.data.device.IDevice;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.meeting.IMeeting;
import de.qaware.smartlab.core.data.meeting.MeetingId;
import de.qaware.smartlab.core.data.person.IPerson;
import de.qaware.smartlab.core.data.person.PersonId;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.exception.DataSetException;

import java.util.Map;
import java.util.Set;

public interface IDataSetFactory {

    String getId();
    Set<IMeeting> createMeetingSet() throws DataSetException;
    Map<MeetingId, IMeeting> createMeetingMap() throws DataSetException ;
    Set<ILocation> createLocationSet() throws DataSetException ;
    Map<LocationId, ILocation> createLocationMap() throws DataSetException ;
    Set<IDevice> createDeviceSet() throws DataSetException ;
    Map<DeviceId, IDevice> createDeviceMap() throws DataSetException ;
    Set<IWorkgroup> createWorkgroupSet() throws DataSetException ;
    Map<WorkgroupId, IWorkgroup> createWorkgroupMap() throws DataSetException ;
    Set<IPerson> createWorkgroupMemberSet() throws DataSetException ;
    Map<PersonId, IPerson> createWorkgroupMemberMap() throws DataSetException ;
}
