package de.qaware.smartlab.action.actions.executable.device.activation;

import de.qaware.smartlab.actuator.adapter.adapters.miscellaneous.IActivatable;
import de.qaware.smartlab.action.actions.executable.generic.AbstractActionExecutable;
import de.qaware.smartlab.action.actions.info.device.activation.DeviceActivationInfo;
import de.qaware.smartlab.action.result.VoidActionResult;
import de.qaware.smartlab.action.actions.callable.device.activation.DeviceActivationCallable;
import de.qaware.smartlab.api.service.connector.delegate.IDelegateService;
import de.qaware.smartlab.api.service.connector.actuator.IActuatorManagementService;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.actuator.IActuator;
import de.qaware.smartlab.core.data.generic.IResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeviceActivationExecutable extends AbstractActionExecutable {

    private IResolver<String, IActivatable> activatableResolver;
    private IActuatorManagementService actuatorManagementService;

    public DeviceActivationExecutable(
            DeviceActivationInfo deviceActivationInfo,
            IResolver<String, IActivatable> activatableResolver,
            IActuatorManagementService actuatorManagementService) {
        super(deviceActivationInfo);
        this.activatableResolver = activatableResolver;
        this.actuatorManagementService = actuatorManagementService;
    }

    // TODO: too much code duplicates
    public IActionResult execute(String actuatorType, IActionArgs genericActionArgs) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        DeviceActivationCallable.ActionArgs actionArgs = toSpecificArgsType(
                DeviceActivationCallable.ActionArgs.class,
                genericActionArgs);
        IActivatable activatable = this.activatableResolver.resolve(actuatorType);
        if(!activatable.hasLocalApi()) throw new IllegalStateException();     // TODO: Better exception
        activatable.activate();
        return VoidActionResult.newInstance();
    }

    public IActionResult execute(IActionArgs genericActionArgs, IDelegateService delegateService) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        DeviceActivationCallable.ActionArgs actionArgs = toSpecificArgsType(
                DeviceActivationCallable.ActionArgs.class,
                genericActionArgs);
        IActuator actuator = this.actuatorManagementService.findOne(actionArgs.getDeviceId());
        String actuatorType = actuator.getType();
        IActivatable activatable = this.activatableResolver.resolve(actuatorType);
        if(activatable.hasLocalApi()) return delegateService.executeAction(
                actuator.getResponsibleDelegate(),
                this.actionInfo.getActionId(),
                actuatorType,
                actionArgs);
        activatable.activate();
        return VoidActionResult.newInstance();
    }
}
