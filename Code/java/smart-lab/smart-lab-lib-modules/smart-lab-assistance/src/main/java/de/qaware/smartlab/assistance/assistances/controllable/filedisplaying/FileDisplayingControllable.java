package de.qaware.smartlab.assistance.assistances.controllable.filedisplaying;

import de.qaware.smartlab.action.actions.submittable.data.download.DataDownloadSubmittable;
import de.qaware.smartlab.action.actions.submittable.file.closing.FileClosingSubmittable;
import de.qaware.smartlab.action.actions.submittable.file.opening.FileOpeningSubmittable;
import de.qaware.smartlab.action.actions.submittable.generic.IActionSubmittable;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.assistance.assistances.controllable.generic.AbstractAssistanceControllable;
import de.qaware.smartlab.assistance.assistances.controllable.generic.IAssistanceControllable;
import de.qaware.smartlab.assistance.assistances.controllable.miscellaneous.factory.AbstractAssistanceControllableFactory;
import de.qaware.smartlab.assistance.assistances.info.filedisplaying.FileDisplayingInfo;
import de.qaware.smartlab.assistance.assistances.info.generic.IAssistanceInfo;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import de.qaware.smartlab.core.exception.AssistanceFailedException;
import de.qaware.smartlab.core.exception.InsufficientContextException;
import de.qaware.smartlab.core.filesystem.ITempFileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import static java.lang.String.format;
import static java.nio.file.Files.readAllBytes;

@Slf4j
public class FileDisplayingControllable extends AbstractAssistanceControllable {

    private final IActionSubmittable<DataDownloadSubmittable.ActionArgs, Path> dataDownload;
    private final IActionSubmittable<FileOpeningSubmittable.ActionArgs, UUID> fileOpening;
    private final IActionSubmittable<FileClosingSubmittable.ActionArgs, Void> fileClosing;
    private final ITempFileManager tempFileManager;
    private UUID programInstanceId;

    private FileDisplayingControllable(
            IAssistanceInfo fileDisplayingInfo,
            IActionSubmittable<DataDownloadSubmittable.ActionArgs, Path> dataDownload,
            IActionSubmittable<FileOpeningSubmittable.ActionArgs, UUID> fileOpening,
            IActionSubmittable<FileClosingSubmittable.ActionArgs, Void> fileClosing,
            ITempFileManager tempFileManager) {
        super(fileDisplayingInfo);
        this.dataDownload = dataDownload;
        this.fileOpening = fileOpening;
        this.fileClosing = fileClosing;
        this.tempFileManager = tempFileManager;
    }

    @Override
    public void begin(IActionService actionService, IAssistanceContext context) {
        // TODO: casting smells
        // TODO: Check for casting exception and throw illegalstateexception
        FileDisplayingInfo.Configuration config = (FileDisplayingInfo.Configuration) context.getAssistanceConfiguration();
        Path downloadedFile = downloadFile(actionService, context, config);
        openFile(actionService, config, downloadedFile);
    }

    private Path downloadFile(
            IActionService actionService,
            IAssistanceContext context,
            FileDisplayingInfo.Configuration config) {
        final DataDownloadSubmittable.ActionArgs dataDownloadArgs = DataDownloadSubmittable.ActionArgs.of(
                context.getWorkgroup().orElseThrow(InsufficientContextException::new).getProjectBaseInfo(),
                config.getFilePath());
        return this.dataDownload.submitExecution(actionService, dataDownloadArgs);
    }

    private void openFile(
            IActionService actionService,
            FileDisplayingInfo.Configuration config,
            Path downloadedFile) {
        final FileOpeningSubmittable.ActionArgs fileOpeningArgs;
        try {
            fileOpeningArgs = FileOpeningSubmittable.ActionArgs.of(
                    config.getProgramId(),
                    config.getDisplayId(),
                    readAllBytes(downloadedFile));
        } catch (IOException e) {
            String errorMessage = format("Could not read downloaded file %s", downloadedFile);
            log.error(errorMessage, e);
            throw new AssistanceFailedException(errorMessage, e);
        }
        this.programInstanceId = this.fileOpening.submitExecution(actionService, fileOpeningArgs);
        this.tempFileManager.markForCleaning(downloadedFile);
    }

    @Override
    public void end(IActionService actionService, IAssistanceContext context) {
        // TODO: casting smells
        // TODO: Check for casting exception and throw illegalstateexception
        FileDisplayingInfo.Configuration config = (FileDisplayingInfo.Configuration) context.getAssistanceConfiguration();
        final FileClosingSubmittable.ActionArgs fileClosingArgs = FileClosingSubmittable.ActionArgs.of(
                config.getProgramId(),
                this.programInstanceId);
        this.fileClosing.submitExecution(actionService, fileClosingArgs);
    }

    @Override
    public void during(IActionService actionService, IAssistanceContext context) {
        // TODO: Implementation
    }

    @Component
    @Slf4j
    public static class Factory extends AbstractAssistanceControllableFactory {

        private final IActionSubmittable<DataDownloadSubmittable.ActionArgs, Path> dataDownload;
        private final IActionSubmittable<FileOpeningSubmittable.ActionArgs, UUID> fileOpening;
        private final IActionSubmittable<FileClosingSubmittable.ActionArgs, Void> fileClosing;
        private final ITempFileManager tempFileManager;

        public Factory(
                IAssistanceInfo fileDisplayingInfo,
                IActionSubmittable<DataDownloadSubmittable.ActionArgs, Path> dataDownload,
                IActionSubmittable<FileOpeningSubmittable.ActionArgs, UUID> fileOpening,
                IActionSubmittable<FileClosingSubmittable.ActionArgs, Void> fileClosing,
                ITempFileManager tempFileManager) {
            super(fileDisplayingInfo);
            this.dataDownload = dataDownload;
            this.fileOpening = fileOpening;
            this.fileClosing = fileClosing;
            this.tempFileManager = tempFileManager;
        }

        @Override
        public IAssistanceControllable newInstance() {
            return new FileDisplayingControllable(
                    this.assistanceInfo,
                    this.dataDownload,
                    this.fileOpening,
                    this.fileClosing,
                    this.tempFileManager);
        }
    }
}
