package de.qaware.smartlabcommons.api.service.delegate;

import de.qaware.smartlabcommons.api.client.IDelegateApiClient;
import de.qaware.smartlabcommons.data.action.IActionArgs;
import de.qaware.smartlabcommons.exception.UnknownErrorException;
import feign.Client;
import feign.FeignException;
import feign.codec.Decoder;
import feign.codec.Encoder;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractDelegateService implements IDelegateService {

    private final Map<String, IDelegateApiClient> clientsByServiceName;
    protected final Client client;
    protected final Encoder feignEncoder;
    protected final Decoder feignDecoder;

    public AbstractDelegateService(
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

    protected abstract IDelegateApiClient createNewClient(String serviceName);
}
