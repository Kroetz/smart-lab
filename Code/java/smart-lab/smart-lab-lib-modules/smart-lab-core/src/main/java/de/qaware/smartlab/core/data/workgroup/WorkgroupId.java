package de.qaware.smartlab.core.data.workgroup;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.qaware.smartlab.core.data.generic.AbstractIdentifier;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class WorkgroupId extends AbstractIdentifier {

    private WorkgroupId(String idValue) {
        super(idValue);
    }

    @JsonCreator
    public static WorkgroupId of(@JsonProperty(ID_VALUE_FIELD_NAME) String idValue) {
        return new WorkgroupId(idValue);
    }
}
