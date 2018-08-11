package de.qaware.smartlab.core.data.action.generic;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlab.core.constant.Miscellaneous;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = Miscellaneous.JSON_TYPE_PROPERTY_NAME)
public interface IActionArgs { }
