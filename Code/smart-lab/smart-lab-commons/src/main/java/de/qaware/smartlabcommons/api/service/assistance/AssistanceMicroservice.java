package de.qaware.smartlabcommons.api.service.assistance;

import de.qaware.smartlabcommons.api.client.IAssistanceApiClient;
import de.qaware.smartlabcommons.data.context.IContext;
import de.qaware.smartlabcommons.exception.UnknownErrorException;
import de.qaware.smartlabcommons.miscellaneous.Constants;
import feign.FeignException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(Constants.PROFILE_NAME_MICROSERVICE)
public class AssistanceMicroservice implements IAssistanceService {

    private final IAssistanceApiClient assistanceApiClient;

    public AssistanceMicroservice(IAssistanceApiClient assistanceApiClient) {
        this.assistanceApiClient = assistanceApiClient;
    }

    @Override
    public void beginAssistance(String assistanceId, IContext context) {
        try {
            this.assistanceApiClient.beginAssistance(assistanceId, context);
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public void endAssistance(String assistanceId, IContext context) {
        try {
            this.assistanceApiClient.endAssistance(assistanceId, context);
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public void updateAssistance(String assistanceId, IContext context) {
        try {
            this.assistanceApiClient.updateAssistance(assistanceId, context);
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }
}
