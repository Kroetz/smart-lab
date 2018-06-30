package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabapi.client.IDelegateApiClient;
import de.qaware.smartlabapi.service.delegate.AbstractDelegateService;
import de.qaware.smartlabcore.exception.UnknownDelegateException;
import de.qaware.smartlabcore.miscellaneous.Property;
import feign.Client;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.lang.String.*;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
@Import(FeignClientsConfiguration.class)
public class DelegateMonolithicService extends AbstractDelegateService {

    private final Map<String, String> urlsByDelegateName;

    public DelegateMonolithicService(
            // TODO: String literal
            @Qualifier("urlsByDelegateName") Map<String, String> urlsByDelegateName,
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
        throw new UnknownDelegateException(format("There was no URL configured for the delegate with the name \"%s\"", serviceName));
    }
}
