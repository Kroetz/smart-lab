package de.qaware.smartlablocation.service.business;

import de.qaware.smartlabcore.data.location.ILocation;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.result.ExtensionResult;
import de.qaware.smartlabcore.service.business.IBasicEntityManagementBusinessLogic;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface ILocationManagementBusinessLogic extends IBasicEntityManagementBusinessLogic<ILocation, LocationId> {

    Optional<Set<IMeeting>> getMeetingsAtLocation(LocationId locationId);
    Optional<IMeeting> getCurrentMeeting(LocationId locationId);
    ExtensionResult extendCurrentMeeting(LocationId locationId, Duration extension);
}
