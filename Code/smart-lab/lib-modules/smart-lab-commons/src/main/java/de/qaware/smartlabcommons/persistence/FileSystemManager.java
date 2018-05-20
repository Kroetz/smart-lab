package de.qaware.smartlabcommons.persistence;

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
    public Path saveToTempFile(Path subDirectory, byte[] bytes) throws IOException {
        Path tempDir = tempFileBaseDir.resolve(subDirectory);
        if(Files.notExists(tempDir)) Files.createTempDirectory(tempDir, this.tempFileNamePrefix);
        Path tempFile = Files.createTempFile(
                tempDir,
                this.tempFileNamePrefix,
                this.tempFileNameSuffix);
        Files.write(tempFile, bytes);
        return tempFile;
    }
}
