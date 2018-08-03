package de.qaware.smartlabapi.service.connector.location;

import de.qaware.smartlabapi.service.connector.generic.IBasicEntityManagementService;
import de.qaware.smartlabcore.data.location.ILocation;
import de.qaware.smartlabcore.data.location.LocationDto;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.data.meeting.IMeeting;

import java.time.Duration;
import java.util.Set;

public interface ILocationManagementService extends IBasicEntityManagementService<ILocation, LocationId, LocationDto> {

    Set<IMeeting> getMeetingsAtLocation(LocationId locationId);
    IMeeting getCurrentMeeting(LocationId locationId);
    void extendCurrentMeeting(LocationId locationId, Duration extension);
}
