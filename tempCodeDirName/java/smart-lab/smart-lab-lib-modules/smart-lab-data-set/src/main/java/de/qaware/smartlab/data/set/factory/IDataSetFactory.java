package de.qaware.smartlab.data.set.factory;

import de.qaware.smartlab.core.data.actuator.ActuatorId;
import de.qaware.smartlab.core.data.actuator.IActuator;
import de.qaware.smartlab.core.data.event.EventId;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.person.IPerson;
import de.qaware.smartlab.core.data.person.PersonId;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.exception.data.DataException;

import java.util.Map;
import java.util.Set;

public interface IDataSetFactory {

    String getId();
    Set<IEvent> createEventSet() throws DataException;
    Map<EventId, IEvent> createEventMap() throws DataException ;
    Set<ILocation> createLocationSet() throws DataException ;
    Map<LocationId, ILocation> createLocationMap() throws DataException ;
    Set<IActuator> createActuatorSet() throws DataException ;
    Map<ActuatorId, IActuator> createActuatorMap() throws DataException ;
    Set<IWorkgroup> createWorkgroupSet() throws DataException ;
    Map<WorkgroupId, IWorkgroup> createWorkgroupMap() throws DataException ;
    Set<IPerson> createWorkgroupMemberSet() throws DataException ;
    Map<PersonId, IPerson> createWorkgroupMemberMap() throws DataException ;
}
