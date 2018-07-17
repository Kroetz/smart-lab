package de.qaware.smartlabcore.filesystem;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public interface ITempFileManager {

    Path createEmptyTempFile() throws IOException;
    Path createEmptyTempFile(Path subDirectory) throws IOException;
    Path saveToTempFile(byte[] bytes) throws IOException;
    Path saveToTempFile(InputStream inputStream) throws IOException;
    Path saveToTempFile(Path subDirectory, byte[] bytes) throws IOException;
    Path saveToTempFile(Path subDirectory, InputStream inputStream) throws IOException;
}
