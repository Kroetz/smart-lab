package de.qaware.smartlab.assistance.assistances.controllable.minutetaking;

import de.qaware.smartlab.action.actions.callable.audio.recordingstart.AudioRecordingStartCallable;
import de.qaware.smartlab.action.actions.callable.audio.recordingstop.AudioRecordingStopCallable;
import de.qaware.smartlab.action.actions.callable.data.upload.DataUploadCallable;
import de.qaware.smartlab.action.actions.callable.generic.IActionCallable;
import de.qaware.smartlab.action.actions.callable.speechtotext.SpeechToTextCallable;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.assistance.assistances.controllable.generic.AbstractAssistanceControllable;
import de.qaware.smartlab.assistance.assistances.controllable.generic.IAssistanceControllable;
import de.qaware.smartlab.assistance.assistances.controllable.miscellaneous.factory.AbstractAssistanceControllableFactory;
import de.qaware.smartlab.assistance.assistances.info.generic.IAssistanceInfo;
import de.qaware.smartlab.assistance.assistances.info.minutetaking.MinuteTakingInfo;
import de.qaware.smartlab.core.data.action.speechtotext.ITranscript;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import de.qaware.smartlab.core.exception.assistance.AssistanceException;
import de.qaware.smartlab.core.exception.context.InsufficientContextException;
import de.qaware.smartlab.core.filesystem.ITempFileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.String.format;
import static java.nio.file.Files.readAllBytes;

@Slf4j
public class MinuteTakingControllable extends AbstractAssistanceControllable {

