package de.qaware.smartlabaction.action.submittable;

import de.qaware.smartlabaction.action.info.MicrophoneActivationInfo;
import de.qaware.smartlabapi.service.action.IActionService;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.room.RoomId;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MicrophoneActivationSubmittable extends AbstractActionSubmittable<MicrophoneActivationSubmittable.ActionArgs, Void> {

    public MicrophoneActivationSubmittable(MicrophoneActivationInfo microphoneActivationInfo) {
        super(microphoneActivationInfo);
    }

    public Void submitExecution(IActionService actionService, ActionArgs actionArgs) {
        IActionResult actionResult = actionService.executeAction(this.actionInfo.getActionId(), actionArgs);
        return actionResult.getVoidValue();
    }

    @Data
    @RequiredArgsConstructor(staticName = "of")     // TODO: Eliminate string literal
    @NoArgsConstructor // TODO: Really necessary for objects being able to serialize/deserialize?
    public static class ActionArgs implements IActionArgs {

        @NonNull
        private RoomId roomId;

        @NonNull
        private DeviceId deviceId;
    }
}
