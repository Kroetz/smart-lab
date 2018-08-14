package de.qaware.smartlab.core.data.location;

import de.qaware.smartlab.core.data.actuator.ActuatorId;
import de.qaware.smartlab.core.data.generic.IEntity;

import java.util.Collection;

public interface ILocation extends IEntity<LocationId> {

    String getName();
    Collection<ActuatorId> getActuatorIds();
}
