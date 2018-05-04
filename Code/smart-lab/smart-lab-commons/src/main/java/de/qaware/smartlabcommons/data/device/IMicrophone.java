package de.qaware.smartlabcommons.data.device;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlabcommons.miscellaneous.Constants;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = Constants.JSON_TYPE_PROPERTY_NAME)
public interface IMicrophone extends IDevice {
}
