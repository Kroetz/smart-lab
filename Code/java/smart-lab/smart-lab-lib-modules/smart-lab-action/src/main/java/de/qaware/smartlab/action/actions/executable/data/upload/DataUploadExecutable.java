package de.qaware.smartlab.action.actions.executable.data.upload;

import de.qaware.smartlab.action.actions.callable.data.upload.DataUploadCallable;
import de.qaware.smartlab.action.actions.executable.generic.AbstractActionExecutable;
import de.qaware.smartlab.action.actions.info.data.upload.DataUploadInfo;
import de.qaware.smartlab.action.result.VoidActionResult;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.IDataUploadService;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.generic.IResolver;
import de.qaware.smartlab.core.exception.action.ActionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class DataUploadExecutable extends AbstractActionExecutable<DataUploadCallable.ActionArgs, IDataUploadService> {

    public DataUploadExecutable(
            DataUploadInfo dataUploadInfo,
            IResolver<String, IDataUploadService> dataUploadServiceResolver) {
        super(
                dataUploadInfo,
                dataUploadServiceResolver,
                actionArgs -> actionArgs.getProjectBaseInfo().getActuatorType(),
                actionArgs -> Optional.empty());
    }

    @Override
    protected IActionResult execute(IDataUploadService dataUploadService, DataUploadCallable.ActionArgs actionArgs) throws ActionException {
        dataUploadService.upload(
                actionArgs.getProjectBaseInfo(),
                actionArgs.getUploadMessage(),
                actionArgs.getDir(),
                actionArgs.getFileName(),
                actionArgs.getDataToUpload());
        return VoidActionResult.newInstance();
    }
}
