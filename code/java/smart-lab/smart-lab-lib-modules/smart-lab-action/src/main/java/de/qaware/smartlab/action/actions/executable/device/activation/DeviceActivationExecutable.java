package de.qaware.smartlab.action.actions.executable.device.activation;

import de.qaware.smartlab.action.actions.callable.device.activation.DeviceActivationCallable;
import de.qaware.smartlab.action.actions.executable.generic.AbstractActionExecutable;
import de.qaware.smartlab.action.actions.info.device.activation.DeviceActivationInfo;
import de.qaware.smartlab.action.result.VoidActionResult;
import de.qaware.smartlab.actuator.adapter.adapters.activatable.IActivatable;
import de.qaware.smartlab.api.service.connector.actuator.IActuatorManagementService;
import de.qaware.smartlab.core.action.generic.IActionResult;
import de.qaware.smartlab.core.resolver.IResolver;
import de.qaware.smartlab.core.exception.action.ActionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class DeviceActivationExecutable extends AbstractActionExecutable<DeviceActivationCallable.ActionArgs, IActivatable> {

    public DeviceActivationExecutable(
            DeviceActivationInfo deviceActivationInfo,
            IResolver<String, IActivatable> activatableResolver,
            IActuatorManagementService actuatorManagementService) {
        super(
                deviceActivationInfo,
                activatableResolver,
                actionArgs -> actuatorManagementService.findOne(actionArgs.getDeviceId()).getType(),
                actionArgs -> Optional.of(actuatorManagementService.findOne(actionArgs.getDeviceId()).getResponsibleDelegate()));
    }

    @Override
    protected IActionResult execute(IActivatable activatable, DeviceActivationCallable.ActionArgs actionArgs) throws ActionException {
        activatable.activate();
        return VoidActionResult.newInstance();
    }
}
