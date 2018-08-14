package de.qaware.smartlab.core.data.actuator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.qaware.smartlab.core.data.generic.AbstractIdentifier;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ActuatorId extends AbstractIdentifier {

    private ActuatorId(String idValue) {
        super(idValue);
    }

    @JsonCreator
    public static ActuatorId of(@JsonProperty(ID_VALUE_FIELD_NAME) String idValue) {
        return new ActuatorId(idValue);
    }
}
