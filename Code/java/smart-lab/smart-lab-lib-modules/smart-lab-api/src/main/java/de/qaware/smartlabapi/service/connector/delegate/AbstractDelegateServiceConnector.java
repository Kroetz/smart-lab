package de.qaware.smartlabapi.service.connector.delegate;

import de.qaware.smartlabapi.service.client.delegate.IDelegateApiClient;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.exception.ConnectionFailedException;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import feign.Client;
import feign.FeignException;
import feign.RetryableException;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Objects.isNull;

@Slf4j
public abstract class AbstractDelegateServiceConnector implements IDelegateService {

    private final Map<String, IDelegateApiClient> clientsByServiceName;
    protected final Client client;
    protected final Encoder feignEncoder;
    protected final Decoder feignDecoder;

    public AbstractDelegateServiceConnector(
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
        catch(RetryableException e) {
            String errorMessage = format("Failed to connect to delegate \"%s\" to execute action \"%s\"", serviceName, actionId);
            log.error(errorMessage, e);
            throw new ConnectionFailedException(errorMessage);
        }
        catch(FeignException e) {
            throw e;
        }
        catch(Exception e) {
            throw new UnknownErrorException(e);
        }
    }

    protected abstract IDelegateApiClient createNewClient(String serviceName);
}
