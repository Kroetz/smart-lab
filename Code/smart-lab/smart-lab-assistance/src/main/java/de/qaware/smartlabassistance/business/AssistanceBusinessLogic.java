package de.qaware.smartlabassistance.business;

import de.qaware.smartlabcommons.data.assistance.IAssistance;
import de.qaware.smartlabcommons.data.context.IContext;
import de.qaware.smartlabcommons.data.generic.IResolver;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.exception.InsufficientContextException;
import de.qaware.smartlabcommons.exception.UnknownAssistanceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AssistanceBusinessLogic implements IAssistanceBusinessLogic {

    private final IResolver<String, IAssistance> assistanceResolver;

    public AssistanceBusinessLogic(IResolver<String, IAssistance> assistanceResolver) {
        this.assistanceResolver = assistanceResolver;
    }

    public void beginAssistance(String assistanceId, IContext context) {
        log.info("Starting assistance (ID: \"{}\") in room with ID \"{}\"",
                assistanceId,
                context.getRoom().map(IRoom::getName).orElseThrow(InsufficientContextException::new));
        IAssistance assistance = this.assistanceResolver.resolve(assistanceId).orElseThrow(UnknownAssistanceException::new);
        assistance.begin(context);
        log.info("Started assistance (ID: \"{}\") in room with ID \"{}\"",
                assistanceId,
                context.getRoom().map(IRoom::getName).orElseThrow(InsufficientContextException::new));
    }

    public void endAssistance(String assistanceId, IContext context) {

        log.info("Stopped assistance (ID: \"{}\") in room with ID \"{}\"", assistanceId, context.getRoom().map(IRoom::getName).orElse("fail"));

        // TODO
    }

    public void updateAssistance(String assistanceId, IContext context) {

        // TODO
    }
}
