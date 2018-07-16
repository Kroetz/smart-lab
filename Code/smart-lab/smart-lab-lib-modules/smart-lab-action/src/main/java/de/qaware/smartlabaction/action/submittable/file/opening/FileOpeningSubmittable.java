package de.qaware.smartlabaction.action.submittable.file.opening;

import de.qaware.smartlabaction.action.info.file.opening.FileOpeningInfo;
import de.qaware.smartlabaction.action.submittable.generic.AbstractActionSubmittable;
import de.qaware.smartlabapi.service.connector.action.IActionService;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.exception.InvalidActionResultException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class FileOpeningSubmittable extends AbstractActionSubmittable<FileOpeningSubmittable.ActionArgs, UUID> {

    public FileOpeningSubmittable(FileOpeningInfo fileOpeningInfo) {
        super(fileOpeningInfo);
    }

    public UUID submitExecution(IActionService actionService, ActionArgs actionArgs) {
        IActionResult actionResult = actionService.executeAction(this.actionInfo.getActionId(), actionArgs);
        return actionResult.getUuidValue().orElseThrow(InvalidActionResultException::new);
    }

    @Data
    @RequiredArgsConstructor(staticName = "of")     // TODO: Eliminate string literal
    @NoArgsConstructor // TODO: Really necessary for objects being able to serialize/deserialize?
    public static class ActionArgs implements IActionArgs {

        @NonNull
        private DeviceId programId;

        @NonNull
        private String fileToOpen;
    }
}
