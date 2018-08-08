package de.qaware.smartlab.action.actions.executable.data.download;

import de.qaware.smartlab.action.actions.executable.generic.AbstractActionExecutable;
import de.qaware.smartlab.action.actions.info.data.download.DataDownloadInfo;
import de.qaware.smartlab.action.result.ByteArrayActionResult;
import de.qaware.smartlab.action.result.VoidActionResult;
import de.qaware.smartlab.action.actions.submittable.data.download.DataDownloadSubmittable;
import de.qaware.smartlab.api.service.connector.delegate.IDelegateService;
import de.qaware.smartlab.core.data.action.datadownload.IDataDownloadService;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.generic.IResolver;
import de.qaware.smartlab.core.exception.ActionExecutionFailedException;
import de.qaware.smartlab.core.filesystem.ITempFileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.readAllBytes;

@Component
@Slf4j
public class DataDownloadExecutable extends AbstractActionExecutable {

    private IResolver<String, IDataDownloadService> dataDownloadServiceResolver;
    private final ITempFileManager tempFileManager;

    public DataDownloadExecutable(
            DataDownloadInfo dataDownloadInfo,
            IResolver<String, IDataDownloadService> dataDownloadServiceResolver,
            ITempFileManager tempFileManager) {
        super(dataDownloadInfo);
        this.dataDownloadServiceResolver = dataDownloadServiceResolver;
        this.tempFileManager = tempFileManager;
    }

    @Override
    public IActionResult execute(String actuatorType, IActionArgs genericActionArgs) {
        // TODO: Is never needed because there is no local execution for a web service.
        return VoidActionResult.newInstance();
    }

    @Override
    public IActionResult execute(IActionArgs genericActionArgs, IDelegateService delegateService) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        DataDownloadSubmittable.ActionArgs actionArgs = toSpecificArgsType(
                DataDownloadSubmittable.ActionArgs.class,
                genericActionArgs);
        String actuatorType = actionArgs.getProjectBaseInfo().getActuatorType();
        IDataDownloadService dataDownloadService = this.dataDownloadServiceResolver.resolve(actuatorType);
        Path downloadedFile = dataDownloadService.download(
                actionArgs.getProjectBaseInfo(),
                actionArgs.getFilePath());
        IActionResult actionResult;
        try {
            actionResult = ByteArrayActionResult.of(readAllBytes(downloadedFile));
            this.tempFileManager.markForCleaning(downloadedFile);
        } catch (IOException e) {
            // TODO: Exception message
            throw new ActionExecutionFailedException(e);
        }
        return actionResult;
    }
}
