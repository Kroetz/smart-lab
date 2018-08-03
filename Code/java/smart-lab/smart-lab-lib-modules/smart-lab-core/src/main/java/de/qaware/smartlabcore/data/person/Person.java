package de.qaware.smartlabcore.data.person;

import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.PersonId;
import de.qaware.smartlabcore.data.person.PersonRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person implements IPerson {

    private PersonId id;
    private String name;
    private String email;
    private PersonRole role;
}
