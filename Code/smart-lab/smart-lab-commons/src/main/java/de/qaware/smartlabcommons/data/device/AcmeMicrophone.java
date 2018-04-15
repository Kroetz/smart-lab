package de.qaware.smartlabcommons.data.device;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class AcmeMicrophone extends AbstractDevice implements IMicrophone {

    private String dummyMicrophoneProperty;

    @Builder
    @JsonCreator
    public AcmeMicrophone(@JsonProperty("id") long id, @JsonProperty("name") String name, @JsonProperty("role") DeviceRole role, @JsonProperty("dummyMicrophoneProperty") String dummyMicrophoneProperty){
        super(id, name, role);
        this.dummyMicrophoneProperty = dummyMicrophoneProperty;
    }
}
