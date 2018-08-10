package de.qaware.smartlab.action.actions.callable.data.download;

import de.qaware.smartlab.action.actions.info.data.download.DataDownloadInfo;
import de.qaware.smartlab.action.actions.callable.generic.AbstractActionCallable;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.workgroup.IProjectBaseInfo;
import de.qaware.smartlab.core.exception.ActionExecutionFailedException;
import de.qaware.smartlab.core.exception.InvalidActionResultException;
import de.qaware.smartlab.core.filesystem.ITempFileManager;
import de.qaware.smartlab.core.filesystem.TempFileManagerConfiguration;
import de.qaware.smartlab.core.miscellaneous.Constants;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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

    @Data
    @RequiredArgsConstructor(staticName = "of")
    @NoArgsConstructor // TODO: Really necessary for objects being able to serialize/deserialize?
    public static class ActionArgs implements IActionArgs {

        @NonNull
        private IProjectBaseInfo projectBaseInfo;

        @NonNull
        private String filePath;
    }
}
