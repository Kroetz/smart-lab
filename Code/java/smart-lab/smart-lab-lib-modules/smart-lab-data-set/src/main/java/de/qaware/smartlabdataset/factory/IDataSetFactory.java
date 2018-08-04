package de.qaware.smartlabdataset.factory;

import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.location.ILocation;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.PersonId;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.exception.DataSetException;

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
