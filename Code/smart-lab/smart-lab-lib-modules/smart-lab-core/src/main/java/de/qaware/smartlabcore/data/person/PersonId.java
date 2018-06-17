package de.qaware.smartlabcore.data.person;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.qaware.smartlabcore.data.generic.AbstractIdentifier;

public class PersonId extends AbstractIdentifier {

    private PersonId(String idValue) {
        super(idValue);
    }

    @JsonCreator
    public static PersonId of(@JsonProperty(ID_VALUE_FIELD_NAME) String idValue) {
        return new PersonId(idValue);
    }
}
