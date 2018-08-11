package de.qaware.smartlab.actuator.adapter.adapters.microphone;

import de.qaware.smartlab.core.exception.actuator.ActuatorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import java.nio.file.Path;

import static java.lang.String.format;

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
        this.genericMicrophone = GenericMicrophone.getMicrophone(MICROPHONE_NAME, AUDIO_FORMAT).orElseThrow(ActuatorException::new);
    }

    @Override
    public void startRecording(Path recordingTargetFile) throws ActuatorException {
        try {
            this.genericMicrophone.startRecording(recordingTargetFile, AUDIO_FILE_FORMAT);
        }
        catch(Exception e) {
            String errorMessage = format("Could not start recording audio to file %s", recordingTargetFile);
            log.error(errorMessage);
            throw new ActuatorException(errorMessage, e);
        }
    }

    @Override
    public Path stopRecording() throws ActuatorException {
        try {
            return this.genericMicrophone.stopRecording().orElseThrow(ActuatorException::new);
        }
        catch(Exception e) {
            String errorMessage = "Could not stop recording audio";
            log.error(errorMessage);
            throw new ActuatorException(errorMessage, e);
        }
    }
}
