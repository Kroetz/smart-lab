package de.qaware.smartlabaction.action.actor.fileassociatedprogram;

import java.nio.file.Path;

public interface IProgramInstance {

    Process getProcess();
    Path getOpenedFile();
}
