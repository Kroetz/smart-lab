package de.qaware.smartlabassistance.business;

import de.qaware.smartlabcommons.data.assistance.IAssistanceResolver;
import de.qaware.smartlabcommons.data.context.IContext;
import de.qaware.smartlabcommons.result.BeginAssistanceResult;
import de.qaware.smartlabcommons.result.EndAssistanceResult;
import de.qaware.smartlabcommons.result.UpdateAssistanceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
@Slf4j
public class AssistanceBusinessLogic implements IAssistanceBusinessLogic {

    private final IAssistanceResolver assistanceResolver;

    public AssistanceBusinessLogic(IAssistanceResolver assistanceResolver) {
        this.assistanceResolver = assistanceResolver;
    }

    public BeginAssistanceResult beginAssistance(String assistanceId, IContext context) {

        // TODO
        throw new NotImplementedException();

        /*Optional<IAssistance> assistance = this.assistanceResolver.resolveAssistanceId(assistanceId);

        //room.getMinuteTakingDevice().ifPresent(device -> device.start());


        // TODO: Resolve which "device" is responsible in the room (from context) for the given assistance. This can be:
        // * a web service
        // * a REST-compatible device
        // * a Smart-Lab-Satellite

        // TODO: Call assistance and pass the device

        // TODO: In assistance: Call the appropriate device functions that encapsulate REST-calls oder local API calls.

        return BeginAssistanceResult.SUCCESS;*/
    }

    public EndAssistanceResult endAssistance(String assistanceId, IContext context) {

        // TODO
        throw new NotImplementedException();
    }

    public UpdateAssistanceResult updateAssistance(String assistanceId, IContext context) {

        // TODO
        throw new NotImplementedException();
    }
}
