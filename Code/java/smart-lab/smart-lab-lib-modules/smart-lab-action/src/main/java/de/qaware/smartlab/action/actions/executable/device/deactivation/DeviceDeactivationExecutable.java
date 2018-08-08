package de.qaware.smartlab.action.actions.executable.device.deactivation;

import de.qaware.smartlab.actuator.adapter.adapters.miscellaneous.IDeactivatable;
import de.qaware.smartlab.action.actions.executable.generic.AbstractActionExecutable;
import de.qaware.smartlab.action.actions.info.device.deactivation.DeviceDeactivationInfo;
import de.qaware.smartlab.action.result.VoidActionResult;
import de.qaware.smartlab.action.actions.submittable.device.deactivation.DeviceDeactivationSubmittable;
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
public class DeviceDeactivationExecutable extends AbstractActionExecutable {

    private IResolver<String, IDeactivatable> deactivatableResolver;
    private IActuatorManagementService actuatorManagementService;

    public DeviceDeactivationExecutable(
            DeviceDeactivationInfo deviceDeactivationInfo,
            IResolver<String, IDeactivatable> deactivatableResolver,
            IActuatorManagementService actuatorManagementService) {
        super(deviceDeactivationInfo);
        this.deactivatableResolver = deactivatableResolver;
        this.actuatorManagementService = actuatorManagementService;
    }

    // TODO: too much code duplicates
    public IActionResult execute(String actuatorType, IActionArgs genericActionArgs) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        DeviceDeactivationSubmittable.ActionArgs actionArgs = toSpecificArgsType(
                DeviceDeactivationSubmittable.ActionArgs.class,
                genericActionArgs);
        IDeactivatable deactivatable = this.deactivatableResolver.resolve(actuatorType);
        if(!deactivatable.hasLocalApi()) throw new IllegalStateException();     // TODO: Better exception
        deactivatable.deactivate();
        return VoidActionResult.newInstance();
    }

    public IActionResult execute(IActionArgs genericActionArgs, IDelegateService delegateService) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        DeviceDeactivationSubmittable.ActionArgs actionArgs = toSpecificArgsType(
                DeviceDeactivationSubmittable.ActionArgs.class,
                genericActionArgs);
        IActuator actuator = this.actuatorManagementService.findOne(actionArgs.getDeviceId());
        String actuatorType = actuator.getType();
        IDeactivatable deactivatable = this.deactivatableResolver.resolve(actuatorType);
        if(deactivatable.hasLocalApi()) return delegateService.executeAction(
                actuator.getResponsibleDelegate(),
                this.actionInfo.getActionId(),
                actuatorType,
                actionArgs);
        deactivatable.deactivate();
        return VoidActionResult.newInstance();
    }
}
