package de.qaware.smartlab.action.actions.executable.file.opening;

import de.qaware.smartlab.action.actions.callable.file.opening.FileOpeningCallable;
import de.qaware.smartlab.action.actions.executable.generic.AbstractActionExecutable;
import de.qaware.smartlab.action.actions.info.file.opening.FileOpeningInfo;
import de.qaware.smartlab.action.result.UuidActionResult;
import de.qaware.smartlab.actuator.adapter.adapters.fileassociatedprogram.IFileAssociatedProgramAdapter;
import de.qaware.smartlab.api.service.connector.actuator.IActuatorManagementService;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.generic.IResolver;
import de.qaware.smartlab.core.exception.action.ActionException;
import de.qaware.smartlab.core.filesystem.ITempFileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class FileOpeningExecutable extends AbstractActionExecutable<FileOpeningCallable.ActionArgs, IFileAssociatedProgramAdapter> {

    private final ITempFileManager tempFileManager;

    public FileOpeningExecutable(
            FileOpeningInfo fileOpeningInfo,
            IActuatorManagementService actuatorManagementService,
            IResolver<String, IFileAssociatedProgramAdapter> fileAssociatedProgramAdapterResolver,
            ITempFileManager tempFileManager) {
        super(
                fileOpeningInfo,
                fileAssociatedProgramAdapterResolver,
                actionArgs -> actuatorManagementService.findOne(actionArgs.getProgramId()).getType(),
                actionArgs -> Optional.of(actuatorManagementService.findOne(actionArgs.getProgramId()).getResponsibleDelegate()));
        this.tempFileManager = tempFileManager;
    }

    @Override
    protected IActionResult execute(IFileAssociatedProgramAdapter programAdapter, FileOpeningCallable.ActionArgs actionArgs) throws ActionException {
        Path fileToOpen;
        try {
            fileToOpen = this.tempFileManager.saveToTempFile(actionArgs.getFileToOpen());
        } catch (IOException e) {
            String errorMessage = "Could not save data to temporary file";
            log.error(errorMessage);
            throw new ActionException(errorMessage, e);
        }
        UUID programInstanceId = programAdapter.openFile(fileToOpen);
        programAdapter.maximizeOnDisplay(programInstanceId, actionArgs.getDisplayId());
        return UuidActionResult.of(programInstanceId);
    }
}
