package de.qaware.smartlabcore.data.workgroup;

import de.qaware.smartlabcore.data.generic.IDto;
import de.qaware.smartlabcore.data.person.PersonId;
import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class WorkgroupDto implements IDto {

    private WorkgroupId id;
    private String name;
    private Set<PersonId> memberIds;
    private ProjectBaseInfoDto projectBaseInfo;
}
