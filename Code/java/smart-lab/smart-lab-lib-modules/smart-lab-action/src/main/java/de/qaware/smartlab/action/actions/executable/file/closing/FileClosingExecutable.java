package de.qaware.smartlab.action.actions.executable.file.closing;

import de.qaware.smartlab.action.actions.callable.file.closing.FileClosingCallable;
import de.qaware.smartlab.action.actions.executable.generic.AbstractActionExecutable;
import de.qaware.smartlab.action.actions.info.file.closing.FileClosingInfo;
import de.qaware.smartlab.action.result.VoidActionResult;
import de.qaware.smartlab.actuator.adapter.adapters.fileassociatedprogram.IFileAssociatedProgramAdapter;
import de.qaware.smartlab.api.service.connector.actuator.IActuatorManagementService;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.generic.IResolver;
import de.qaware.smartlab.core.exception.action.ActionException;
import de.qaware.smartlab.core.filesystem.ITempFileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Optional;

@Component
@Slf4j
public class FileClosingExecutable extends AbstractActionExecutable<FileClosingCallable.ActionArgs, IFileAssociatedProgramAdapter> {

    private final ITempFileManager tempFileManager;

    public FileClosingExecutable(
            FileClosingInfo fileClosingInfo,
            IActuatorManagementService actuatorManagementService,
            IResolver<String, IFileAssociatedProgramAdapter> fileAssociatedProgramAdapterResolver,
            ITempFileManager tempFileManager) {
        super(
                fileClosingInfo,
                fileAssociatedProgramAdapterResolver,
                actionArgs -> actuatorManagementService.findOne(actionArgs.getProgramId()).getType(),
                actionArgs -> Optional.of(actuatorManagementService.findOne(actionArgs.getProgramId()).getResponsibleDelegate()));
        this.tempFileManager = tempFileManager;
    }

    @Override
    protected IActionResult execute(IFileAssociatedProgramAdapter programAdapter, FileClosingCallable.ActionArgs actionArgs) throws ActionException {
        Path closedFile = programAdapter.close(actionArgs.getProgramInstanceId());
        this.tempFileManager.markForCleaning(closedFile);
        return VoidActionResult.newInstance();
    }
}
