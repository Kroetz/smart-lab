package de.qaware.smartlab.action.actions.callable.audio.recordingstop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.qaware.smartlab.action.actions.callable.generic.AbstractActionCallable;
import de.qaware.smartlab.action.actions.info.audio.recordingstop.AudioRecordingStopInfo;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.actuator.ActuatorId;
import de.qaware.smartlab.core.exception.action.ActionException;
import de.qaware.smartlab.core.filesystem.ITempFileManager;
import de.qaware.smartlab.core.filesystem.TempFileManagerConfiguration;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

@Component
@Slf4j
public class AudioRecordingStopCallable extends AbstractActionCallable<AudioRecordingStopCallable.ActionArgs, Path> {

    private final Path recordedAudioTempFileSubDir;
    private final ITempFileManager tempFileManager;

    public AudioRecordingStopCallable(
            AudioRecordingStopInfo audioRecordingStopInfo,
            @Qualifier(TempFileManagerConfiguration.QUALIFIER_RECORDED_AUDIO_TEMP_FILE_SUB_DIR) Path recordedAudioTempFileSubDir,
            ITempFileManager tempFileManager) {
        super(audioRecordingStopInfo);
        this.recordedAudioTempFileSubDir = recordedAudioTempFileSubDir;
        this.tempFileManager = tempFileManager;
    }

    public Path call(IActionService actionService, ActionArgs actionArgs) throws ActionException {
        IActionResult actionResult = actionService.executeAction(this.actionInfo.getActionId(), actionArgs);
        byte[] recordedAudio = actionResult.getByteArrayValue();
        try {
            return this.tempFileManager.saveToTempFile(recordedAudioTempFileSubDir, recordedAudio);
        } catch (IOException e) {
            throw new ActionException(e);
        }
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class ActionArgs implements IActionArgs {

        private static final String FIELD_NAME_MICROPHONE_ID = "microphoneId";

        @NonNull
        private final ActuatorId microphoneId;

        @JsonCreator
        public static ActionArgs of(@JsonProperty(FIELD_NAME_MICROPHONE_ID) ActuatorId microphoneId) {
            return new ActionArgs(microphoneId);
        }
    }
}
