package de.qaware.smartlabactuatoradapter.actuator.fileassociatedprogram;

import de.qaware.smartlabactuatoradapter.actuator.windowhandling.windowinfo.IWindowInfo;

import java.nio.file.Path;
import java.util.UUID;

public interface IFileAssociatedProgramInstance {

    UUID getId();
    Process getProcess();
    Path getOpenedFile();
    IWindowInfo getWindow();
}
