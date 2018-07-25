package de.qaware.smartlabaction.action.actor.fileassociatedprogram.powerpoint;

import de.qaware.smartlabaction.action.actor.fileassociatedprogram.AbstractFileAssociatedProgramAdapter;
import de.qaware.smartlabaction.action.actor.fileassociatedprogram.FileAssociatedProgramInstance;
import de.qaware.smartlabaction.action.actor.fileassociatedprogram.IFileAssociatedProgramInstance;
import de.qaware.smartlabcore.exception.LocalDeviceException;
import de.qaware.smartlabcore.windowhandling.IWindowHandler;
import de.qaware.smartlabcore.windowhandling.IWindowInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import static java.lang.String.format;

@Component
@Slf4j
public class PowerPointAdapter extends AbstractFileAssociatedProgramAdapter {

    public static final String PROGRAM_TYPE = "powerPoint";
    private static final boolean HAS_LOCAL_API = true;

    private final Path powerPointExecutable;

    public PowerPointAdapter(IWindowHandler windowHandler, Path powerPointExecutable) {
        super(PROGRAM_TYPE, HAS_LOCAL_API, windowHandler);
        this.powerPointExecutable = powerPointExecutable;
    }

    @Override
    public UUID openFile(Path fileToOpen) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        Process powerPointProcess;
        try {
            powerPointProcess = processBuilder
                    // TODO: String literals
                    .command(
                            this.powerPointExecutable.toString(),
                            "/S",   // This command line switch starts PowerPoint in slide show mode (See https://support.office.com/en-us/article/command-line-switches-for-microsoft-office-products-079164cd-4ef5-4178-b235-441737deb3a6?ocmsassetID=HA010153889&CTT=1&CorrelationId=b9563055-15ea-4339-832d-2d0e76a7f7f1&ui=en-US&rs=en-US&ad=US#ID0EAABAAA=PowerPoint,_PowerPoint_Viewer)
                            fileToOpen.toString())
                    .start();
        }
        catch(IOException e) {
            String errorMessage = format("I/O error while opening file %s with PowerPoint", fileToOpen);
            log.error(errorMessage);
            throw new LocalDeviceException(errorMessage, e);
        }
        UUID powerPointInstanceId = UUID.randomUUID();
        IWindowInfo powerPointWindow = this.windowHandler.findPowerPointWindow(fileToOpen);
        IFileAssociatedProgramInstance powerPointInstance = FileAssociatedProgramInstance.of(
                powerPointInstanceId,
                powerPointProcess,
                fileToOpen,
                powerPointWindow);
        this.programInstancesByID.put(powerPointInstance.getId(), powerPointInstance);
        return powerPointInstanceId;
    }

    @Override
    public Path close(UUID powerPointInstanceId) {
        IFileAssociatedProgramInstance programInstance = resolveProgramInstance(powerPointInstanceId);
        programInstance.getProcess().destroy();
        this.programInstancesByID.remove(powerPointInstanceId);
        return programInstance.getOpenedFile();
    }
}
