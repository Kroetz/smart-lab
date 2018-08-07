package de.qaware.smartlab.action.actions.submittable.data.upload;

import de.qaware.smartlab.action.actions.info.data.upload.DataUploadInfo;
import de.qaware.smartlab.action.actions.submittable.generic.AbstractActionSubmittable;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.workgroup.IProjectBaseInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataUploadSubmittable extends AbstractActionSubmittable<DataUploadSubmittable.ActionArgs, Void> {

    public DataUploadSubmittable(DataUploadInfo dataUploadInfo) {
        super(dataUploadInfo);
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
        private IProjectBaseInfo projectBaseInfo;

        @NonNull
        private String dir;

        @NonNull
        private String fileName;

        @NonNull
        private String uploadMessage;

        @NonNull
        private String dataToUpload;
    }
}
