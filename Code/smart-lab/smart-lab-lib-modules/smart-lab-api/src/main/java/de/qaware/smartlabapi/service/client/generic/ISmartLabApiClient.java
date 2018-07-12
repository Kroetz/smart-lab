package de.qaware.smartlabapi.service.client.generic;

import org.springframework.http.ResponseEntity;

import java.net.URL;

public interface ISmartLabApiClient {

    ResponseEntity<URL> getBaseUrl();
}
