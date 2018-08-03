package de.qaware.smartlabcore.data.person;

import de.qaware.smartlabcore.data.generic.IDto;
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
