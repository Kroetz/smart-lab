package de.qaware.smartlab.action.actions.executable.device.deactivation;

import de.qaware.smartlab.action.actions.callable.device.deactivation.DeviceDeactivationCallable;
import de.qaware.smartlab.action.actions.executable.generic.AbstractActionExecutable;
import de.qaware.smartlab.action.actions.info.device.deactivation.DeviceDeactivationInfo;
import de.qaware.smartlab.action.result.VoidActionResult;
import de.qaware.smartlab.actuator.adapter.adapters.deactivatable.IDeactivatable;
import de.qaware.smartlab.api.service.connector.actuator.IActuatorManagementService;
import de.qaware.smartlab.core.action.generic.IActionResult;
import de.qaware.smartlab.core.resolver.IResolver;
import de.qaware.smartlab.core.exception.action.ActionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class DeviceDeactivationExecutable extends AbstractActionExecutable<DeviceDeactivationCallable.ActionArgs, IDeactivatable> {

    public DeviceDeactivationExecutable(
            DeviceDeactivationInfo deviceDeactivationInfo,
            IResolver<String, IDeactivatable> deactivatableResolver,
            IActuatorManagementService actuatorManagementService) {
        super(
                deviceDeactivationInfo,
                deactivatableResolver,
                actionArgs -> actuatorManagementService.findOne(actionArgs.getDeviceId()).getType(),
                actionArgs -> Optional.of(actuatorManagementService.findOne(actionArgs.getDeviceId()).getResponsibleDelegate()));
    }

    @Override
    protected IActionResult execute(IDeactivatable deactivatable, DeviceDeactivationCallable.ActionArgs actionArgs) throws ActionException {
        deactivatable.deactivate();
        return VoidActionResult.newInstance();
    }
}
