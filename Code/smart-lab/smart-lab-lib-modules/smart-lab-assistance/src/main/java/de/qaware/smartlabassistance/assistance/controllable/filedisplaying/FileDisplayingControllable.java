package de.qaware.smartlabassistance.assistance.controllable.filedisplaying;

import de.qaware.smartlabaction.action.submittable.data.download.DataDownloadSubmittable;
import de.qaware.smartlabaction.action.submittable.file.closing.FileClosingSubmittable;
import de.qaware.smartlabaction.action.submittable.file.opening.FileOpeningSubmittable;
import de.qaware.smartlabaction.action.submittable.generic.IActionSubmittable;
import de.qaware.smartlabapi.service.connector.action.IActionService;
import de.qaware.smartlabassistance.assistance.controllable.generic.AbstractAssistanceControllable;
import de.qaware.smartlabassistance.assistance.controllable.generic.IAssistanceControllable;
import de.qaware.smartlabassistance.assistance.controllable.miscellaneous.factory.AbstractAssistanceControllableFactory;
import de.qaware.smartlabassistance.assistance.info.filedisplaying.FileDisplayingInfo;
import de.qaware.smartlabassistance.assistance.info.generic.IAssistanceInfo;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import de.qaware.smartlabcore.exception.AssistanceFailedException;
import de.qaware.smartlabcore.exception.InsufficientContextException;
import de.qaware.smartlabcore.filesystem.ITempFileManager;
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

        final DataDownloadSubmittable.ActionArgs dataDownloadArgs = DataDownloadSubmittable.ActionArgs.of(
                context.getWorkgroup().orElseThrow(InsufficientContextException::new).getKnowledgeBaseInfo(),
                config.getFilePath());
        Path downloadedFile = this.dataDownload.submitExecution(actionService, dataDownloadArgs);

        final FileOpeningSubmittable.ActionArgs fileOpeningArgs;
        try {
            fileOpeningArgs = FileOpeningSubmittable.ActionArgs.of(
                    config.getProgramId(),
                    readAllBytes(downloadedFile));
        } catch (IOException e) {
            String errorMessage = format("Could not read downloaded file %s", downloadedFile);
            log.error(errorMessage);
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
    public void update(IActionService actionService, IAssistanceContext context) {
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
