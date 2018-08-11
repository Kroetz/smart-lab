package de.qaware.smartlab.action.actions.callable.file.opening;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.qaware.smartlab.action.actions.callable.generic.AbstractActionCallable;
import de.qaware.smartlab.action.actions.info.file.opening.FileOpeningInfo;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.actuator.ActuatorId;
import lombok.*;
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
        return actionResult.getUuidValue();
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class ActionArgs implements IActionArgs {

        private static final String FIELD_NAME_PROGRAM_ID = "programId";
        private static final String FIELD_NAME_DISPLAY_ID = "displayId";
        private static final String FIELD_NAME_FILE_TO_OPEN = "fileToOpen";

        @NonNull
        private final ActuatorId programId;

        @NonNull
        private final ActuatorId displayId;

        @NonNull
        private final byte[] fileToOpen;

        @JsonCreator
        public static ActionArgs of(
                @JsonProperty(FIELD_NAME_PROGRAM_ID) ActuatorId programId,
                @JsonProperty(FIELD_NAME_DISPLAY_ID) ActuatorId displayId,
                @JsonProperty(FIELD_NAME_FILE_TO_OPEN) byte[] fileToOpen) {
            return new ActionArgs(programId, displayId, fileToOpen);
        }
    }
}
