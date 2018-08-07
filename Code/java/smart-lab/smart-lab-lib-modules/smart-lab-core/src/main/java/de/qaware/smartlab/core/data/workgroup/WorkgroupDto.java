package de.qaware.smartlab.core.data.workgroup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.qaware.smartlab.core.data.generic.IDto;
import de.qaware.smartlab.core.data.person.PersonId;
import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkgroupDto implements IDto {

    private WorkgroupId id;
    private String name;
    private Set<PersonId> memberIds;
    private ProjectBaseInfoDto projectBaseInfo;
}
