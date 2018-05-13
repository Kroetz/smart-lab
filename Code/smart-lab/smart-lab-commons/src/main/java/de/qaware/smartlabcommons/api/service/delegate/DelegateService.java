package de.qaware.smartlabcommons.api.service.delegate;

import de.qaware.smartlabcommons.api.client.IDelegateApiClient;
import de.qaware.smartlabcommons.data.action.IActionArgs;
import de.qaware.smartlabcommons.exception.UnknownErrorException;
import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.FeignException;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Import(FeignClientsConfiguration.class)
public class DelegateService implements IDelegateService {

    private final Map<String, IDelegateApiClient> clientsByServiceName;
    private final Client client;
    private final Encoder feignEncoder;
    private final Decoder feignDecoder;

    public DelegateService(
            Client client,
            Encoder feignEncoder,       // TODO: Suppress compiler warnings about failed autowiring
            Decoder feignDecoder) {
        this.clientsByServiceName = new HashMap<>();
        this.client = client;
        this.feignEncoder = feignEncoder;
        this.feignDecoder = feignDecoder;
    }

    @Override
    public void executeAction(String serviceName, String actionId, IActionArgs actionArgs) {
        IDelegateApiClient delegateApiClient = this.clientsByServiceName.get(serviceName);
        if(delegateApiClient == null) delegateApiClient = createNewClient(serviceName);
        try {
            delegateApiClient.executeAction(actionId, actionArgs);
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    private IDelegateApiClient createNewClient(String serviceName) {
        return Feign.builder()
                .client(this.client)
                .encoder(this.feignEncoder)
                .decoder(this.feignDecoder)
                .target(IDelegateApiClient.class, "http://" + serviceName); // TODO: String literal smells
    }
}
