package de.qaware.smartlab.monolith.service.connector.delegate;

import de.qaware.smartlab.api.service.client.delegate.IDelegateApiClient;
import de.qaware.smartlab.api.service.connector.delegate.AbstractDelegateServiceConnector;
import de.qaware.smartlab.core.configuration.ModularityConfiguration;
import de.qaware.smartlab.core.exception.delegate.DelegateException;
import de.qaware.smartlab.core.constant.Property;
import de.qaware.smartlab.monolith.configuration.MonolithModuleConfiguration;
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

import static java.lang.String.format;

@Component
@ConditionalOnProperty(
        prefix = ModularityConfiguration.Properties.PREFIX,
        name = ModularityConfiguration.Properties.MODULARITY,
        havingValue = ModularityConfiguration.Properties.MONOLITH)
@Import(FeignClientsConfiguration.class)
public class DelegateMonolithicServiceConnector extends AbstractDelegateServiceConnector {

    private final Map<String, String> urlsByDelegateName;

    public DelegateMonolithicServiceConnector(
            @Qualifier(MonolithModuleConfiguration.QUALIFIER_URLS_BY_DELEGATE_NAME) Map<String, String> urlsByDelegateName,
            Client client,
            Encoder feignEncoder,
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
        throw new DelegateException(format("There was no URL configured for the delegate with the name \"%s\"", serviceName));
    }
}
