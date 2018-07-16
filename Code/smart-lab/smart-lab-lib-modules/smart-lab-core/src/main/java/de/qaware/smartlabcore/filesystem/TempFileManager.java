package de.qaware.smartlabcore.filesystem;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.apache.commons.io.FileUtils.copyInputStreamToFile;

@Component
@Slf4j
public class TempFileManager implements ITempFileManager {

    private final Path tempFileBaseDir;
    private final String tempFileNamePrefix;
    private final String tempFileNameSuffix;

    public TempFileManager(Path tempFileBaseDir, String tempFileNamePrefix, String tempFileNameSuffix) {
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

    @Override
    public Path saveToTempFile(Path subDirectory, InputStream inputStream) throws IOException {
        Path tempFile = createEmptyTempFile(subDirectory);
        copyInputStreamToFile(inputStream, tempFile.toFile());
        return tempFile;
    }
}
