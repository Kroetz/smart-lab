package de.qaware.smartlabcommons.api.internal.service.assistance;

import de.qaware.smartlabcommons.api.internal.client.IAssistanceApiClient;
import de.qaware.smartlabcommons.data.context.IContext;
import de.qaware.smartlabcommons.exception.UnknownErrorException;
import de.qaware.smartlabcommons.miscellaneous.Property;
import feign.FeignException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
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
