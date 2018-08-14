package de.qaware.smartlab.action.actions.executable.data.download;

import de.qaware.smartlab.action.actions.callable.data.download.DataDownloadCallable;
import de.qaware.smartlab.action.actions.executable.generic.AbstractActionExecutable;
import de.qaware.smartlab.action.actions.info.data.download.DataDownloadInfo;
import de.qaware.smartlab.action.result.ByteArrayActionResult;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.IDataDownloadService;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.generic.IResolver;
import de.qaware.smartlab.core.exception.action.ActionException;
import de.qaware.smartlab.core.filesystem.ITempFileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import static java.lang.String.format;
import static java.nio.file.Files.readAllBytes;

@Component
@Slf4j
public class DataDownloadExecutable extends AbstractActionExecutable<DataDownloadCallable.ActionArgs, IDataDownloadService> {

    private final ITempFileManager tempFileManager;

    public DataDownloadExecutable(
            DataDownloadInfo dataDownloadInfo,
            IResolver<String, IDataDownloadService> dataDownloadServiceResolver,
            ITempFileManager tempFileManager) {
        super(
                dataDownloadInfo,
                dataDownloadServiceResolver,
                actionArgs -> actionArgs.getProjectBaseInfo().getActuatorType(),
                actionArgs -> Optional.empty());
        this.tempFileManager = tempFileManager;
    }

    @Override
    protected IActionResult execute(IDataDownloadService dataDownloadService, DataDownloadCallable.ActionArgs actionArgs) throws ActionException {
        Path downloadedFile = dataDownloadService.download(
                actionArgs.getProjectBaseInfo(),
                actionArgs.getFilePath());
        IActionResult actionResult;
        try {
            actionResult = ByteArrayActionResult.of(readAllBytes(downloadedFile));
            this.tempFileManager.markForCleaning(downloadedFile);
        } catch (IOException e) {
            String errorMessage = format("Could not read data from downloaded file %s", downloadedFile);
            log.error(errorMessage);
            throw new ActionException(errorMessage, e);
        }
        return actionResult;
    }
}
