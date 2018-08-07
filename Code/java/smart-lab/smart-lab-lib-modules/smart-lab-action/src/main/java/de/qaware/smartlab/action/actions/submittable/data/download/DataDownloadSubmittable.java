package de.qaware.smartlab.action.actions.submittable.data.download;

import de.qaware.smartlab.action.actions.info.data.download.DataDownloadInfo;
import de.qaware.smartlab.action.actions.submittable.generic.AbstractActionSubmittable;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.workgroup.IProjectBaseInfo;
import de.qaware.smartlab.core.exception.ActionExecutionFailedException;
import de.qaware.smartlab.core.exception.InvalidActionResultException;
import de.qaware.smartlab.core.filesystem.ITempFileManager;
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
public class DataDownloadSubmittable extends AbstractActionSubmittable<DataDownloadSubmittable.ActionArgs, Path> {

    private final Path downloadsTempFileSubDir;
    private final ITempFileManager tempFileManager;

    public DataDownloadSubmittable(
            DataDownloadInfo dataDownloadInfo,
            // TODO: String literals
            @Qualifier("downloadsTempFileSubDir") Path downloadsTempFileSubDir,
            ITempFileManager tempFileManager) {
        super(dataDownloadInfo);
        this.downloadsTempFileSubDir = downloadsTempFileSubDir;
        this.tempFileManager = tempFileManager;
    }

    public Path submitExecution(IActionService actionService, ActionArgs actionArgs) {
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
    @RequiredArgsConstructor(staticName = "of")     // TODO: Eliminate string literal
    @NoArgsConstructor // TODO: Really necessary for objects being able to serialize/deserialize?
    public static class ActionArgs implements IActionArgs {

        @NonNull
        private IProjectBaseInfo projectBaseInfo;

        @NonNull
        private String filePath;
    }
}