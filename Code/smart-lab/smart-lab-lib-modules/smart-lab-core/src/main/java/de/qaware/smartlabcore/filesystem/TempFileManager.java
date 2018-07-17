package de.qaware.smartlabcore.filesystem;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import static java.nio.file.Files.*;
import static java.util.Objects.isNull;
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
    public Path createEmptyTempFile() throws IOException {
        return createEmptyTempFile(null);
    }

    @Override
    public Path createEmptyTempFile(@Nullable Path subDirectory) throws IOException {
        Path tempDir = isNull(subDirectory) ? this.tempFileBaseDir : this.tempFileBaseDir.resolve(subDirectory);
        createDirectories(tempDir);
        return createTempFile(
                tempDir,
                this.tempFileNamePrefix,
                this.tempFileNameSuffix);
    }

    @Override
    public Path saveToTempFile(byte[] bytes) throws IOException {
        return saveToTempFile(null, bytes);
    }

    @Override
    public Path saveToTempFile(InputStream inputStream) throws IOException {
        return saveToTempFile(null, inputStream);
    }

    @Override
    public Path saveToTempFile(@Nullable Path subDirectory, byte[] bytes) throws IOException {
        Path tempFile = isNull(subDirectory) ? createEmptyTempFile() : createEmptyTempFile(subDirectory);
        write(tempFile, bytes);
        return tempFile;
    }

    @Override
    public Path saveToTempFile(@Nullable Path subDirectory, InputStream inputStream) throws IOException {
        Path tempFile = isNull(subDirectory) ? createEmptyTempFile() : createEmptyTempFile(subDirectory);
        copyInputStreamToFile(inputStream, tempFile.toFile());
        return tempFile;
    }
}
