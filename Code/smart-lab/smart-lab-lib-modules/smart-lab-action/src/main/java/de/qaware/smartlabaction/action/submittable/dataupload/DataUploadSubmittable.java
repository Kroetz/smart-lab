package de.qaware.smartlabaction.action.submittable.dataupload;

import de.qaware.smartlabaction.action.info.dataupload.DataUploadInfo;
import de.qaware.smartlabaction.action.submittable.generic.AbstractActionSubmittable;
import de.qaware.smartlabapi.service.connector.action.IActionService;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.data.workgroup.IKnowledgeBaseInfo;
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
        private IKnowledgeBaseInfo knowledgeBaseInfo;

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
