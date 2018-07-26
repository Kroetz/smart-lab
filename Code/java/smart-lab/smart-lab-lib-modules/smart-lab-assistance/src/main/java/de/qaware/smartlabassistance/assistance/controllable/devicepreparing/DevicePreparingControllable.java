package de.qaware.smartlabassistance.assistance.controllable.devicepreparing;

import de.qaware.smartlabaction.action.submittable.device.activation.DeviceActivationSubmittable;
import de.qaware.smartlabaction.action.submittable.device.deactivation.DeviceDeactivationSubmittable;
import de.qaware.smartlabaction.action.submittable.generic.IActionSubmittable;
import de.qaware.smartlabapi.service.connector.action.IActionService;
import de.qaware.smartlabassistance.assistance.controllable.generic.AbstractAssistanceControllable;
import de.qaware.smartlabassistance.assistance.controllable.generic.IAssistanceControllable;
import de.qaware.smartlabassistance.assistance.controllable.miscellaneous.factory.AbstractAssistanceControllableFactory;
import de.qaware.smartlabassistance.assistance.info.devicepreparing.DevicePreparingInfo;
import de.qaware.smartlabassistance.assistance.info.generic.IAssistanceInfo;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
public class DevicePreparingControllable extends AbstractAssistanceControllable {

    private final IActionSubmittable<DeviceActivationSubmittable.ActionArgs, Void> deviceActivation;
    private final IActionSubmittable<DeviceDeactivationSubmittable.ActionArgs, Void> deviceDeactivation;

    private DevicePreparingControllable(
            IAssistanceInfo devicePreparingInfo,
            IActionSubmittable<DeviceActivationSubmittable.ActionArgs, Void> deviceActivation,
            IActionSubmittable<DeviceDeactivationSubmittable.ActionArgs, Void> deviceDeactivation) {
        super(devicePreparingInfo);
        this.deviceActivation = deviceActivation;
        this.deviceDeactivation = deviceDeactivation;
    }

    @Override
    public void begin(IActionService actionService, IAssistanceContext context) {
        // TODO: casting smells
        // TODO: Check for casting exception and throw illegalstateexception
        DevicePreparingInfo.Configuration config = (DevicePreparingInfo.Configuration) context.getAssistanceConfiguration();
        final DeviceActivationSubmittable.ActionArgs deviceActivationArgs = DeviceActivationSubmittable.ActionArgs.of(
                config.getDeviceId());
        this.deviceActivation.submitExecution(actionService, deviceActivationArgs);
    }

    @Override
    public void end(IActionService actionService, IAssistanceContext context) {
        // TODO: casting smells
        // TODO: Check for casting exception and throw illegalstateexception
        DevicePreparingInfo.Configuration config = (DevicePreparingInfo.Configuration) context.getAssistanceConfiguration();
        final DeviceDeactivationSubmittable.ActionArgs deviceDeactivationArgs = DeviceDeactivationSubmittable.ActionArgs.of(
                config.getDeviceId());
        this.deviceDeactivation.submitExecution(actionService, deviceDeactivationArgs);
    }

    @Override
    public void update(IActionService actionService, IAssistanceContext context) {
        // TODO: Implementation
    }

    @Component
    @Slf4j
    public static class Factory extends AbstractAssistanceControllableFactory {

        private final IActionSubmittable<DeviceActivationSubmittable.ActionArgs, Void> deviceActivation;
        private final IActionSubmittable<DeviceDeactivationSubmittable.ActionArgs, Void> deviceDeactivation;

        public Factory(
                IAssistanceInfo devicePreparingInfo,
                IActionSubmittable<DeviceActivationSubmittable.ActionArgs, Void> deviceActivation,
                IActionSubmittable<DeviceDeactivationSubmittable.ActionArgs, Void> deviceDeactivation) {
            super(devicePreparingInfo);
            this.deviceActivation = deviceActivation;
            this.deviceDeactivation = deviceDeactivation;
        }

        @Override
        public IAssistanceControllable newInstance() {
            return new DevicePreparingControllable(
                    this.assistanceInfo,
                    this.deviceActivation,
                    this.deviceDeactivation);
        }
    }
}
