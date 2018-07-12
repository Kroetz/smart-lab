package de.qaware.smartlabcore.generic.controller.url;

import org.springframework.http.ResponseEntity;

import java.net.URL;

public interface IBaseUrlController {

    ResponseEntity<URL> getBaseUrl();
}
