package de.qaware.smartlabassistance.assistance.controllable.devicepreparation;

import de.qaware.smartlab.action.actions.submittable.device.activation.DeviceActivationSubmittable;
import de.qaware.smartlab.action.actions.submittable.device.deactivation.DeviceDeactivationSubmittable;
import de.qaware.smartlab.action.actions.submittable.generic.IActionSubmittable;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlabassistance.assistance.controllable.generic.AbstractAssistanceControllable;
import de.qaware.smartlabassistance.assistance.controllable.generic.IAssistanceControllable;
import de.qaware.smartlabassistance.assistance.controllable.miscellaneous.factory.AbstractAssistanceControllableFactory;
import de.qaware.smartlabassistance.assistance.info.devicepreparation.DevicePreparationInfo;
import de.qaware.smartlabassistance.assistance.info.generic.IAssistanceInfo;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
public class DevicePreparationControllable extends AbstractAssistanceControllable {

    private final IActionSubmittable<DeviceActivationSubmittable.ActionArgs, Void> deviceActivation;
    private final IActionSubmittable<DeviceDeactivationSubmittable.ActionArgs, Void> deviceDeactivation;

    private DevicePreparationControllable(
            IAssistanceInfo devicePreparationInfo,
            IActionSubmittable<DeviceActivationSubmittable.ActionArgs, Void> deviceActivation,
            IActionSubmittable<DeviceDeactivationSubmittable.ActionArgs, Void> deviceDeactivation) {
        super(devicePreparationInfo);
        this.deviceActivation = deviceActivation;
        this.deviceDeactivation = deviceDeactivation;
    }

    @Override
    public void begin(IActionService actionService, IAssistanceContext context) {
        // TODO: casting smells
        // TODO: Check for casting exception and throw illegalstateexception
        DevicePreparationInfo.Configuration config = (DevicePreparationInfo.Configuration) context.getAssistanceConfiguration();
        final DeviceActivationSubmittable.ActionArgs deviceActivationArgs = DeviceActivationSubmittable.ActionArgs.of(
                config.getDeviceId());
        this.deviceActivation.submitExecution(actionService, deviceActivationArgs);
    }

    @Override
    public void end(IActionService actionService, IAssistanceContext context) {
        // TODO: casting smells
        // TODO: Check for casting exception and throw illegalstateexception
        DevicePreparationInfo.Configuration config = (DevicePreparationInfo.Configuration) context.getAssistanceConfiguration();
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
                IAssistanceInfo devicePreparationInfo,
                IActionSubmittable<DeviceActivationSubmittable.ActionArgs, Void> deviceActivation,
                IActionSubmittable<DeviceDeactivationSubmittable.ActionArgs, Void> deviceDeactivation) {
            super(devicePreparationInfo);
            this.deviceActivation = deviceActivation;
            this.deviceDeactivation = deviceDeactivation;
        }

        @Override
        public IAssistanceControllable newInstance() {
            return new DevicePreparationControllable(
                    this.assistanceInfo,
                    this.deviceActivation,
                    this.deviceDeactivation);
        }
    }
}
