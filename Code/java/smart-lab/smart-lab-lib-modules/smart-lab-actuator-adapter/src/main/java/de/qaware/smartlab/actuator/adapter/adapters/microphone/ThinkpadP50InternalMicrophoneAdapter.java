package de.qaware.smartlab.actuator.adapter.adapters.microphone;

import de.qaware.smartlab.core.exception.LocalActuatorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import java.nio.file.Path;

@Component
@Slf4j
public class ThinkpadP50InternalMicrophoneAdapter extends AbstractMicrophoneAdapter {

    public static final String ACTUATOR_TYPE = "thinkpad p50 internal microphone";
    private static final boolean HAS_LOCAL_API = true;
    // TODO: Do not use microphone name for identification but rather something like manufacturer (not affected by localization)
    private static final String MICROPHONE_NAME = "Mikrofonarray (Realtek High Def";
    private static final AudioFormat AUDIO_FORMAT = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED,
            44100.0f,
            8,
            1,
            1,
            44100.0f,
            false);
    private static final AudioFileFormat.Type AUDIO_FILE_FORMAT = AudioFileFormat.Type.WAVE;

    private final GenericMicrophone genericMicrophone;

    public ThinkpadP50InternalMicrophoneAdapter() {
        super(ACTUATOR_TYPE, HAS_LOCAL_API);
        this.genericMicrophone = GenericMicrophone.getMicrophone(MICROPHONE_NAME, AUDIO_FORMAT).orElseThrow(LocalActuatorException::new);
    }

    @Override
    public void startRecording(Path recordingTargetFile) {
        // TODO: Get format from application properties
        this.genericMicrophone.startRecording(recordingTargetFile, AUDIO_FILE_FORMAT);
    }

    @Override
    public Path stopRecording() {
        return this.genericMicrophone.stopRecording().orElseThrow(LocalActuatorException::new);
    }
}
