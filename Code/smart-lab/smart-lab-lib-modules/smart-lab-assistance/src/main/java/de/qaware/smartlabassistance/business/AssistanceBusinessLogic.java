package de.qaware.smartlabassistance.business;

import de.qaware.smartlabapi.service.action.IActionService;
import de.qaware.smartlabassistance.assistance.controllable.IAssistanceControllable;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import de.qaware.smartlabcore.data.generic.IResolver;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.exception.InsufficientContextException;
import de.qaware.smartlabcore.exception.UnknownAssistanceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@Slf4j
public class AssistanceBusinessLogic implements IAssistanceBusinessLogic {

    private final IActionService actionService;
    private final IResolver<String, IAssistanceControllable> assistanceControllableResolver;

    public AssistanceBusinessLogic(
            IActionService actionService,
            IResolver<String, IAssistanceControllable> assistanceControllableResolver) {
        this.actionService = actionService;
        this.assistanceControllableResolver = assistanceControllableResolver;
    }

    private void executeAssistanceStage(String assistanceId, Consumer<IAssistanceControllable> assistanceStageExecution) {
        IAssistanceControllable assistance = this.assistanceControllableResolver
                .resolve(assistanceId)
                .orElseThrow(UnknownAssistanceException::new);
        assistanceStageExecution.accept(assistance);
    }

    public void beginAssistance(String assistanceId, final IAssistanceContext context) {
        log.info("Executing begin stage of assistance (ID: \"{}\") in room with ID \"{}\"",
                assistanceId,
                context.getRoom().map(IRoom::getName).orElseThrow(InsufficientContextException::new));
        executeAssistanceStage(assistanceId, assistance -> assistance.begin(this.actionService, context));
        log.info("Executed begin stage of assistance (ID: \"{}\") in room with ID \"{}\"",
                assistanceId,
                context.getRoom().map(IRoom::getName).orElseThrow(InsufficientContextException::new));
    }

    public void endAssistance(String assistanceId, IAssistanceContext context) {
        log.info("Executing end stage of assistance (ID: \"{}\") in room with ID \"{}\"",
                assistanceId,
                context.getRoom().map(IRoom::getName).orElseThrow(InsufficientContextException::new));
        executeAssistanceStage(assistanceId, assistance -> assistance.end(this.actionService, context));
        log.info("Executed end stage of assistance (ID: \"{}\") in room with ID \"{}\"",
                assistanceId,
                context.getRoom().map(IRoom::getName).orElseThrow(InsufficientContextException::new));
    }

    public void updateAssistance(String assistanceId, IAssistanceContext context) {

        // TODO: Implementation
    }
}
