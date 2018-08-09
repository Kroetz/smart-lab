package de.qaware.smartlab.action.actions.executable.generic;

import de.qaware.smartlab.action.actions.info.generic.IActionInfo;
import de.qaware.smartlab.api.service.connector.delegate.IDelegateService;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.actuator.IActuatorAdapter;
import de.qaware.smartlab.core.data.generic.IResolver;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.function.Function;

import static java.lang.String.format;

@Slf4j
public abstract class AbstractActionExecutable<ActionArgsT extends IActionArgs, ActuatorAdapterT extends IActuatorAdapter> implements IActionExecutable {

    protected final IActionInfo actionInfo;
    private final IResolver<String, ActuatorAdapterT> actuatorAdapterResolver;
    private final Function<ActionArgsT, String> actuatorTypeGetter;
    private final Function<ActionArgsT, Optional<String>> responsibleDelegateGetter;

    public AbstractActionExecutable(
            IActionInfo actionInfo,
            IResolver<String, ActuatorAdapterT> actuatorAdapterResolver,
            Function<ActionArgsT, String> actuatorTypeGetter,
            Function<ActionArgsT, Optional<String>> responsibleDelegateGetter) {
        this.actionInfo = actionInfo;
        this.actuatorAdapterResolver = actuatorAdapterResolver;
        this.actuatorTypeGetter = actuatorTypeGetter;
        this.responsibleDelegateGetter = responsibleDelegateGetter;
    }

    public String getActionId() {
        return this.actionInfo.getActionId();
    }

    @Override
    public IActionResult executeLocally(String actuatorType, IActionArgs genericActionArgs) {
        ActionArgsT actionArgs = toSpecificArgsType(genericActionArgs);
        ActuatorAdapterT actuatorAdapter = this.actuatorAdapterResolver.resolve(actuatorType);
        if(!actuatorAdapter.hasLocalApi()) throw new IllegalStateException();     // TODO: Better exception
        return execute(actuatorAdapter, actionArgs);
    }

    @Override
    public IActionResult executeRemotely(IActionArgs genericActionArgs, IDelegateService delegateService) {
        ActionArgsT actionArgs = toSpecificArgsType(genericActionArgs);
        String actuatorType = this.actuatorTypeGetter.apply(actionArgs);
        ActuatorAdapterT actuatorAdapter = this.actuatorAdapterResolver.resolve(actuatorType);
        if(actuatorAdapter.hasLocalApi()) return delegateService.executeAction(
                responsibleDelegateGetter.apply(actionArgs).orElseThrow(IllegalStateException::new),  // TODO: Better exception
                this.actionInfo.getActionId(),
                actuatorType,
                actionArgs);
        return execute(actuatorAdapter, actionArgs);
    }

    protected abstract IActionResult execute(ActuatorAdapterT actuatorAdapter, ActionArgsT actionArgs);

    protected ActionArgsT toSpecificArgsType(IActionArgs genericArgs) throws IllegalStateException {
        try {
            return (ActionArgsT) genericArgs;
        }
        catch(ClassCastException e) {
            String errorMessage = format(
                    "The action args %s must be of the type %s",
                    genericArgs.toString());
            log.error(errorMessage, e);
            throw new IllegalStateException(errorMessage, e);
        }
    }
}
