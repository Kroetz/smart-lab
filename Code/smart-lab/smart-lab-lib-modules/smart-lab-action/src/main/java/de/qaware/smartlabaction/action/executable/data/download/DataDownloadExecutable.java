package de.qaware.smartlabaction.action.executable.data.download;

import de.qaware.smartlabaction.action.executable.generic.AbstractActionExecutable;
import de.qaware.smartlabaction.action.info.data.download.DataDownloadInfo;
import de.qaware.smartlabaction.action.result.ByteArrayActionResult;
import de.qaware.smartlabaction.action.result.VoidActionResult;
import de.qaware.smartlabaction.action.submittable.data.download.DataDownloadSubmittable;
import de.qaware.smartlabapi.service.connector.delegate.IDelegateService;
import de.qaware.smartlabcore.data.action.datadownload.IDataDownloadService;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.data.generic.IResolver;
import de.qaware.smartlabcore.exception.ActionExecutionFailedException;
import de.qaware.smartlabcore.exception.UnknownServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.deleteIfExists;
import static java.nio.file.Files.readAllBytes;

@Component
@Slf4j
public class DataDownloadExecutable extends AbstractActionExecutable {

    private IResolver<String, IDataDownloadService> dataDownloadServiceResolver;

    public DataDownloadExecutable(
            DataDownloadInfo dataDownloadInfo,
            IResolver<String, IDataDownloadService> dataDownloadServiceResolver) {
        super(dataDownloadInfo);
        this.dataDownloadServiceResolver = dataDownloadServiceResolver;
    }

    @Override
    public IActionResult execute(String deviceType, IActionArgs genericActionArgs) {
        // TODO: Is never needed because there is no local execution for a web service.
        return VoidActionResult.instance();
    }

    @Override
    public IActionResult execute(IActionArgs genericActionArgs, IDelegateService delegateService) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        DataDownloadSubmittable.ActionArgs actionArgs = convertToSpecificActionArgs(
                DataDownloadSubmittable.ActionArgs.class,
                genericActionArgs);
        IDataDownloadService dataDownloadService = this.dataDownloadServiceResolver
                .resolve(actionArgs.getKnowledgeBaseInfo().getServiceId())
                .orElseThrow(UnknownServiceException::new);
        Path downloadedFile = dataDownloadService.download(
                actionArgs.getKnowledgeBaseInfo(),
                actionArgs.getFilePath());
        IActionResult actionResult;
        try {
            actionResult = ByteArrayActionResult.of(readAllBytes(downloadedFile));
            deleteIfExists(downloadedFile);
        } catch (IOException e) {
            // TODO: Exception message
            throw new ActionExecutionFailedException(e);
        }
        return actionResult;
    }
}
