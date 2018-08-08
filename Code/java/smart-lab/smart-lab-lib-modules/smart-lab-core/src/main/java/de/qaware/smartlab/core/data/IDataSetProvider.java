package de.qaware.smartlab.core.data;

import de.qaware.smartlab.core.data.actuator.IActuator;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.person.IPerson;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.exception.DataSetException;

import java.util.Map;
import java.util.Set;

public interface IDataSetProvider {

    Set<IEvent> getEvents() throws DataSetException ;
    Map<LocationId, Set<IEvent>> getEventsByLocation() throws DataSetException ;
    Set<ILocation> getLocations() throws DataSetException ;
    Set<IActuator> getActuators() throws DataSetException ;
    Set<IWorkgroup> getWorkgroups() throws DataSetException;
    Set<IPerson> getWorkgroupMembers() throws DataSetException ;
}
