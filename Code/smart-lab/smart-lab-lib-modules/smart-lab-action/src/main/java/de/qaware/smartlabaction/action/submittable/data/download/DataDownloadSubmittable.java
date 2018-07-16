package de.qaware.smartlabaction.action.submittable.data.download;

import de.qaware.smartlabaction.action.info.data.download.DataDownloadInfo;
import de.qaware.smartlabaction.action.submittable.generic.AbstractActionSubmittable;
import de.qaware.smartlabapi.service.connector.action.IActionService;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.data.workgroup.IKnowledgeBaseInfo;
import de.qaware.smartlabcore.exception.ActionExecutionFailedException;
import de.qaware.smartlabcore.exception.InvalidActionResultException;
import de.qaware.smartlabcore.filesystem.ITempFileManager;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

@Component
@Slf4j
public class DataDownloadSubmittable extends AbstractActionSubmittable<DataDownloadSubmittable.ActionArgs, Path> {

    private final Path downloadedFilesTempFileSubDir;
    private final ITempFileManager tempFileManager;

    public DataDownloadSubmittable(
            DataDownloadInfo dataDownloadInfo,
            Path downloadedFilesTempFileSubDir,
            ITempFileManager tempFileManager) {
        super(dataDownloadInfo);
        this.downloadedFilesTempFileSubDir = downloadedFilesTempFileSubDir;
        this.tempFileManager = tempFileManager;
    }

    public Path submitExecution(IActionService actionService, ActionArgs actionArgs) {
        IActionResult actionResult = actionService.executeAction(this.actionInfo.getActionId(), actionArgs);
        byte[] downloadedData = actionResult.getByteArrayValue().orElseThrow(InvalidActionResultException::new);
        try {
            return this.tempFileManager.saveToTempFile(downloadedFilesTempFileSubDir, downloadedData);
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
        private IKnowledgeBaseInfo knowledgeBaseInfo;

        @NonNull
        private String dir;

        @NonNull
        private String fileName;
    }
}
