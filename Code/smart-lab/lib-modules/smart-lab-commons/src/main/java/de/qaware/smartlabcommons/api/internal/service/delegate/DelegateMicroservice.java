package de.qaware.smartlabcommons.api.internal.service.delegate;

import de.qaware.smartlabcommons.api.internal.client.IDelegateApiClient;
import de.qaware.smartlabcommons.miscellaneous.Property;
import feign.Client;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
@Import(FeignClientsConfiguration.class)
public class DelegateMicroservice extends AbstractDelegateService {

    public DelegateMicroservice(
            Client client,
            Encoder feignEncoder,       // TODO: Suppress compiler warnings about failed autowiring
            Decoder feignDecoder) {
        super(client, feignEncoder, feignDecoder);
    }

    @Override
    protected IDelegateApiClient createNewClient(String serviceName) {
        return Feign.builder()
                .client(this.client)
                .encoder(this.feignEncoder)
                .decoder(this.feignDecoder)
                .target(IDelegateApiClient.class, "http://" + serviceName); // TODO: String literal smells
    }
}
