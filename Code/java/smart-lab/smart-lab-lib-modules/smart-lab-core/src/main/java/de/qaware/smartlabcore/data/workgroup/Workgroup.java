package de.qaware.smartlabcore.data.workgroup;

import de.qaware.smartlabcore.data.person.PersonId;
import de.qaware.smartlabcore.data.workgroup.IKnowledgeBaseInfo;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Workgroup implements IWorkgroup {

    private WorkgroupId id;
    private String name;
    private Set<PersonId> memberIds;
    private IKnowledgeBaseInfo knowledgeBaseInfo;
}
