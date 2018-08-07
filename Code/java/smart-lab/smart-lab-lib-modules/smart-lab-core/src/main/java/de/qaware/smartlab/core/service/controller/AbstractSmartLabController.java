package de.qaware.smartlab.core.service.controller;

import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.data.generic.IDto;
import de.qaware.smartlab.core.data.generic.IEntity;
import de.qaware.smartlab.core.data.generic.IIdentifier;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public abstract class AbstractSmartLabController {

    protected <T> ResponseEntity<T> responseFromOptional(Optional<T> optional) {
        return optional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    protected <
            EntityT extends IEntity<IdentifierT>,
            IdentifierT extends IIdentifier,
            DtoT extends IDto> ResponseEntity<DtoT> responseFromEntityOptional(
                    Optional<EntityT> entityOptional,
                    IDtoConverter<EntityT, DtoT> converter) {
        return entityOptional
                .map(entity -> ResponseEntity.ok(converter.toDto(entity)))
                .orElse(ResponseEntity.notFound().build());
    }

    protected <
            EntityT extends IEntity<IdentifierT>,
            IdentifierT extends IIdentifier,
            DtoT extends IDto> ResponseEntity<Set<DtoT>> responseFromEntityOptionals(
                    Optional<Set<EntityT>> entitiesOptional,
                    IDtoConverter<EntityT, DtoT> converter) {
        return entitiesOptional
                .map(entities -> ResponseEntity.ok(entities.stream()
                        .map(converter::toDto)
                        .collect(toSet())))
                .orElse(ResponseEntity.notFound().build());
    }

    protected <
            EntityT extends IEntity<IdentifierT>,
            IdentifierT extends IIdentifier,
            DtoT extends IDto> ResponseEntity<Set<DtoT>> responseFromEntityOptionals(
                    Map<IdentifierT, Optional<EntityT>> entityOptionalsById,
                    IDtoConverter<EntityT, DtoT> converter) {
        Collection<Optional<EntityT>> entityOptionals = entityOptionalsById.values();
        if(entityOptionals.stream().anyMatch(o -> !o.isPresent())) {
            return ResponseEntity.notFound().build();
        }
        // TODO: Would be easier with Java 9 (see http://www.baeldung.com/java-filter-stream-of-optional)
        return ResponseEntity.ok(entityOptionals.stream()
                .flatMap(entityOptional -> entityOptional.map(Stream::of).orElseGet(Stream::empty))
                .map(converter::toDto)
                .collect(toSet()));
    }
}
