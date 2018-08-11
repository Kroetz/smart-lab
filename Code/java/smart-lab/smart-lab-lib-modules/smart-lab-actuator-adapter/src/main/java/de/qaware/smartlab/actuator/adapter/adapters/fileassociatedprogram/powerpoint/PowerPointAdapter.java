package de.qaware.smartlab.actuator.adapter.adapters.fileassociatedprogram.powerpoint;

import de.qaware.smartlab.actuator.adapter.adapters.fileassociatedprogram.AbstractFileAssociatedProgramAdapter;
import de.qaware.smartlab.actuator.adapter.adapters.fileassociatedprogram.FileAssociatedProgramInstance;
import de.qaware.smartlab.actuator.adapter.adapters.fileassociatedprogram.IFileAssociatedProgramInstance;
import de.qaware.smartlab.actuator.adapter.windowhandling.windowhandler.IWindowHandler;
import de.qaware.smartlab.actuator.adapter.windowhandling.windowinfo.IWindowInfo;
import de.qaware.smartlab.core.constant.Miscellaneous;
import de.qaware.smartlab.core.exception.actuator.ActuatorException;
import de.qaware.smartlab.core.miscellaneous.Language;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;
import java.util.UUID;

import static java.lang.String.format;
import static java.util.UUID.randomUUID;

@Component
@ConditionalOnProperty(
        prefix = PowerPointConfiguration.Properties.PREFIX,
        name = PowerPointConfiguration.Properties.ENABLED,
        havingValue = Miscellaneous.TRUE)
@Slf4j
public class PowerPointAdapter extends AbstractFileAssociatedProgramAdapter {

    public static final String ACTUATOR_TYPE = "powerPoint";
    private static final boolean HAS_LOCAL_API = true;

    /**
     * This command line switch starts PowerPoint in slide show mode
     * See https://support.office.com/en-us/article/command-line-switches-for-microsoft-office-products-079164cd-4ef5-4178-b235-441737deb3a6?ocmsassetID=HA010153889&CTT=1&CorrelationId=b9563055-15ea-4339-832d-2d0e76a7f7f1&ui=en-US&rs=en-US&ad=US#ID0EAABAAA=PowerPoint,_PowerPoint_Viewer
     */
    private static final String COMMAND_LINE_SWITCH_SLIDE_SHOW_MODE = "/S";

    private static final String POWER_POINT_WINDOW_TITLE_TEMPLATE_DE_DE = "PowerPoint-Bildschirmpräsentation  -  %s";
    private static final String POWER_POINT_WINDOW_TITLE_TEMPLATE_EN_US = "PowerPoint Slide Show  -  %s";

    private final Path powerPointExecutable;

    public PowerPointAdapter(
            IWindowHandler windowHandler,
            @Qualifier(PowerPointConfiguration.QUALIFIER_POWER_POINT_EXECUTABLE) Path powerPointExecutable) {
        super(ACTUATOR_TYPE, HAS_LOCAL_API, windowHandler);
        this.powerPointExecutable = powerPointExecutable;
    }

    public static String getWindowTitle(Path openedFile) throws IllegalStateException {
        Language systemLanguage = Language.of(Locale.getDefault().toLanguageTag());
        switch(systemLanguage) {
            case EN_US:
            case EN_GB:
                return format(POWER_POINT_WINDOW_TITLE_TEMPLATE_EN_US, openedFile.getFileName());
            case DE_DE:
                return format(POWER_POINT_WINDOW_TITLE_TEMPLATE_DE_DE, openedFile.getFileName());
            default:
                String errorMessage = format("System language \"%s\" is not supported", systemLanguage.toString());
                log.error(errorMessage);
                throw new IllegalStateException(errorMessage);
        }
    }

    @Override
    public UUID openFile(Path fileToOpen) throws ActuatorException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        Process powerPointProcess;
        try {
            powerPointProcess = processBuilder
                    .command(
                            this.powerPointExecutable.toString(),
                            COMMAND_LINE_SWITCH_SLIDE_SHOW_MODE,
                            fileToOpen.toString())
                    .start();
        }
        catch(IOException e) {
            String errorMessage = format("I/O error while opening file %s with PowerPoint", fileToOpen);
            log.error(errorMessage);
            throw new ActuatorException(errorMessage, e);
        }
        UUID powerPointInstanceId = randomUUID();
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
    public Path close(UUID powerPointInstanceId) throws ActuatorException {
        IFileAssociatedProgramInstance programInstance = resolveProgramInstance(powerPointInstanceId);
        programInstance.getProcess().destroy();
        this.programInstancesByID.remove(powerPointInstanceId);
        return programInstance.getOpenedFile();
    }
}
