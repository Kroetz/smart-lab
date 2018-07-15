package de.qaware.smartlabcore.data.device.microphone;

import de.qaware.smartlabcore.exception.LocalDeviceException;
import de.qaware.smartlabcore.filesystem.IFileSystemManager;
import de.qaware.smartlabcore.miscellaneous.ResourcePaths;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import static org.apache.commons.io.IOUtils.toByteArray;

@Component
@Slf4j
public class DummyMicrophone extends AbstractMicrophoneAdapter {

    public static final String DEVICE_TYPE = "dummy microphone";
    private static final boolean HAS_LOCAL_API = true;
    private final ResourceLoader resourceLoader;
    private final IFileSystemManager fileSystemManager;
    private final Path resourcesTempFileSubDir;

    public DummyMicrophone(
            ResourceLoader resourceLoader,
            IFileSystemManager fileSystemManager,
            Path resourcesTempFileSubDir) {
        super(DEVICE_TYPE, HAS_LOCAL_API);
        this.resourceLoader = resourceLoader;
        this.fileSystemManager = fileSystemManager;
        this.resourcesTempFileSubDir = resourcesTempFileSubDir;
    }

    @Override
    public void activate(Path recordingTargetFile) {
        log.info("Dummy microphone activated");
    }

    @Override
    public Path deactivate() {
        log.info("Dummy microphone deactivated");
        try {
            Resource dummySpeechResource = resourceLoader.getResource(ResourcePaths.DUMMY_SPEECH);
            InputStream resourceInputStream = dummySpeechResource.getInputStream();
            byte[] bytes = toByteArray(resourceInputStream);
            Path dummySpeechFile = fileSystemManager.saveToTempFile(this.resourcesTempFileSubDir, bytes);
            dummySpeechFile.toFile().deleteOnExit();
            return dummySpeechFile;
        } catch (IOException e) {
            throw new LocalDeviceException(e);
        }
    }
}
