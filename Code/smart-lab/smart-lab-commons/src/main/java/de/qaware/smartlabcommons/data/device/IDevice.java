package de.qaware.smartlabcommons.data.device;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlabcommons.data.IEntity;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AcmeDisplay.class),
        @JsonSubTypes.Type(value = AcmeMicrophone.class),
})
public interface IDevice extends IEntity {

    String getName();
    DeviceRole getRole();
}
