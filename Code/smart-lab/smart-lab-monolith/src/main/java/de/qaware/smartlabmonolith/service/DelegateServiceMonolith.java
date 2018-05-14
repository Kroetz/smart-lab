package de.qaware.smartlabmonolith.service;

import de.qaware.smartlabcommons.api.service.delegate.DelegateMicroservice;
import de.qaware.smartlabcommons.api.service.delegate.IDelegateService;
import de.qaware.smartlabcommons.data.action.IActionArgs;
import de.qaware.smartlabcommons.miscellaneous.Constants;
import de.qaware.smartlabdelegate.controller.DelegateController;
import feign.Client;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(Constants.PROFILE_NAME_MONOLITH)
public class DelegateServiceMonolith extends DelegateMicroservice {

    public DelegateServiceMonolith(
            Client client,
            Encoder feignEncoder,
            Decoder feignDecoder) {
        super(client, feignEncoder, feignDecoder);
    }
}

/*@Component
@Profile(Constants.PROFILE_NAME_MONOLITH)
public class DelegateServiceMonolith implements IDelegateService {

    private final DelegateController delegateController;

    public DelegateServiceMonolith(DelegateController delegateController) {
        this.delegateController = delegateController;
    }

    @Override
    public void executeAction(String delegateId, String actionId, IActionArgs actionArgs) {
        delegateController.executeAction(actionId, actionArgs);
    }
}*/
