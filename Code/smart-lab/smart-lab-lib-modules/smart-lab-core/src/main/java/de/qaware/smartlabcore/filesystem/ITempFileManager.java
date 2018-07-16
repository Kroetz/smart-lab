package de.qaware.smartlabcore.filesystem;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public interface ITempFileManager {

    Path createEmptyTempFile(Path subDirectory) throws IOException;
    Path saveToTempFile(Path subDirectory, byte[] bytes) throws IOException;
    Path saveToTempFile(Path subDirectory, InputStream inputStream) throws IOException;
}
