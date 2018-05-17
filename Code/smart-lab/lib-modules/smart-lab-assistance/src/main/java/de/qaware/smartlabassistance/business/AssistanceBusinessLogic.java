package de.qaware.smartlabassistance.business;

import de.qaware.smartlabcommons.api.service.action.IActionService;
import de.qaware.smartlabcommons.data.assistance.IAssistanceStage;
import de.qaware.smartlabcommons.data.assistance.IAssistance;
import de.qaware.smartlabcommons.data.context.IContext;
import de.qaware.smartlabcommons.data.generic.IResolver;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.exception.InsufficientContextException;
import de.qaware.smartlabcommons.exception.UnknownAssistanceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@Slf4j
public class AssistanceBusinessLogic implements IAssistanceBusinessLogic {

    private final IActionService actionService;
    private final IResolver<String, IAssistance> assistanceResolver;

    public AssistanceBusinessLogic(
            IActionService actionService,
            IResolver<String, IAssistance> assistanceResolver) {
        this.actionService = actionService;
        this.assistanceResolver = assistanceResolver;
    }

    public void executeAssistanceStage(String assistanceId, Function<IAssistance, IAssistanceStage> assistanceStageGetter) {
        IAssistance assistance = this.assistanceResolver.resolve(assistanceId).orElseThrow(UnknownAssistanceException::new);
        IAssistanceStage assistanceStage = assistanceStageGetter.apply(assistance);
        assistanceStage.execute(this.actionService);
    }

    public void beginAssistance(String assistanceId, final IContext context) {
        log.info("Executing begin phase of assistance (ID: \"{}\") in room with ID \"{}\"",
                assistanceId,
                context.getRoom().map(IRoom::getName).orElseThrow(InsufficientContextException::new));
        executeAssistanceStage(assistanceId, assistance -> assistance.actionsOfBeginStage(context));
        log.info("Executed begin phase of assistance (ID: \"{}\") in room with ID \"{}\"",
                assistanceId,
                context.getRoom().map(IRoom::getName).orElseThrow(InsufficientContextException::new));
    }

    public void endAssistance(String assistanceId, IContext context) {

        // TODO: Implementation
    }

    public void updateAssistance(String assistanceId, IContext context) {

        // TODO: Implementation
    }
}
