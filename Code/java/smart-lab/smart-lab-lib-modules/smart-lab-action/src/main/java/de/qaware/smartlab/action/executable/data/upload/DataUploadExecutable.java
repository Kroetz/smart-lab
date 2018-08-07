package de.qaware.smartlab.action.executable.data.upload;

import de.qaware.smartlab.action.executable.generic.AbstractActionExecutable;
import de.qaware.smartlab.action.info.data.upload.DataUploadInfo;
import de.qaware.smartlab.action.result.VoidActionResult;
import de.qaware.smartlab.action.submittable.data.upload.DataUploadSubmittable;
import de.qaware.smartlabapi.service.connector.delegate.IDelegateService;
import de.qaware.smartlabcore.data.action.dataupload.IDataUploadService;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.data.generic.IResolver;
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
        return VoidActionResult.newInstance();
    }

    @Override
    public IActionResult execute(IActionArgs genericActionArgs, IDelegateService delegateService) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        DataUploadSubmittable.ActionArgs actionArgs = convertToSpecificActionArgs(
                DataUploadSubmittable.ActionArgs.class,
                genericActionArgs);
        String projectBaseService = actionArgs.getProjectBaseInfo().getServiceId();
        IDataUploadService dataUploadService = this.dataUploadServiceResolver.resolve(projectBaseService);
        dataUploadService.upload(
                actionArgs.getProjectBaseInfo(),
                actionArgs.getUploadMessage(),
                actionArgs.getDir(),
                actionArgs.getFileName(),
                actionArgs.getDataToUpload());
        return VoidActionResult.newInstance();
    }
}
