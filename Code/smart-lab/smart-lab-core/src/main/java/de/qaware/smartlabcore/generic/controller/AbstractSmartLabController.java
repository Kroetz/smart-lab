package de.qaware.smartlabcore.generic.controller;

import org.springframework.http.ResponseEntity;

import java.util.Optional;

public abstract class AbstractSmartLabController {

    protected <T> ResponseEntity<T> responseFromOptional(Optional<T> optional) {
        return optional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
