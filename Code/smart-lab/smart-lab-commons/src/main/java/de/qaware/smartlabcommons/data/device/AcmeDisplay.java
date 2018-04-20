package de.qaware.smartlabcommons.data.device;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class AcmeDisplay extends AbstractDevice implements IDisplay {

    private String dummyDisplayProperty;

    @Builder
    @JsonCreator
    public AcmeDisplay(@JsonProperty("id") String id, @JsonProperty("name") String name, @JsonProperty("role") DeviceRole role, @JsonProperty("dummyDisplayProperty") String dummyDisplayProperty){
        super(id, name, role);
        this.dummyDisplayProperty = dummyDisplayProperty;
    }
}
