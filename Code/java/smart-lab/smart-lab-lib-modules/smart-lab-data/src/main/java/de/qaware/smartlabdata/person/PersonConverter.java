package de.qaware.smartlabdata.person;

import de.qaware.smartlabcore.data.generic.IDtoConverter;
import de.qaware.smartlabcore.data.person.IPerson;
import de.qaware.smartlabcore.data.person.Person;
import de.qaware.smartlabcore.data.person.PersonDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PersonConverter implements IDtoConverter<IPerson, PersonDto> {

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
        return Person.builder()
                .id(person.getId())
                .name(person.getName())
                .email(person.getEmail())
                .role(person.getRole())
                .build();
    }
}
