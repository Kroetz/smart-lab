package de.qaware.smartlab.action.actions.executable.file.opening;

import de.qaware.smartlab.actuator.adapter.adapters.fileassociatedprogram.IFileAssociatedProgramAdapter;
import de.qaware.smartlab.action.actions.executable.generic.AbstractActionExecutable;
import de.qaware.smartlab.action.actions.info.file.opening.FileOpeningInfo;
import de.qaware.smartlab.action.result.UuidActionResult;
import de.qaware.smartlab.action.actions.callable.file.opening.FileOpeningCallable;
import de.qaware.smartlab.api.service.connector.delegate.IDelegateService;
import de.qaware.smartlab.api.service.connector.actuator.IActuatorManagementService;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.actuator.IActuator;
import de.qaware.smartlab.core.data.generic.IResolver;
import de.qaware.smartlab.core.exception.ActionExecutionFailedException;
import de.qaware.smartlab.core.filesystem.ITempFileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import static java.lang.String.format;

@Component
@Slf4j
public class FileOpeningExecutable extends AbstractActionExecutable {

    private final IActuatorManagementService actuatorManagementService;
    private final IResolver<String, IFileAssociatedProgramAdapter> programAdapterResolver;
    private final ITempFileManager tempFileManager;

    public FileOpeningExecutable(
            FileOpeningInfo fileOpeningInfo,
            IActuatorManagementService actuatorManagementService,
            IResolver<String, IFileAssociatedProgramAdapter> fileAssociatedProgramAdapterResolver,
            ITempFileManager tempFileManager) {
        super(fileOpeningInfo);
        this.actuatorManagementService = actuatorManagementService;
        this.programAdapterResolver = fileAssociatedProgramAdapterResolver;
        this.tempFileManager = tempFileManager;
    }

    // TODO: too much code duplicates
    public IActionResult execute(String actuatorType, IActionArgs genericActionArgs) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        FileOpeningCallable.ActionArgs actionArgs = toSpecificArgsType(
                FileOpeningCallable.ActionArgs.class,
                genericActionArgs);
        IFileAssociatedProgramAdapter programAdapter = this.programAdapterResolver.resolve(actuatorType);
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
        FileOpeningCallable.ActionArgs actionArgs = toSpecificArgsType(
                FileOpeningCallable.ActionArgs.class,
                genericActionArgs);
        IActuator fileAssociatedProgram = this.actuatorManagementService.findOne(actionArgs.getProgramId());
        String actuatorType = fileAssociatedProgram.getType();
        IFileAssociatedProgramAdapter programAdapter = this.programAdapterResolver.resolve(actuatorType);
        if(programAdapter.hasLocalApi()) return delegateService.executeAction(
                fileAssociatedProgram.getResponsibleDelegate(),
                this.actionInfo.getActionId(),
                actuatorType,
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
