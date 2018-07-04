package de.qaware.smartlabapi.client;

import de.qaware.smartlabapi.GuiApiConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URL;

@FeignClient(name = GuiApiConstants.FEIGN_CLIENT_NAME, path = GuiApiConstants.MAPPING_BASE)
public interface IGuiApiClient {

    @GetMapping(GuiApiConstants.MAPPING_GET_BASE_URL)
    ResponseEntity<URL> getBaseUrl();
}
