package de.qaware.smartlab.api.service.connector.location;

import de.qaware.smartlab.api.service.connector.generic.IBasicEntityManagementService;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.location.LocationDto;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.meeting.IMeeting;

import java.time.Duration;
import java.util.Set;

public interface ILocationManagementService extends IBasicEntityManagementService<ILocation, LocationId, LocationDto> {

    Set<IMeeting> getMeetingsAtLocation(LocationId locationId);
    IMeeting getCurrentMeeting(LocationId locationId);
    void extendCurrentMeeting(LocationId locationId, Duration extension);
}
