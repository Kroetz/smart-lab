package de.qaware.smartlabcore.data.meeting;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.qaware.smartlabcore.data.generic.AbstractIdentifier;

public class MeetingId extends AbstractIdentifier {

    private MeetingId(String idValue) {
        super(idValue);
    }

    @JsonCreator
    public static MeetingId of(@JsonProperty(ID_VALUE_FIELD_NAME) String idValue) {
        return new MeetingId(idValue);
    }
}
