package de.qaware.smartlabcommons.data.assistance;

import de.qaware.smartlabcommons.api.service.action.IActionService;

import java.util.function.Consumer;

public interface IAssistanceStage extends Consumer<IActionService> {

    default void execute(IActionService actionService) {
        accept(actionService);
    }
}
