package de.qaware.smartlab.core.data.workgroup;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlab.core.data.generic.IEntity;
import de.qaware.smartlab.core.data.person.PersonId;
import de.qaware.smartlab.core.miscellaneous.Constants;

import java.util.Set;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = Constants.JSON_TYPE_PROPERTY_NAME)
public interface IWorkgroup extends IEntity<WorkgroupId> {

    String getName();
    Set<PersonId> getMemberIds();
    IProjectBaseInfo getProjectBaseInfo();
}
