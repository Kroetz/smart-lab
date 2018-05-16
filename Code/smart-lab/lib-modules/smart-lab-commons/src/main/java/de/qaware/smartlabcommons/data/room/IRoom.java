package de.qaware.smartlabcommons.data.room;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlabcommons.miscellaneous.Constants;
import de.qaware.smartlabcommons.data.generic.IEntity;
import de.qaware.smartlabcommons.data.device.entity.IDevice;

import java.util.Collection;
import java.util.Optional;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = Constants.JSON_TYPE_PROPERTY_NAME)
public interface IRoom extends IEntity {

    String getName();
    Collection<String> getDeviceIds();
    Optional<IDevice> getMinuteTakingDevice();      // TODO: Still needed?
}
