package de.qaware.smartlabaction.action.actor.microphone;

import de.qaware.smartlabcore.exception.LocalDeviceException;
import de.qaware.smartlabcore.filesystem.ITempFileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import static de.qaware.smartlabcore.miscellaneous.ResourcePaths.DUMMY_SPEECH;
import static org.apache.commons.io.IOUtils.toByteArray;

@Component
@Slf4j
public class DummyMicrophoneAdapter extends AbstractMicrophoneAdapter {

    public static final String DEVICE_TYPE = "dummy microphone";
    private static final boolean HAS_LOCAL_API = true;
    private final ResourceLoader resourceLoader;
    private final ITempFileManager tempFileManager;
    private final Path resourcesTempFileSubDir;

    public DummyMicrophoneAdapter(
            ResourceLoader resourceLoader,
            ITempFileManager tempFileManager,
            Path resourcesTempFileSubDir) {
        super(DEVICE_TYPE, HAS_LOCAL_API);
        this.resourceLoader = resourceLoader;
        this.tempFileManager = tempFileManager;
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
            Resource dummySpeechResource = resourceLoader.getResource(DUMMY_SPEECH);
            InputStream resourceInputStream = dummySpeechResource.getInputStream();
            byte[] bytes = toByteArray(resourceInputStream);
            Path dummySpeechFile = tempFileManager.saveToTempFile(this.resourcesTempFileSubDir, bytes);
            dummySpeechFile.toFile().deleteOnExit();
            return dummySpeechFile;
        } catch (IOException e) {
            throw new LocalDeviceException(e);
        }
    }
}
