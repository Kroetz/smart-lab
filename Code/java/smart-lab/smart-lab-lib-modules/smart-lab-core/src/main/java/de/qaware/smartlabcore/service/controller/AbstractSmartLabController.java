package de.qaware.smartlabcore.service.controller;

import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public abstract class AbstractSmartLabController {

    protected <T> ResponseEntity<T> responseFromOptional(Optional<T> entityOptional) {
        return entityOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    protected <T, IdentifierT> ResponseEntity<Set<T>> responseFromOptionals(Map<IdentifierT, Optional<T>> entityOptionalsById) {
        Collection<Optional<T>> entityOptionals = entityOptionalsById.values();
        if(entityOptionals.stream().anyMatch(o -> !o.isPresent())) {
            return ResponseEntity.notFound().build();
        }
        // TODO: Would be easier with Java 9 (see http://www.baeldung.com/java-filter-stream-of-optional)
        return ResponseEntity.ok(entityOptionals.stream()
                .flatMap(entityOptional -> entityOptional.map(Stream::of).orElseGet(Stream::empty))
                .collect(toSet()));
    }
}
