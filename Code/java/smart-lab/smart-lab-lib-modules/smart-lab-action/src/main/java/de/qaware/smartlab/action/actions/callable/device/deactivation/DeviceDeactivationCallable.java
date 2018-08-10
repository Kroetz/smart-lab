package de.qaware.smartlab.action.actions.callable.device.deactivation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.qaware.smartlab.action.actions.callable.generic.AbstractActionCallable;
import de.qaware.smartlab.action.actions.info.device.deactivation.DeviceDeactivationInfo;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.actuator.ActuatorId;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeviceDeactivationCallable extends AbstractActionCallable<DeviceDeactivationCallable.ActionArgs, Void> {

    public DeviceDeactivationCallable(DeviceDeactivationInfo deviceDeactivationInfo) {
        super(deviceDeactivationInfo);
    }

    public Void call(IActionService actionService, ActionArgs actionArgs) {
        IActionResult actionResult = actionService.executeAction(this.actionInfo.getActionId(), actionArgs);
        return actionResult.getVoidValue();
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class ActionArgs implements IActionArgs {

        private static final String FIELD_NAME_DEVICE_ID = "deviceId";

        @NonNull
        private final ActuatorId deviceId;

        @JsonCreator
        public static ActionArgs of(@JsonProperty(FIELD_NAME_DEVICE_ID) ActuatorId deviceId) {
            return new ActionArgs(deviceId);
        }
    }
}
