package de.qaware.smartlab.action.actions.callable.data.download;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.qaware.smartlab.action.actions.callable.generic.AbstractActionCallable;
import de.qaware.smartlab.action.actions.info.data.download.DataDownloadInfo;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.workgroup.IProjectBaseInfo;
import de.qaware.smartlab.core.exception.ActionExecutionFailedException;
import de.qaware.smartlab.core.exception.InvalidActionResultException;
import de.qaware.smartlab.core.filesystem.ITempFileManager;
import de.qaware.smartlab.core.filesystem.TempFileManagerConfiguration;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

@Component
@Slf4j
public class DataDownloadCallable extends AbstractActionCallable<DataDownloadCallable.ActionArgs, Path> {

    private final Path downloadsTempFileSubDir;
    private final ITempFileManager tempFileManager;

    public DataDownloadCallable(
            DataDownloadInfo dataDownloadInfo,
            @Qualifier(TempFileManagerConfiguration.QUALIFIER_DOWNLOADS_TEMP_FILE_SUB_DIR) Path downloadsTempFileSubDir,
            ITempFileManager tempFileManager) {
        super(dataDownloadInfo);
        this.downloadsTempFileSubDir = downloadsTempFileSubDir;
        this.tempFileManager = tempFileManager;
    }

    public Path call(IActionService actionService, ActionArgs actionArgs) {
        IActionResult actionResult = actionService.executeAction(this.actionInfo.getActionId(), actionArgs);
        byte[] downloadedData = actionResult.getByteArrayValue().orElseThrow(InvalidActionResultException::new);
        try {
            return this.tempFileManager.saveToTempFile(downloadsTempFileSubDir, downloadedData);
        } catch (IOException e) {
            // TODO: Exception message
            throw new ActionExecutionFailedException(e);
        }
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class ActionArgs implements IActionArgs {

        private static final String FIELD_NAME_PROJECT_BASE_INFO = "projectBaseInfo";
        private static final String FIELD_NAME_FILE_PATH = "filePath";

        @NonNull
        private final IProjectBaseInfo projectBaseInfo;

        @NonNull
        private final String filePath;

        @JsonCreator
        public static ActionArgs of(
                @JsonProperty(FIELD_NAME_PROJECT_BASE_INFO) IProjectBaseInfo projectBaseInfo,
                @JsonProperty(FIELD_NAME_FILE_PATH) String filePath) {
            return new ActionArgs(projectBaseInfo, filePath);
        }
    }
}
