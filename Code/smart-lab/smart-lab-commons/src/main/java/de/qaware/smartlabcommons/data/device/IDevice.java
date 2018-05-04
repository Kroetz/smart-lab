package de.qaware.smartlabcommons.data.device;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlabcommons.miscellaneous.Constants;
import de.qaware.smartlabcommons.data.generic.IEntity;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = Constants.JSON_TYPE_PROPERTY_NAME)
public interface IDevice extends IEntity {

    String getName();
    DeviceRole getRole();
}
