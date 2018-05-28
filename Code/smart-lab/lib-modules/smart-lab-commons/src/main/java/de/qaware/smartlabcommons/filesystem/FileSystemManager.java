package de.qaware.smartlabcommons.filesystem;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@Slf4j
public class FileSystemManager implements IFileSystemManager {

    private final Path tempFileBaseDir;
    private final String tempFileNamePrefix;
    private final String tempFileNameSuffix;

    public FileSystemManager(Path tempFileBaseDir, String tempFileNamePrefix, String tempFileNameSuffix) {
        this.tempFileBaseDir = tempFileBaseDir;
        this.tempFileNamePrefix = tempFileNamePrefix;
        this.tempFileNameSuffix = tempFileNameSuffix;
    }

    @Override
    public Path createEmptyTempFile(Path subDirectory) throws IOException {
        Path tempDir = tempFileBaseDir.resolve(subDirectory);
        Files.createDirectories(tempDir);
        return Files.createTempFile(
                tempDir,
                this.tempFileNamePrefix,
                this.tempFileNameSuffix);
    }

    @Override
    public Path saveToTempFile(Path subDirectory, byte[] bytes) throws IOException {
        Path tempFile = createEmptyTempFile(subDirectory);
        Files.write(tempFile, bytes);
        return tempFile;
    }
}
