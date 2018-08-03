package de.qaware.smartlabcore.data.person;

import de.qaware.smartlabcore.data.generic.IDtoConverter;
import de.qaware.smartlabcore.data.person.dto.PersonDto;
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
