package de.qaware.smartlabapi.service.delegate;

import de.qaware.smartlabapi.client.IDelegateApiClient;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import feign.Client;
import feign.FeignException;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

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
    public IActionResult executeAction(String serviceName, String actionId, String deviceType, IActionArgs actionArgs) {
        IDelegateApiClient delegateApiClient = this.clientsByServiceName.get(serviceName);
        if(isNull(delegateApiClient)) delegateApiClient = createNewClient(serviceName);
        try {
            ResponseEntity<IActionResult> response = delegateApiClient.executeAction(actionId, deviceType, actionArgs);
            return response.getBody();
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    protected abstract IDelegateApiClient createNewClient(String serviceName);
}
