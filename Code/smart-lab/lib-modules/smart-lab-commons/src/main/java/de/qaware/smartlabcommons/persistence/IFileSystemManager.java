package de.qaware.smartlabcommons.persistence;

import java.io.IOException;
import java.nio.file.Path;

public interface IFileSystemManager {

    Path saveToTempFile(Path subDirectory, byte[] bytes) throws IOException;
}
