package de.qaware.smartlabaction.action.executable.device.deactivation;

import de.qaware.smartlabactuatoradapter.actuator.generic.IDeactivatable;
import de.qaware.smartlabaction.action.executable.generic.AbstractActionExecutable;
import de.qaware.smartlabaction.action.info.device.deactivation.DeviceDeactivationInfo;
import de.qaware.smartlabaction.action.result.VoidActionResult;
import de.qaware.smartlabaction.action.submittable.device.deactivation.DeviceDeactivationSubmittable;
import de.qaware.smartlabapi.service.connector.delegate.IDelegateService;
import de.qaware.smartlabapi.service.connector.device.IDeviceManagementService;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.generic.IResolver;
import de.qaware.smartlabcore.exception.UnknownDeviceAdapterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeviceDeactivationExecutable extends AbstractActionExecutable {

    private IResolver<String, IDeactivatable> deactivatableResolver;
    private IDeviceManagementService deviceManagementService;

    public DeviceDeactivationExecutable(
            DeviceDeactivationInfo deviceDeactivationInfo,
            IResolver<String, IDeactivatable> deactivatableResolver,
            IDeviceManagementService deviceManagementService) {
        super(deviceDeactivationInfo);
        this.deactivatableResolver = deactivatableResolver;
        this.deviceManagementService = deviceManagementService;
    }

    // TODO: too much code duplicates
    public IActionResult execute(String deviceType, IActionArgs genericActionArgs) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        DeviceDeactivationSubmittable.ActionArgs actionArgs = convertToSpecificActionArgs(
                DeviceDeactivationSubmittable.ActionArgs.class,
                genericActionArgs);
        IDeactivatable deactivatable = this.deactivatableResolver
                .resolve(deviceType)
                .orElseThrow(UnknownDeviceAdapterException::new);
        if(!deactivatable.hasLocalApi()) throw new IllegalStateException();     // TODO: Better exception
        deactivatable.deactivate();
        return VoidActionResult.newInstance();
    }

    public IActionResult execute(IActionArgs genericActionArgs, IDelegateService delegateService) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        DeviceDeactivationSubmittable.ActionArgs actionArgs = convertToSpecificActionArgs(
                DeviceDeactivationSubmittable.ActionArgs.class,
                genericActionArgs);
        IDevice device = this.deviceManagementService.findOne(actionArgs.getDeviceId());
        String deviceType = device.getType();
        IDeactivatable deactivatable = this.deactivatableResolver
                .resolve(deviceType)
                .orElseThrow(UnknownDeviceAdapterException::new);
        if(deactivatable.hasLocalApi()) return delegateService.executeAction(
                device.getResponsibleDelegate(),
                this.actionInfo.getActionId(),
                deviceType,
                actionArgs);
        deactivatable.deactivate();
        return VoidActionResult.newInstance();
    }
}
