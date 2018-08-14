package de.qaware.smartlab.actuator.adapter.adapters.fileassociatedprogram;

import de.qaware.smartlab.actuator.adapter.windowhandling.windowinfo.IWindowInfo;

import java.nio.file.Path;
import java.util.UUID;

public interface IFileAssociatedProgramInstance {

    UUID getId();
    Process getProcess();
    Path getOpenedFile();
    IWindowInfo getWindow();
}
