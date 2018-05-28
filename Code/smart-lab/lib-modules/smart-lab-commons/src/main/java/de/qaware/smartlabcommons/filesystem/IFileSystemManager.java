package de.qaware.smartlabcommons.filesystem;

import java.io.IOException;
import java.nio.file.Path;

public interface IFileSystemManager {

    Path createEmptyTempFile(Path subDirectory) throws IOException;
    Path saveToTempFile(Path subDirectory, byte[] bytes) throws IOException;
}