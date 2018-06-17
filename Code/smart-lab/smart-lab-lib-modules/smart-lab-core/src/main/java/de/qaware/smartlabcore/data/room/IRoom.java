package de.qaware.smartlabcore.data.room;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.miscellaneous.Constants;
import de.qaware.smartlabcore.data.generic.IEntity;
import de.qaware.smartlabcore.data.device.entity.IDevice;

import java.util.Collection;
import java.util.Optional;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = Constants.JSON_TYPE_PROPERTY_NAME)
public interface IRoom extends IEntity<RoomId> {

    String getName();
    Collection<DeviceId> getDeviceIds();        // TODO: Set?
    Optional<IDevice> getMinuteTakingDevice();      // TODO: Still needed?
}
