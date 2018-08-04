package de.qaware.smartlabcore.data.workgroup;

import de.qaware.smartlabcore.data.person.PersonId;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@EqualsAndHashCode
@Slf4j
public class Workgroup implements IWorkgroup {

    private final WorkgroupId id;
    private final String name;
    private final Set<PersonId> memberIds;
    private final IProjectBaseInfo projectBaseInfo;

    public static IWorkgroup of(
            WorkgroupId id,
            String name,
            Set<PersonId> memberIds,
            IProjectBaseInfo projectBaseInfo) {
        return new Workgroup(id, name, memberIds, projectBaseInfo);
    }
}
