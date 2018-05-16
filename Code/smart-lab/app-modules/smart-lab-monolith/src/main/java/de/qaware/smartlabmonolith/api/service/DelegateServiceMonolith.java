package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabcommons.api.client.IDelegateApiClient;
import de.qaware.smartlabcommons.api.service.delegate.AbstractDelegateService;
import de.qaware.smartlabcommons.exception.UnknownDelegateException;
import de.qaware.smartlabcommons.miscellaneous.ProfileNames;
import feign.Client;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Profile(ProfileNames.MONOLITH)
@Import(FeignClientsConfiguration.class)
public class DelegateServiceMonolith extends AbstractDelegateService {

    private final Map<String, String> urlsByDelegateName;

    public DelegateServiceMonolith(
            Map<String, String> urlsByDelegateName,
            Client client,
            Encoder feignEncoder,       // TODO: Suppress compiler warnings about failed autowiring
            Decoder feignDecoder) {
        super(client, feignEncoder, feignDecoder);
        this.urlsByDelegateName = urlsByDelegateName;
    }

    @Override
    protected IDelegateApiClient createNewClient(String serviceName) {
        if(this.urlsByDelegateName.containsKey(serviceName)) {
            return Feign.builder()
                    .client(this.client)
                    .encoder(this.feignEncoder)
                    .decoder(this.feignDecoder)
                    .target(IDelegateApiClient.class, this.urlsByDelegateName.get(serviceName));
        }
        throw new UnknownDelegateException(String.format("There was no URL configured for the delegate with the name \"%s\""));
    }
}
