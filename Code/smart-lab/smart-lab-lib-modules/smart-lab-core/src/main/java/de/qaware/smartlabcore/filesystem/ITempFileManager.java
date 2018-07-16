package de.qaware.smartlabcore.filesystem;

import java.io.IOException;
import java.nio.file.Path;

public interface ITempFileManager {

    Path createEmptyTempFile(Path subDirectory) throws IOException;
    Path saveToTempFile(Path subDirectory, byte[] bytes) throws IOException;
}
