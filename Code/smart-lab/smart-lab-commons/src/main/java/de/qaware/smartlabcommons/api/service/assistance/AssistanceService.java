package de.qaware.smartlabcommons.api.service.assistance;

import de.qaware.smartlabcommons.api.client.IAssistanceApiClient;
import de.qaware.smartlabcommons.data.context.IContext;
import de.qaware.smartlabcommons.exception.UnknownErrorException;
import feign.FeignException;
import org.springframework.stereotype.Component;

@Component
public class AssistanceService implements IAssistanceService {

    private final IAssistanceApiClient assistanceApiClient;

    public AssistanceService(IAssistanceApiClient assistanceApiClient) {
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
