package de.qaware.smartlabcore.service.controller.url;

import de.qaware.smartlabcore.url.IBaseUrlDetector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public abstract class AbstractBaseUrlController implements IBaseUrlController {

    private final IBaseUrlDetector baseUrlDetector;

    public AbstractBaseUrlController(IBaseUrlDetector baseUrlDetector) {
        this.baseUrlDetector = baseUrlDetector;
    }

    public ResponseEntity<URL> getBaseUrl() {
        try {
            return ResponseEntity.ok(this.baseUrlDetector.detect());
        } catch (MalformedURLException e) {
            log.error("Could determine base URL", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
