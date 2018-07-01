package de.qaware.smartlabaction.action.executable;

import de.qaware.smartlabaction.action.info.DataUploadInfo;
import de.qaware.smartlabaction.action.result.VoidActionResult;
import de.qaware.smartlabaction.action.submittable.DataUploadSubmittable;
import de.qaware.smartlabapi.service.delegate.IDelegateService;
import de.qaware.smartlabcore.data.action.dataupload.IDataUploadService;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.data.generic.IResolver;
import de.qaware.smartlabcore.exception.UnknownServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataUploadExecutable extends AbstractActionExecutable {

    private IResolver<String, IDataUploadService> dataUploadServiceResolver;

    public DataUploadExecutable(
            DataUploadInfo dataUploadInfo,
            IResolver<String, IDataUploadService> dataUploadServiceResolver) {
        super(dataUploadInfo);
        this.dataUploadServiceResolver = dataUploadServiceResolver;
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
        DataUploadSubmittable.ActionArgs actionArgs = convertToSpecificActionArgs(
                DataUploadSubmittable.ActionArgs.class,
                genericActionArgs);
        IDataUploadService dataUploadService = this.dataUploadServiceResolver
                .resolve(actionArgs.getKnowledgeBaseInfo().getServiceId())
                .orElseThrow(UnknownServiceException::new);
        dataUploadService.upload(
                actionArgs.getKnowledgeBaseInfo(),
                actionArgs.getUploadMessage(),
                actionArgs.getPath(),
                actionArgs.getFileName(),
                actionArgs.getDataToUpload());
        return VoidActionResult.instance();
    }
}
