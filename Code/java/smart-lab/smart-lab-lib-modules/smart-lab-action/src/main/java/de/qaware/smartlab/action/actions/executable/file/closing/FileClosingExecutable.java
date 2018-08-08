package de.qaware.smartlab.action.actions.executable.file.closing;

import de.qaware.smartlab.actuator.adapter.adapters.fileassociatedprogram.IFileAssociatedProgramAdapter;
import de.qaware.smartlab.action.actions.executable.generic.AbstractActionExecutable;
import de.qaware.smartlab.action.actions.info.file.closing.FileClosingInfo;
import de.qaware.smartlab.action.result.VoidActionResult;
import de.qaware.smartlab.action.actions.submittable.file.closing.FileClosingSubmittable;
import de.qaware.smartlab.api.service.connector.delegate.IDelegateService;
import de.qaware.smartlab.api.service.connector.device.IDeviceManagementService;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.device.IDevice;
import de.qaware.smartlab.core.data.generic.IResolver;
import de.qaware.smartlab.core.filesystem.ITempFileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@Slf4j
public class FileClosingExecutable extends AbstractActionExecutable {

    private final IDeviceManagementService deviceManagementService;
    private final IResolver<String, IFileAssociatedProgramAdapter> programAdapterResolver;
    private final ITempFileManager tempFileManager;

    public FileClosingExecutable(
            FileClosingInfo fileClosingInfo,
            IDeviceManagementService deviceManagementService,
            IResolver<String, IFileAssociatedProgramAdapter> fileAssociatedProgramAdapterResolver,
            ITempFileManager tempFileManager) {
        super(fileClosingInfo);
        this.deviceManagementService = deviceManagementService;
        this.programAdapterResolver = fileAssociatedProgramAdapterResolver;
        this.tempFileManager = tempFileManager;
    }

    // TODO: too much code duplicates
    public IActionResult execute(String programType, IActionArgs genericActionArgs) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        FileClosingSubmittable.ActionArgs actionArgs = toSpecificArgsType(
                FileClosingSubmittable.ActionArgs.class,
                genericActionArgs);
        IFileAssociatedProgramAdapter programAdapter = this.programAdapterResolver.resolve(programType);
        if(!programAdapter.hasLocalApi()) throw new IllegalStateException();     // TODO: Better exception
        Path closedFile = programAdapter.close(actionArgs.getProgramInstanceId());
        this.tempFileManager.markForCleaning(closedFile);
        return VoidActionResult.newInstance();
    }

    public IActionResult execute(IActionArgs genericActionArgs, IDelegateService delegateService) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        FileClosingSubmittable.ActionArgs actionArgs = toSpecificArgsType(
                FileClosingSubmittable.ActionArgs.class,
                genericActionArgs);
        IDevice fileAssociatedProgram = this.deviceManagementService.findOne(actionArgs.getProgramId());
        String programType = fileAssociatedProgram.getType();
        IFileAssociatedProgramAdapter programAdapter = this.programAdapterResolver.resolve(programType);
        if(programAdapter.hasLocalApi()) return delegateService.executeAction(
                fileAssociatedProgram.getResponsibleDelegate(),
                this.actionInfo.getActionId(),
                programType,
                actionArgs);
        Path closedFile = programAdapter.close(actionArgs.getProgramInstanceId());
        this.tempFileManager.markForCleaning(closedFile);
        return VoidActionResult.newInstance();
    }
}
