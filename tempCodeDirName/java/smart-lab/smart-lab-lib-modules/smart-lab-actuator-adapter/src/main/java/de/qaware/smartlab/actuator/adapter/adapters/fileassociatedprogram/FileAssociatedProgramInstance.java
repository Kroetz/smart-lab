package de.qaware.smartlab.actuator.adapter.adapters.fileassociatedprogram;

import de.qaware.smartlab.actuator.adapter.windowhandling.windowinfo.IWindowInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.UUID;

@Getter
@Slf4j
@ToString
@EqualsAndHashCode
public class FileAssociatedProgramInstance implements IFileAssociatedProgramInstance {

    private final UUID id;
    private final Process process;
    private final Path openedFile;
    private final IWindowInfo window;

    private FileAssociatedProgramInstance(
            UUID id,
            Process process,
            Path openedFile,
            IWindowInfo window) {
        this.id = id;
        this.process = process;
        this.openedFile = openedFile;
        this.window = window;
    }

    public static FileAssociatedProgramInstance of(
            UUID id,
            Process process,
            Path openedFile,
            IWindowInfo window) {
        return new FileAssociatedProgramInstance(id, process, openedFile, window);
    }
}
