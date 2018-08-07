package de.qaware.smartlab.core.data.location;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlab.core.data.device.DeviceId;
import de.qaware.smartlab.core.data.generic.IEntity;
import de.qaware.smartlab.core.miscellaneous.Constants;

import java.util.Collection;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = Constants.JSON_TYPE_PROPERTY_NAME)
public interface ILocation extends IEntity<LocationId> {

    String getName();
    Collection<DeviceId> getDeviceIds();        // TODO: Set?
}
