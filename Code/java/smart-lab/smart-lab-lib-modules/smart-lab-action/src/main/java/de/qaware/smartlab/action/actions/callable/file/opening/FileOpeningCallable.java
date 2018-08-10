package de.qaware.smartlab.action.actions.callable.file.opening;

import de.qaware.smartlab.action.actions.info.file.opening.FileOpeningInfo;
import de.qaware.smartlab.action.actions.callable.generic.AbstractActionCallable;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.actuator.ActuatorId;
import de.qaware.smartlab.core.exception.InvalidActionResultException;
import de.qaware.smartlab.core.miscellaneous.Constants;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class FileOpeningCallable extends AbstractActionCallable<FileOpeningCallable.ActionArgs, UUID> {

    public FileOpeningCallable(FileOpeningInfo fileOpeningInfo) {
        super(fileOpeningInfo);
    }

    public UUID call(IActionService actionService, ActionArgs actionArgs) {
        IActionResult actionResult = actionService.executeAction(this.actionInfo.getActionId(), actionArgs);
        return actionResult.getUuidValue().orElseThrow(InvalidActionResultException::new);
    }

    @Data
    @RequiredArgsConstructor(staticName = "of")
    @NoArgsConstructor // TODO: Really necessary for objects being able to serialize/deserialize?
    public static class ActionArgs implements IActionArgs {

        @NonNull
        private ActuatorId programId;

        @NonNull
        private ActuatorId displayId;

        @NonNull
        private byte[] fileToOpen;
    }
}
