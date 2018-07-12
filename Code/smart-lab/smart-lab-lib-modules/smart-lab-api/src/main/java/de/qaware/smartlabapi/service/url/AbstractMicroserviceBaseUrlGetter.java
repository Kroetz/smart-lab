package de.qaware.smartlabapi.service.url;

import de.qaware.smartlabapi.service.client.generic.ISmartLabApiClient;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import de.qaware.smartlabcore.service.url.IServiceBaseUrlGetter;
import feign.FeignException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;

@Slf4j
public class AbstractMicroserviceBaseUrlGetter implements IServiceBaseUrlGetter {

    private final ISmartLabApiClient smartLabApiClient;

    public AbstractMicroserviceBaseUrlGetter(ISmartLabApiClient smartLabApiClient) {
        this.smartLabApiClient = smartLabApiClient;
    }

    @Override
    public URL getBaseUrl() {
        // TODO: Exceptions
        try {
            return this.smartLabApiClient.getBaseUrl().getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }
}
