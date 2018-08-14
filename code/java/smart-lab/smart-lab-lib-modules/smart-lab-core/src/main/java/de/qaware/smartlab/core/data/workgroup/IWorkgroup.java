package de.qaware.smartlab.core.data.workgroup;

import de.qaware.smartlab.core.data.generic.IEntity;
import de.qaware.smartlab.core.data.person.PersonId;

import java.util.Set;

public interface IWorkgroup extends IEntity<WorkgroupId> {

    String getName();
    Set<PersonId> getMemberIds();
    IProjectBaseInfo getProjectBaseInfo();
}
