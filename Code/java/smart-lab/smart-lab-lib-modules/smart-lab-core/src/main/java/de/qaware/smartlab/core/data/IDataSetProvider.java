package de.qaware.smartlab.core.data;

import de.qaware.smartlab.core.data.actuator.IActuator;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.person.IPerson;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.exception.data.DataException;

import java.util.Map;
import java.util.Set;

public interface IDataSetProvider {

    Set<IEvent> getEvents() throws DataException;
    Map<LocationId, Set<IEvent>> getEventsByLocation() throws DataException ;
    Set<ILocation> getLocations() throws DataException ;
    Set<IActuator> getActuators() throws DataException ;
    Set<IWorkgroup> getWorkgroups() throws DataException;
    Set<IPerson> getWorkgroupMembers() throws DataException ;
}
