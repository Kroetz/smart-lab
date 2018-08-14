package de.qaware.smartlab.assistance.assistances.controllable.filedisplaying;

import de.qaware.smartlab.action.actions.callable.data.download.DataDownloadCallable;
import de.qaware.smartlab.action.actions.callable.file.closing.FileClosingCallable;
import de.qaware.smartlab.action.actions.callable.file.opening.FileOpeningCallable;
import de.qaware.smartlab.action.actions.callable.generic.IActionCallable;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.assistance.assistances.controllable.generic.AbstractAssistanceControllable;
import de.qaware.smartlab.assistance.assistances.controllable.generic.IAssistanceControllable;
import de.qaware.smartlab.assistance.assistances.controllable.miscellaneous.factory.AbstractAssistanceControllableFactory;
import de.qaware.smartlab.assistance.assistances.info.filedisplaying.FileDisplayingInfo;
import de.qaware.smartlab.assistance.assistances.info.generic.IAssistanceInfo;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import de.qaware.smartlab.core.exception.assistance.AssistanceException;
import de.qaware.smartlab.core.exception.context.InsufficientContextException;
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

    private final IActionCallable<DataDownloadCallable.ActionArgs, Path> dataDownload;
    private final IActionCallable<FileOpeningCallable.ActionArgs, UUID> fileOpening;
    private final IActionCallable<FileClosingCallable.ActionArgs, Void> fileClosing;
    private final ITempFileManager tempFileManager;
    private UUID programInstanceId;

    private FileDisplayingControllable(
            IAssistanceInfo fileDisplayingInfo,
            IActionCallable<DataDownloadCallable.ActionArgs, Path> dataDownload,
            IActionCallable<FileOpeningCallable.ActionArgs, UUID> fileOpening,
            IActionCallable<FileClosingCallable.ActionArgs, Void> fileClosing,
            ITempFileManager tempFileManager) {
        super(fileDisplayingInfo);
        this.dataDownload = dataDownload;
        this.fileOpening = fileOpening;
        this.fileClosing = fileClosing;
        this.tempFileManager = tempFileManager;
    }

    @Override
    public void begin(IActionService actionService, IAssistanceContext context) throws AssistanceException, InsufficientContextException {
        FileDisplayingInfo.Configuration config = toSpecificConfigType(
                FileDisplayingInfo.Configuration.class,
                context.getAssistanceConfiguration());
        Path downloadedFile = downloadFile(actionService, context, config);
        openFile(actionService, config, downloadedFile);
    }

    private Path downloadFile(
            IActionService actionService,
            IAssistanceContext context,
            FileDisplayingInfo.Configuration config) {
        final DataDownloadCallable.ActionArgs dataDownloadArgs = DataDownloadCallable.ActionArgs.of(
                context.getWorkgroup().getProjectBaseInfo(),
                config.getFilePath());
        return this.dataDownload.call(actionService, dataDownloadArgs);
    }

    private void openFile(
            IActionService actionService,
            FileDisplayingInfo.Configuration config,
            Path downloadedFile) {
        final FileOpeningCallable.ActionArgs fileOpeningArgs;
        try {
            fileOpeningArgs = FileOpeningCallable.ActionArgs.of(
                    config.getProgramId(),
                    config.getDisplayId(),
                    readAllBytes(downloadedFile));
        } catch (IOException e) {
            String errorMessage = format("Could not read downloaded file %s", downloadedFile);
            log.error(errorMessage);
            throw new AssistanceException(errorMessage, e);
        }
        this.programInstanceId = this.fileOpening.call(actionService, fileOpeningArgs);
        this.tempFileManager.markForCleaning(downloadedFile);
    }

    @Override
    public void end(IActionService actionService, IAssistanceContext context) throws AssistanceException, InsufficientContextException {
        FileDisplayingInfo.Configuration config = toSpecificConfigType(
                FileDisplayingInfo.Configuration.class,
                context.getAssistanceConfiguration());
        final FileClosingCallable.ActionArgs fileClosingArgs = FileClosingCallable.ActionArgs.of(
                config.getProgramId(),
                this.programInstanceId);
        this.fileClosing.call(actionService, fileClosingArgs);
    }

    @Component
    @Slf4j
    public static class Factory extends AbstractAssistanceControllableFactory {

        private final IActionCallable<DataDownloadCallable.ActionArgs, Path> dataDownload;
        private final IActionCallable<FileOpeningCallable.ActionArgs, UUID> fileOpening;
        private final IActionCallable<FileClosingCallable.ActionArgs, Void> fileClosing;
        private final ITempFileManager tempFileManager;

        public Factory(
                IAssistanceInfo fileDisplayingInfo,
                IActionCallable<DataDownloadCallable.ActionArgs, Path> dataDownload,
                IActionCallable<FileOpeningCallable.ActionArgs, UUID> fileOpening,
                IActionCallable<FileClosingCallable.ActionArgs, Void> fileClosing,
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
