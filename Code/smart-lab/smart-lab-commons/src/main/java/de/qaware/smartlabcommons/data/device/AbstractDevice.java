package de.qaware.smartlabcommons.data.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class AbstractDevice implements IDevice {

    // Setting this field manually is needed due to a Jackson bug with Java Optionals (see https://stackoverflow.com/questions/49071166/jackson-java-util-optional-serialization-does-not-include-type-id)
    @JsonProperty
    private String type = this.getClass().getName();

    private long id;
    private String name;
    private DeviceRole role;

    public AbstractDevice(long id, String name, DeviceRole role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }
}
