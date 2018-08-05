package de.qaware.smartlabaction.action.executable.file.opening;

import de.qaware.smartlabactuatoradapter.actuator.fileassociatedprogram.IFileAssociatedProgramAdapter;
import de.qaware.smartlabaction.action.executable.generic.AbstractActionExecutable;
import de.qaware.smartlabaction.action.info.file.opening.FileOpeningInfo;
import de.qaware.smartlabaction.action.result.UuidActionResult;
import de.qaware.smartlabaction.action.submittable.file.opening.FileOpeningSubmittable;
import de.qaware.smartlabapi.service.connector.delegate.IDelegateService;
import de.qaware.smartlabapi.service.connector.device.IDeviceManagementService;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.generic.IResolver;
import de.qaware.smartlabcore.exception.ActionExecutionFailedException;
import de.qaware.smartlabcore.exception.UnknownDeviceAdapterException;
import de.qaware.smartlabcore.filesystem.ITempFileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@Component
@Slf4j
public class FileOpeningExecutable extends AbstractActionExecutable {

    private final IDeviceManagementService deviceManagementService;
    private final IResolver<String, IFileAssociatedProgramAdapter> programAdapterResolver;
    private final ITempFileManager tempFileManager;

    public FileOpeningExecutable(
            FileOpeningInfo fileOpeningInfo,
            IDeviceManagementService deviceManagementService,
            IResolver<String, IFileAssociatedProgramAdapter> fileAssociatedProgramAdapterResolver,
            ITempFileManager tempFileManager) {
        super(fileOpeningInfo);
        this.deviceManagementService = deviceManagementService;
        this.programAdapterResolver = fileAssociatedProgramAdapterResolver;
        this.tempFileManager = tempFileManager;
    }

    // TODO: too much code duplicates
    public IActionResult execute(String programType, IActionArgs genericActionArgs) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        FileOpeningSubmittable.ActionArgs actionArgs = convertToSpecificActionArgs(
                FileOpeningSubmittable.ActionArgs.class,
                genericActionArgs);
        IFileAssociatedProgramAdapter programAdapter = this.programAdapterResolver
                .resolve(programType)
                .orElseThrow(UnknownDeviceAdapterException::new);
        if(!programAdapter.hasLocalApi()) throw new IllegalStateException();     // TODO: Better exception
        Path fileToOpen;
        try {
            fileToOpen = this.tempFileManager.saveToTempFile(actionArgs.getFileToOpen());
        } catch (IOException e) {
            // TODO: Exception message
            throw new ActionExecutionFailedException(e);
        }
        UUID programInstanceId = programAdapter.openFile(fileToOpen);
        programAdapter.maximizeOnDisplay(programInstanceId, actionArgs.getDisplayId());
        return UuidActionResult.of(programInstanceId);
    }

    public IActionResult execute(IActionArgs genericActionArgs, IDelegateService delegateService) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        FileOpeningSubmittable.ActionArgs actionArgs = convertToSpecificActionArgs(
                FileOpeningSubmittable.ActionArgs.class,
                genericActionArgs);
        IDevice fileAssociatedProgram = this.deviceManagementService.findOne(actionArgs.getProgramId());
        String programType = fileAssociatedProgram.getType();
        IFileAssociatedProgramAdapter programAdapter = this.programAdapterResolver
                .resolve(programType)
                .orElseThrow(UnknownDeviceAdapterException::new);
        if(programAdapter.hasLocalApi()) return delegateService.executeAction(
                fileAssociatedProgram.getResponsibleDelegate(),
                this.actionInfo.getActionId(),
                programType,
                actionArgs);
        Path fileToOpen;
        try {
            fileToOpen = this.tempFileManager.saveToTempFile(actionArgs.getFileToOpen());
        } catch (IOException e) {
            // TODO: Exception message
            throw new ActionExecutionFailedException(e);
        }
        UUID programInstanceId = programAdapter.openFile(fileToOpen);
        programAdapter.maximizeOnDisplay(programInstanceId, actionArgs.getDisplayId());
        return UuidActionResult.of(programInstanceId);
    }
}