    private static final String AUDIO_UPLOAD_MESSAGE = "Upload minutes (audio) taken by Smart-Lab";
    private static final String AUDIO_UPLOAD_FILE_NAME_SUFFIX = "_audio.wav";
    private static final String TRANSCRIPT_UPLOAD_MESSAGE = "Upload minutes (transcript) taken by Smart-Lab";
    private static final String TRANSCRIPT_UPLOAD_FILE_NAME_SUFFIX = "_transcript.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");

    private final IActionCallable<AudioRecordingStartCallable.ActionArgs, Void> audioRecordingStart;
    private final IActionCallable<AudioRecordingStopCallable.ActionArgs, Path> audioRecordingStop;
    private final IActionCallable<SpeechToTextCallable.ActionArgs, ITranscript> speechToText;
    private final IActionCallable<DataUploadCallable.ActionArgs, Void> dataUpload;
    private final ITempFileManager tempFileManager;

    private MinuteTakingControllable(
            IAssistanceInfo minuteTakingInfo,
            IActionCallable<AudioRecordingStartCallable.ActionArgs, Void> audioRecordingStart,
            IActionCallable<AudioRecordingStopCallable.ActionArgs, Path> audioRecordingStop,
            IActionCallable<SpeechToTextCallable.ActionArgs, ITranscript> speechToText,
            IActionCallable<DataUploadCallable.ActionArgs, Void> dataUpload,
            ITempFileManager tempFileManager) {
        super(minuteTakingInfo);
        this.audioRecordingStart = audioRecordingStart;
        this.audioRecordingStop = audioRecordingStop;
        this.speechToText = speechToText;
        this.dataUpload = dataUpload;
        this.tempFileManager = tempFileManager;
    }

    @Override
    public void begin(IActionService actionService, IAssistanceContext context) throws AssistanceException, InsufficientContextException {
        MinuteTakingInfo.Configuration config = toSpecificConfigType(
                MinuteTakingInfo.Configuration.class,
                context.getAssistanceConfiguration());
        final AudioRecordingStartCallable.ActionArgs audioRecordingStartArgs =
                AudioRecordingStartCallable.ActionArgs.of(config.getMicrophoneId());
        this.audioRecordingStart.call(actionService, audioRecordingStartArgs);
    }

    @Override
    public void end(IActionService actionService, IAssistanceContext context) throws AssistanceException, InsufficientContextException {
        MinuteTakingInfo.Configuration config = toSpecificConfigType(
                MinuteTakingInfo.Configuration.class,
                context.getAssistanceConfiguration());
        Path recordedAudio = stopRecording(actionService, context, config);
        ITranscript transcript = speechToText(actionService, config, recordedAudio);
        uploadAudio(actionService, context, config, recordedAudio);
        uploadTranscript(actionService, context, config, transcript);
        this.tempFileManager.markForCleaning(recordedAudio);
    }

    private Path stopRecording(
            IActionService actionService,
            IAssistanceContext context,
            MinuteTakingInfo.Configuration config) {
        final AudioRecordingStopCallable.ActionArgs audioRecordingStopArgs =
                AudioRecordingStopCallable.ActionArgs.of(config.getMicrophoneId());
        return this.audioRecordingStop.call(actionService, audioRecordingStopArgs);
    }

    private ITranscript speechToText(
            IActionService actionService,
            MinuteTakingInfo.Configuration config,
            Path recordedAudio) {
        final SpeechToTextCallable.ActionArgs speechToTextArgs = SpeechToTextCallable.ActionArgs.of(
                recordedAudio,
                config.getSpokenLanguage());
        return this.speechToText.call(actionService, speechToTextArgs);
    }

    private void uploadAudio(
            IActionService actionService,
            IAssistanceContext context,
            MinuteTakingInfo.Configuration config,
            Path recordedAudio) {
        String fileName = LocalDateTime.now().format(formatter) + AUDIO_UPLOAD_FILE_NAME_SUFFIX;
        final DataUploadCallable.ActionArgs dataUploadArgs;
        try {
            dataUploadArgs = DataUploadCallable.ActionArgs.of(
                    context.getWorkgroup().getProjectBaseInfo(),
                    config.getUploadDir(),
                    fileName,
                    AUDIO_UPLOAD_MESSAGE,
                    readAllBytes(recordedAudio));
        } catch (IOException e) {
            String errorMessage = format("Could not read recorded audio file %s", recordedAudio);
            log.error(errorMessage);
            throw new AssistanceException(errorMessage, e);
        }
        this.dataUpload.call(actionService, dataUploadArgs);
    }

    private void uploadTranscript(
            IActionService actionService,
            IAssistanceContext context,
            MinuteTakingInfo.Configuration config,
            ITranscript transcript) {
        String fileName = LocalDateTime.now().format(formatter) + TRANSCRIPT_UPLOAD_FILE_NAME_SUFFIX;
        final DataUploadCallable.ActionArgs dataUploadArgs = DataUploadCallable.ActionArgs.of(
                context.getWorkgroup().getProjectBaseInfo(),
                config.getUploadDir(),
                fileName,
                TRANSCRIPT_UPLOAD_MESSAGE,
                transcript.toHumanReadable().getBytes());
        this.dataUpload.call(actionService, dataUploadArgs);
    }

    @Component
    @Slf4j
    public static class Factory extends AbstractAssistanceControllableFactory {

        private final IActionCallable<AudioRecordingStartCallable.ActionArgs, Void> audioRecordingStart;
        private final IActionCallable<AudioRecordingStopCallable.ActionArgs, Path> audioRecordingStop;
        private final IActionCallable<SpeechToTextCallable.ActionArgs, ITranscript> speechToText;
        private final IActionCallable<DataUploadCallable.ActionArgs, Void> dataUpload;
        private final ITempFileManager tempFileManager;

        public Factory(
                IAssistanceInfo minuteTakingInfo,
                IActionCallable<AudioRecordingStartCallable.ActionArgs, Void> audioRecordingStart,
                IActionCallable<AudioRecordingStopCallable.ActionArgs, Path> audioRecordingStop,
                IActionCallable<SpeechToTextCallable.ActionArgs, ITranscript> speechToText,
                IActionCallable<DataUploadCallable.ActionArgs, Void> dataUpload,
                ITempFileManager tempFileManager) {
            super(minuteTakingInfo);
            this.audioRecordingStart = audioRecordingStart;
            this.audioRecordingStop = audioRecordingStop;
            this.speechToText = speechToText;
            this.dataUpload = dataUpload;
            this.tempFileManager = tempFileManager;
        }

        @Override
        public IAssistanceControllable newInstance() {
            return new MinuteTakingControllable(
                    this.assistanceInfo,
                    this.audioRecordingStart,
                    this.audioRecordingStop,
                    this.speechToText,
                    this.dataUpload,
                    this.tempFileManager);
        }
    }
}
