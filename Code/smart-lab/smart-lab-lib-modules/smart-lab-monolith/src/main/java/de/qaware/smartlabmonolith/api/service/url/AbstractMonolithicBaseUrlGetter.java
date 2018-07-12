package de.qaware.smartlabmonolith.api.service.url;

import de.qaware.smartlabapi.service.IServiceBaseUrlGetter;
import de.qaware.smartlabcore.generic.controller.url.IBaseUrlController;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;

@Slf4j
public abstract class AbstractMonolithicBaseUrlGetter implements IServiceBaseUrlGetter {

    private final IBaseUrlController baseUrlController;

    public AbstractMonolithicBaseUrlGetter(IBaseUrlController baseUrlController) {
        this.baseUrlController = baseUrlController;
    }

    @Override
    public URL getBaseUrl() {
        // TODO: Check response code
        return this.baseUrlController.getBaseUrl().getBody();
    }
}
