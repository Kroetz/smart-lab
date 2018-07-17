package de.qaware.smartlabaction.action.executable.deviceadapter.fileassociatedprogram;

import java.nio.file.Path;

public interface IProgramInstance {

    Process getProcess();
    Path getOpenedFile();
}
