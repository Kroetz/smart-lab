package de.qaware.smartlabdata.converter.person;

import de.qaware.smartlabcore.data.generic.IDtoConverter;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.Person;
import de.qaware.smartlabcore.data.person.PersonDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PersonConverter implements IDtoConverter<IPerson, PersonDto> {

    /*
     * TODO: Map fields automatically without breaking the immutability of the entity classes
     * Maybe http://modelmapper.org/ is appropriate for this task
     */

    @Override
    public PersonDto toDto(IPerson person) {
        return PersonDto.builder()
                .id(person.getId())
                .name(person.getName())
                .email(person.getEmail())
                .role(person.getRole())
                .build();
    }

    @Override
    public IPerson toEntity(PersonDto person) {
        return Person.of(
                person.getId(),
                person.getName(),
                person.getEmail(),
                person.getRole());
    }
}
