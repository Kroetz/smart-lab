package de.qaware.smartlabaction.action.actor.fileassociatedprogram;

import lombok.Getter;
import lombok.ToString;

import java.nio.file.Path;

@Getter
@ToString
public class ProgramInstance implements IProgramInstance {

    private final Process process;
    private final Path openedFile;

    private ProgramInstance(Process process, Path openedFile) {
        this.process = process;
        this.openedFile = openedFile;
    }

    public static ProgramInstance of(Process process, Path openedFile) {
        return new ProgramInstance(process, openedFile);
    }
}
