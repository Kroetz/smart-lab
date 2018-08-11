package de.qaware.smartlab.actuator.adapter.adapters.microphone;

import de.qaware.smartlab.core.configuration.ResourcesConfiguration;
import de.qaware.smartlab.core.exception.actuator.ActuatorException;
import de.qaware.smartlab.core.filesystem.ITempFileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import static de.qaware.smartlab.core.constant.ResourcePaths.DUMMY_SPEECH;
import static org.apache.commons.io.IOUtils.toByteArray;

@Component
@Slf4j
public class DummyMicrophoneAdapter extends AbstractMicrophoneAdapter {

    public static final String ACTUATOR_TYPE = "dummy microphone";
    private static final boolean HAS_LOCAL_API = true;
    private final ResourceLoader resourceLoader;
    private final ITempFileManager tempFileManager;
    private final Path resourcesTempFileSubDir;

    public DummyMicrophoneAdapter(
            ResourceLoader resourceLoader,
            ITempFileManager tempFileManager,
            @Qualifier(ResourcesConfiguration.QUALIFIER_RESOURCES_TEMP_FILE_SUB_DIR) Path resourcesTempFileSubDir) {
        super(ACTUATOR_TYPE, HAS_LOCAL_API);
        this.resourceLoader = resourceLoader;
        this.tempFileManager = tempFileManager;
        this.resourcesTempFileSubDir = resourcesTempFileSubDir;
    }

    @Override
    public void startRecording(Path recordingTargetFile) throws ActuatorException {
        log.info("Dummy microphone started recording");
    }

    @Override
    public Path stopRecording() throws ActuatorException {
        log.info("Dummy microphone stopped recording");
        try {
            Resource dummySpeechResource = resourceLoader.getResource(DUMMY_SPEECH);
            InputStream resourceInputStream = dummySpeechResource.getInputStream();
            byte[] bytes = toByteArray(resourceInputStream);
            Path dummySpeechFile = tempFileManager.saveToTempFile(this.resourcesTempFileSubDir, bytes);
            dummySpeechFile.toFile().deleteOnExit();
            return dummySpeechFile;
        } catch (IOException e) {
            String errorMessage = "Could not read dummy audio from resources";
            log.error(errorMessage);
            throw new ActuatorException(errorMessage, e);
        }
    }
}
