package de.qaware.smartlab.action.actions.callable.file.closing;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.qaware.smartlab.action.actions.callable.generic.AbstractActionCallable;
import de.qaware.smartlab.action.actions.info.file.closing.FileClosingInfo;
import de.qaware.smartlab.action.result.VoidActionResult;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.core.action.generic.IActionArgs;
import de.qaware.smartlab.core.action.generic.IActionResult;
import de.qaware.smartlab.core.data.actuator.ActuatorId;
import de.qaware.smartlab.core.exception.action.ActionException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class FileClosingCallable extends AbstractActionCallable<FileClosingCallable.ActionArgs, Void> {

    public FileClosingCallable(FileClosingInfo fileClosingInfo) {
        super(fileClosingInfo);
    }

    public Void call(IActionService actionService, ActionArgs actionArgs) throws ActionException {
        IActionResult actionResult = actionService.executeAction(this.actionInfo.getActionId(), actionArgs);
        return toSpecificResultType(VoidActionResult.class, actionResult).getValue();
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class ActionArgs implements IActionArgs {

        private static final String FIELD_NAME_PROGRAM_ID = "programId";
        private static final String FIELD_NAME_PROGRAM_INSTANCE_ID = "programInstanceId";

        @NonNull
        private final ActuatorId programId;

        @NonNull
        private final UUID programInstanceId;

        @JsonCreator
        public static ActionArgs of(
                @JsonProperty(FIELD_NAME_PROGRAM_ID) ActuatorId programId,
                @JsonProperty(FIELD_NAME_PROGRAM_INSTANCE_ID) UUID programInstanceId) {
            return new ActionArgs(programId, programInstanceId);
        }
    }
}
