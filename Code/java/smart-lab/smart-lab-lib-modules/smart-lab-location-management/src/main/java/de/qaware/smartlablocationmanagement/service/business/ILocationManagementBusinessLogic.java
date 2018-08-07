package de.qaware.smartlablocationmanagement.service.business;

import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.meeting.IMeeting;
import de.qaware.smartlab.core.result.ExtensionResult;
import de.qaware.smartlab.core.service.business.IBasicEntityManagementBusinessLogic;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface ILocationManagementBusinessLogic extends IBasicEntityManagementBusinessLogic<ILocation, LocationId> {

    Optional<Set<IMeeting>> getMeetingsAtLocation(LocationId locationId);
    Optional<IMeeting> getCurrentMeeting(LocationId locationId);
    ExtensionResult extendCurrentMeeting(LocationId locationId, Duration extension);
}
