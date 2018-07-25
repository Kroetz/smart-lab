package de.qaware.smartlabaction.action.actor.fileassociatedprogram;

import de.qaware.smartlabcore.windowhandling.IWindowInfo;

import java.nio.file.Path;
import java.util.UUID;

public interface IFileAssociatedProgramInstance {

    UUID getId();
    Process getProcess();
    Path getOpenedFile();
    IWindowInfo getWindow();
}
