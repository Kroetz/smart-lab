package de.qaware.smartlabcommons.data.device;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AcmeDisplay.class),
        @JsonSubTypes.Type(value = AcmeMicrophone.class),
})
public interface IDevice {

    long getId();
    String getName();
    DeviceRole getRole();
}
