package de.qaware.smartlab.core.data.person;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@EqualsAndHashCode
@Slf4j
public class Person implements IPerson {

    private final PersonId id;
    private final String name;
    private final String email;
    private final PersonRole role;

    public static Person of(PersonId id, String name, String email, PersonRole role) {
        return new Person(id, name, email, role);
    }
}
