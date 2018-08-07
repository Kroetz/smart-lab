package de.qaware.smartlabaction.action.executable.device.activation;

import de.qaware.smartlabactuatoradapter.actuator.generic.IActivatable;
import de.qaware.smartlabaction.action.executable.generic.AbstractActionExecutable;
import de.qaware.smartlabaction.action.info.device.activation.DeviceActivationInfo;
import de.qaware.smartlabaction.action.result.VoidActionResult;
import de.qaware.smartlabaction.action.submittable.device.activation.DeviceActivationSubmittable;
import de.qaware.smartlabapi.service.connector.delegate.IDelegateService;
import de.qaware.smartlabapi.service.connector.device.IDeviceManagementService;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.generic.IResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeviceActivationExecutable extends AbstractActionExecutable {

    private IResolver<String, IActivatable> activatableResolver;
    private IDeviceManagementService deviceManagementService;

    public DeviceActivationExecutable(
            DeviceActivationInfo deviceActivationInfo,
            IResolver<String, IActivatable> activatableResolver,
            IDeviceManagementService deviceManagementService) {
        super(deviceActivationInfo);
        this.activatableResolver = activatableResolver;
        this.deviceManagementService = deviceManagementService;
    }

    // TODO: too much code duplicates
    public IActionResult execute(String deviceType, IActionArgs genericActionArgs) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        DeviceActivationSubmittable.ActionArgs actionArgs = convertToSpecificActionArgs(
                DeviceActivationSubmittable.ActionArgs.class,
                genericActionArgs);
        IActivatable activatable = this.activatableResolver.resolve(deviceType);
        if(!activatable.hasLocalApi()) throw new IllegalStateException();     // TODO: Better exception
        activatable.activate();
        return VoidActionResult.newInstance();
    }

    public IActionResult execute(IActionArgs genericActionArgs, IDelegateService delegateService) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        DeviceActivationSubmittable.ActionArgs actionArgs = convertToSpecificActionArgs(
                DeviceActivationSubmittable.ActionArgs.class,
                genericActionArgs);
        IDevice device = this.deviceManagementService.findOne(actionArgs.getDeviceId());
        String deviceType = device.getType();
        IActivatable activatable = this.activatableResolver.resolve(deviceType);
        if(activatable.hasLocalApi()) return delegateService.executeAction(
                device.getResponsibleDelegate(),
                this.actionInfo.getActionId(),
                deviceType,
                actionArgs);
        activatable.activate();
        return VoidActionResult.newInstance();
    }
}
