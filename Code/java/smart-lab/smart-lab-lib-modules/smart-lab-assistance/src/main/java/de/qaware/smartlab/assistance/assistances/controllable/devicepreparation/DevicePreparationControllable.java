package de.qaware.smartlab.assistance.assistances.controllable.devicepreparation;

import de.qaware.smartlab.action.actions.callable.device.activation.DeviceActivationCallable;
import de.qaware.smartlab.action.actions.callable.device.deactivation.DeviceDeactivationCallable;
import de.qaware.smartlab.action.actions.callable.generic.IActionCallable;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.assistance.assistances.controllable.generic.AbstractAssistanceControllable;
import de.qaware.smartlab.assistance.assistances.controllable.generic.IAssistanceControllable;
import de.qaware.smartlab.assistance.assistances.controllable.miscellaneous.factory.AbstractAssistanceControllableFactory;
import de.qaware.smartlab.assistance.assistances.info.devicepreparation.DevicePreparationInfo;
import de.qaware.smartlab.assistance.assistances.info.generic.IAssistanceInfo;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
public class DevicePreparationControllable extends AbstractAssistanceControllable {

    private final IActionCallable<DeviceActivationCallable.ActionArgs, Void> deviceActivation;
    private final IActionCallable<DeviceDeactivationCallable.ActionArgs, Void> deviceDeactivation;

    private DevicePreparationControllable(
            IAssistanceInfo devicePreparationInfo,
            IActionCallable<DeviceActivationCallable.ActionArgs, Void> deviceActivation,
            IActionCallable<DeviceDeactivationCallable.ActionArgs, Void> deviceDeactivation) {
        super(devicePreparationInfo);
        this.deviceActivation = deviceActivation;
        this.deviceDeactivation = deviceDeactivation;
    }

    @Override
    public void begin(IActionService actionService, IAssistanceContext context) {
        DevicePreparationInfo.Configuration config = toSpecificConfigType(
                DevicePreparationInfo.Configuration.class,
                context.getAssistanceConfiguration());
        final DeviceActivationCallable.ActionArgs deviceActivationArgs = DeviceActivationCallable.ActionArgs.of(
                config.getDeviceId());
        this.deviceActivation.call(actionService, deviceActivationArgs);
    }

    @Override
    public void end(IActionService actionService, IAssistanceContext context) {
        DevicePreparationInfo.Configuration config = toSpecificConfigType(
                DevicePreparationInfo.Configuration.class,
                context.getAssistanceConfiguration());
        final DeviceDeactivationCallable.ActionArgs deviceDeactivationArgs = DeviceDeactivationCallable.ActionArgs.of(
                config.getDeviceId());
        this.deviceDeactivation.call(actionService, deviceDeactivationArgs);
    }

    @Component
    @Slf4j
    public static class Factory extends AbstractAssistanceControllableFactory {

        private final IActionCallable<DeviceActivationCallable.ActionArgs, Void> deviceActivation;
        private final IActionCallable<DeviceDeactivationCallable.ActionArgs, Void> deviceDeactivation;

        public Factory(
                IAssistanceInfo devicePreparationInfo,
                IActionCallable<DeviceActivationCallable.ActionArgs, Void> deviceActivation,
                IActionCallable<DeviceDeactivationCallable.ActionArgs, Void> deviceDeactivation) {
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
