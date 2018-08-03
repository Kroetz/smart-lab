package de.qaware.smartlabcore.data.location;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.generic.IEntity;
import de.qaware.smartlabcore.miscellaneous.Constants;

import java.util.Collection;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = Constants.JSON_TYPE_PROPERTY_NAME)
public interface ILocation extends IEntity<LocationId> {

    String getName();
    Collection<DeviceId> getDeviceIds();        // TODO: Set?
}
