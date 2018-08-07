package de.qaware.smartlab.core.filesystem;

import de.qaware.smartlab.core.exception.TempFileManagerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static java.nio.file.Files.*;
import static java.util.Objects.isNull;
import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static org.apache.commons.io.FileUtils.copyInputStreamToFile;

@Component
@Slf4j
public class TempFileManager implements ITempFileManager, CommandLineRunner {

    private boolean isCleaningObsoleteFiles;
    private final Set<Path> filesToClean;
    private final Duration obsoleteFileCleaningInterval;
    private final Path tempFileBaseDir;
    private final String tempFileNamePrefix;
    private final String tempFileNameSuffix;

    public TempFileManager(
            // TODO: String literal
            @Qualifier("obsoleteFileCleaningInterval") Duration obsoleteFileCleaningInterval,
            @Qualifier("tempFileBaseDir") Path tempFileBaseDir,
            @Qualifier("tempFileNamePrefix") String tempFileNamePrefix,
            @Qualifier("tempFileNameSuffix") String tempFileNameSuffix) {
        this.filesToClean = ConcurrentHashMap.newKeySet();  // This creates a concurrent set in Java 8. In more recent versions there may be a more intuitive way.
        this.obsoleteFileCleaningInterval = obsoleteFileCleaningInterval;
        this.tempFileBaseDir = tempFileBaseDir;
        this.tempFileNamePrefix = tempFileNamePrefix;
        this.tempFileNameSuffix = tempFileNameSuffix;
    }

    @Override
    public void run(String... args) throws Exception {
        startCleaningObsoleteFiles();
    }

    public void startCleaningObsoleteFiles() {
        if(this.isCleaningObsoleteFiles) {
            log.warn("Ignoring call to start cleaning obsolete temp files since cleaning is already in progress");
            return;
        }
        this.isCleaningObsoleteFiles = true;
        ExecutorService executor = newSingleThreadExecutor();
        executor.submit(() -> {
            while(this.isCleaningObsoleteFiles) {
                cleanObsoleteFiles();
                try {
                    TimeUnit.SECONDS.sleep(this.obsoleteFileCleaningInterval.getSeconds());
                } catch (InterruptedException e) {
                    String errorMessage = format(
                            "Could not wait specified interval of %d seconds",
                            this.obsoleteFileCleaningInterval.getSeconds());
                    log.error(errorMessage, e);
                    throw new TempFileManagerException(errorMessage, e);
                }
            }
        });
    }

    public void stopCleaningObsoleteFiles() {
        if(this.isCleaningObsoleteFiles) {
            log.warn("Ignoring call to stop cleaning obsolete temp files since cleaning is not in progress");
            return;
        }
        this.isCleaningObsoleteFiles = false;
    }

    private void cleanObsoleteFiles() {
        synchronized(this.filesToClean) {
            for(Path fileToClean : this.filesToClean) {
                try {
                    deleteIfExists(fileToClean);
                    this.filesToClean.remove(fileToClean);
                    log.info("Cleaned obsolete file {}", fileToClean);
                } catch (Exception e) {
                    log.error("Could not clean obsolete file {}", fileToClean, e);
                }
            }
        }
    }

    @Override
    public void markForCleaning(Path fileToDelete) {
        this.filesToClean.add(fileToDelete);
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
