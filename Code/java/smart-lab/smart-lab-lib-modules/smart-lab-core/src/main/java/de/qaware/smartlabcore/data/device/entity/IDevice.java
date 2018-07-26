package de.qaware.smartlabcore.data.device.entity;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlabcore.miscellaneous.Constants;
import de.qaware.smartlabcore.data.generic.IEntity;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = Constants.JSON_TYPE_PROPERTY_NAME)
public interface IDevice extends IEntity<DeviceId> {

    String getType();
    String getName();
    String getResponsibleDelegate();
}
