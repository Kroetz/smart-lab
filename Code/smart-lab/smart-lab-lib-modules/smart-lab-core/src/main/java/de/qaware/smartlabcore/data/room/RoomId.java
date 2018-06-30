package de.qaware.smartlabcore.data.room;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.qaware.smartlabcore.data.generic.AbstractIdentifier;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public class RoomId extends AbstractIdentifier {

    private RoomId(String idValue) {
        super(idValue);
    }

    @JsonCreator
    public static RoomId of(@JsonProperty(ID_VALUE_FIELD_NAME) String idValue) {
        return new RoomId(idValue);
    }
}
