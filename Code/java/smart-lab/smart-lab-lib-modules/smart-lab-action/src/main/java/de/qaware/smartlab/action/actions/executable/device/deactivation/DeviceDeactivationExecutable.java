package de.qaware.smartlab.action.actions.executable.device.deactivation;

import de.qaware.smartlab.action.actions.callable.device.deactivation.DeviceDeactivationCallable;
import de.qaware.smartlab.action.actions.executable.generic.AbstractActionExecutable;
import de.qaware.smartlab.action.actions.info.device.deactivation.DeviceDeactivationInfo;
import de.qaware.smartlab.action.result.VoidActionResult;
import de.qaware.smartlab.actuator.adapter.adapters.miscellaneous.IDeactivatable;
import de.qaware.smartlab.api.service.connector.actuator.IActuatorManagementService;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.generic.IResolver;
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
                actionArgs -> actuatorManagementService.findOne(actionArgs.getDeviceId()).getResponsibleDelegate(),
                actionArgs -> Optional.of(actuatorManagementService.findOne(actionArgs.getDeviceId()).getType()));
    }

    @Override
    protected IActionResult execute(IDeactivatable deactivatableAdapter, DeviceDeactivationCallable.ActionArgs actionArgs) throws ActionException {
        deactivatableAdapter.deactivate();
        return VoidActionResult.newInstance();
    }
}
