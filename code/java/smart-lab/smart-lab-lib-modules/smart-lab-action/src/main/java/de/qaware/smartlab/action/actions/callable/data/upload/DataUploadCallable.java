package de.qaware.smartlab.action.actions.callable.data.upload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.qaware.smartlab.action.actions.callable.generic.AbstractActionCallable;
import de.qaware.smartlab.action.actions.info.data.upload.DataUploadInfo;
import de.qaware.smartlab.action.result.VoidActionResult;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.workgroup.IProjectBaseInfo;
import de.qaware.smartlab.core.exception.action.ActionException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataUploadCallable extends AbstractActionCallable<DataUploadCallable.ActionArgs, Void> {

    public DataUploadCallable(DataUploadInfo dataUploadInfo) {
        super(dataUploadInfo);
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

        private static final String FIELD_NAME_PROJECT_BASE_INFO = "projectBaseInfo";
        private static final String FIELD_NAME_DIR = "dir";
        private static final String FIELD_NAME_FILE_NAME = "fileName";
        private static final String FIELD_NAME_UPLOAD_MESSAGE = "uploadMessage";
        private static final String FIELD_NAME_DATA_TO_UPLOAD = "dataToUpload";

        @NonNull
        private final IProjectBaseInfo projectBaseInfo;

        @NonNull
        private final String dir;

        @NonNull
        private final String fileName;

        @NonNull
        private final String uploadMessage;

        @NonNull
        private final byte[] dataToUpload;

        @JsonCreator
        public static ActionArgs of(
                @JsonProperty(FIELD_NAME_PROJECT_BASE_INFO) IProjectBaseInfo projectBaseInfo,
                @JsonProperty(FIELD_NAME_DIR) String dir,
                @JsonProperty(FIELD_NAME_FILE_NAME) String fileName,
                @JsonProperty(FIELD_NAME_UPLOAD_MESSAGE) String uploadMessage,
                @JsonProperty(FIELD_NAME_DATA_TO_UPLOAD) byte[] dataToUpload) {
            return new ActionArgs(
                    projectBaseInfo,
                    dir,
                    fileName,
                    uploadMessage,
                    dataToUpload);
        }
    }
}
