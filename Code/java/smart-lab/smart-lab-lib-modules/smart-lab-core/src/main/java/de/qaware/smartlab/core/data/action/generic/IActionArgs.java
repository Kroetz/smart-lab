package de.qaware.smartlab.core.data.action.generic;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlab.core.constant.Constants;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = Constants.JSON_TYPE_PROPERTY_NAME)
public interface IActionArgs { }
