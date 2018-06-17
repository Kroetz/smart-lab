package de.qaware.smartlabcore.data.workgroup;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.qaware.smartlabcore.data.generic.AbstractIdentifier;

public class WorkgroupId extends AbstractIdentifier {

    private WorkgroupId(String idValue) {
        super(idValue);
    }

    @JsonCreator
    public static WorkgroupId of(@JsonProperty(ID_VALUE_FIELD_NAME) String idValue) {
        return new WorkgroupId(idValue);
    }
}
