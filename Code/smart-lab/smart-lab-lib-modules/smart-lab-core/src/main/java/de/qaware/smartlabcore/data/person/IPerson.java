package de.qaware.smartlabcore.data.person;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlabcore.miscellaneous.Constants;
import de.qaware.smartlabcore.data.generic.IEntity;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = Constants.JSON_TYPE_PROPERTY_NAME)
public interface IPerson extends IEntity {

    String getName();
    String getEmail();
    PersonRole getRole();
}
