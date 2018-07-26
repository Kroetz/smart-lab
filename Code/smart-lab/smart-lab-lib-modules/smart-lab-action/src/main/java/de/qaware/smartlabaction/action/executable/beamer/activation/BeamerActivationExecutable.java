package de.qaware.smartlabaction.action.executable.beamer.activation;

import de.qaware.smartlabaction.action.actor.beamer.IBeamerAdapter;
import de.qaware.smartlabaction.action.executable.generic.AbstractActionExecutable;
import de.qaware.smartlabaction.action.info.beamer.activation.BeamerActivationInfo;
import de.qaware.smartlabaction.action.result.VoidActionResult;
import de.qaware.smartlabaction.action.submittable.beamer.activation.BeamerActivationSubmittable;
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
public class BeamerActivationExecutable extends AbstractActionExecutable {

    private IResolver<String, IBeamerAdapter> beamerAdapterResolver;
    private IDeviceManagementService deviceManagementService;

    public BeamerActivationExecutable(
            BeamerActivationInfo beamerActivationInfo,
            IResolver<String, IBeamerAdapter> beamerAdapterResolver,
            IDeviceManagementService deviceManagementService) {
        super(beamerActivationInfo);
        this.beamerAdapterResolver = beamerAdapterResolver;
        this.deviceManagementService = deviceManagementService;
    }

    // TODO: too much code duplicates
    public IActionResult execute(String deviceType, IActionArgs genericActionArgs) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        BeamerActivationSubmittable.ActionArgs actionArgs = convertToSpecificActionArgs(
                BeamerActivationSubmittable.ActionArgs.class,
                genericActionArgs);
        IBeamerAdapter beamerAdapter = this.beamerAdapterResolver
                .resolve(deviceType)
                .orElseThrow(UnknownDeviceAdapterException::new);
        if(!beamerAdapter.hasLocalApi()) throw new IllegalStateException();     // TODO: Better exception
        beamerAdapter.activate();
        return VoidActionResult.instance();
    }

    public IActionResult execute(IActionArgs genericActionArgs, IDelegateService delegateService) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        BeamerActivationSubmittable.ActionArgs actionArgs = convertToSpecificActionArgs(
                BeamerActivationSubmittable.ActionArgs.class,
                genericActionArgs);
        IDevice device = this.deviceManagementService.findOne(actionArgs.getBeamerId());
        String deviceType = device.getType();
        IBeamerAdapter beamerAdapter = this.beamerAdapterResolver
                .resolve(deviceType)
                .orElseThrow(UnknownDeviceAdapterException::new);
        if(beamerAdapter.hasLocalApi()) return delegateService.executeAction(
                device.getResponsibleDelegate(),
                this.actionInfo.getActionId(),
                deviceType,
                actionArgs);
        beamerAdapter.activate();
        return VoidActionResult.instance();
    }
}
