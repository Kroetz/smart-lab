package de.qaware.smartlabaction.action;

import de.qaware.smartlabcommons.api.internal.service.action.IActionService;

import java.util.function.Function;

public interface IActionExecution<T> extends Function<IActionService, T> {

    default T execute(IActionService actionService) {
        return apply(actionService);
    }
}
