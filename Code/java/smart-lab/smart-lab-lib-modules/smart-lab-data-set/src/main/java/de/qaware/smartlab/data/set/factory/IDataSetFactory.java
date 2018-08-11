package de.qaware.smartlab.data.set.factory;

import de.qaware.smartlab.core.data.actuator.ActuatorId;
import de.qaware.smartlab.core.data.actuator.IActuator;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.event.EventId;
import de.qaware.smartlab.core.data.person.IPerson;
import de.qaware.smartlab.core.data.person.PersonId;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.exception.data.DataSetException;

import java.util.Map;
import java.util.Set;

public interface IDataSetFactory {

    String getId();
    Set<IEvent> createEventSet() throws DataSetException;
    Map<EventId, IEvent> createEventMap() throws DataSetException ;
    Set<ILocation> createLocationSet() throws DataSetException ;
    Map<LocationId, ILocation> createLocationMap() throws DataSetException ;
    Set<IActuator> createActuatorSet() throws DataSetException ;
    Map<ActuatorId, IActuator> createActuatorMap() throws DataSetException ;
    Set<IWorkgroup> createWorkgroupSet() throws DataSetException ;
    Map<WorkgroupId, IWorkgroup> createWorkgroupMap() throws DataSetException ;
    Set<IPerson> createWorkgroupMemberSet() throws DataSetException ;
    Map<PersonId, IPerson> createWorkgroupMemberMap() throws DataSetException ;
}
