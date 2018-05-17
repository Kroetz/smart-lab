package de.qaware.smartlabcommons.data.assistance;

import de.qaware.smartlabcommons.api.service.assistance.IAssistanceService;

import java.util.function.Consumer;

public interface ITriggerReaction extends Consumer<IAssistanceService> {

    default void react(IAssistanceService assistanceService) {
        accept(assistanceService);
    }
}
