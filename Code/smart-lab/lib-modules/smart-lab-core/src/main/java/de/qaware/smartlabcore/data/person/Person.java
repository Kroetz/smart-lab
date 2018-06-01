package de.qaware.smartlabcore.data.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person implements IPerson {

    private String id;
    private String name;
    private String email;
    private PersonRole role;
}
