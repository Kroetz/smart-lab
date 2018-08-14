package de.qaware.smartlab.core.service.controller.url;

import de.qaware.smartlab.core.service.url.IBaseUrlDetector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URL;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
public abstract class AbstractBaseUrlController implements IBaseUrlController {

    private final IBaseUrlDetector baseUrlDetector;

    protected AbstractBaseUrlController(IBaseUrlDetector baseUrlDetector) {
        this.baseUrlDetector = baseUrlDetector;
    }

    public ResponseEntity<URL> getBaseUrl() {
        try {
            return ResponseEntity.ok(this.baseUrlDetector.detect());
        } catch (MalformedURLException e) {
            log.error("Could determine base URL", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
}
