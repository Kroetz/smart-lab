package de.qaware.smartlabcore.data.workgroup;

import de.qaware.smartlabcore.data.generic.AbstractIdentifier;

public class WorkgroupId extends AbstractIdentifier {

    private WorkgroupId(String idValue) {
        super(idValue);
    }

    public static WorkgroupId of(String idValue) {
        return new WorkgroupId(idValue);
    }
}
