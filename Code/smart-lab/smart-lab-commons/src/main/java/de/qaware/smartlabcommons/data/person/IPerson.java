package de.qaware.smartlabcommons.data.person;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlabcommons.data.IEntity;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
public interface IPerson extends IEntity {

    String getName();
    String getEmail();
    PersonRole getRole();
}
