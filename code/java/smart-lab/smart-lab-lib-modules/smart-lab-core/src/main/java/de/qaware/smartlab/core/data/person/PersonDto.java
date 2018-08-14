package de.qaware.smartlab.core.data.person;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.qaware.smartlab.core.data.generic.IDto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonDto implements IDto {

    private PersonId id;
    private String name;
    private String email;
    private PersonRole role;
}
