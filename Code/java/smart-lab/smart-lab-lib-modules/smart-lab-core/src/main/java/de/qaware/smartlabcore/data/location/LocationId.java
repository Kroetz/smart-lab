package de.qaware.smartlabcore.data.location;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.qaware.smartlabcore.data.generic.AbstractIdentifier;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LocationId extends AbstractIdentifier {

    private LocationId(String idValue) {
        super(idValue);
    }

    @JsonCreator
    public static LocationId of(@JsonProperty(ID_VALUE_FIELD_NAME) String idValue) {
        return new LocationId(idValue);
    }
}
