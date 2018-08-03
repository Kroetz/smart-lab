package de.qaware.smartlabcore.data.person.dto;

import de.qaware.smartlabcore.data.generic.IDto;
import de.qaware.smartlabcore.data.person.PersonId;
import de.qaware.smartlabcore.data.person.PersonRole;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PersonDto implements IDto {

    private PersonId id;
    private String name;
    private String email;
    private PersonRole role;
}
