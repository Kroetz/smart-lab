package de.qaware.smartlabcommons.data.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person implements IPerson {

    private long id;
    private String name;
    private String email;
    private PersonRole role;
}
