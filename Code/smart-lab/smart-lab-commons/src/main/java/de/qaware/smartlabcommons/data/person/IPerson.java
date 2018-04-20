package de.qaware.smartlabcommons.data.person;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Person.class)
})
public interface IPerson {

    String getId();
    String getName();
    String getEmail();
    PersonRole getRole();
}
