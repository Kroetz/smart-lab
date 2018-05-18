package de.qaware.smartlabcommons.data.action;

import de.qaware.smartlabcommons.api.service.action.IActionService;

import java.util.function.Function;

public interface IActionExecution<T> extends Function<IActionService, T> {

    default T execute(IActionService actionService) {
        return apply(actionService);
    }
}
