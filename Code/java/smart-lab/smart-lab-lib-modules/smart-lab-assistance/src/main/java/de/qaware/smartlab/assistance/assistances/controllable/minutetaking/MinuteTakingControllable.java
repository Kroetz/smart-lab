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
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.exception.AssistanceFailedException;
import de.qaware.smartlab.core.exception.InsufficientContextException;
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
    public void begin(IActionService actionService, IAssistanceContext context) {
        MinuteTakingInfo.Configuration config = toSpecificConfigType(
                MinuteTakingInfo.Configuration.class,
                context.getAssistanceConfiguration());
        final AudioRecordingStartCallable.ActionArgs audioRecordingStartArgs = AudioRecordingStartCallable.ActionArgs.of(
                context.getLocation().map(ILocation::getId).orElseThrow(InsufficientContextException::new),
                config.getMicrophoneId());
        this.audioRecordingStart.submitExecution(actionService, audioRecordingStartArgs);
    }

    @Override
    public void end(IActionService actionService, IAssistanceContext context) {
        MinuteTakingInfo.Configuration config = toSpecificConfigType(
                MinuteTakingInfo.Configuration.class,
                context.getAssistanceConfiguration());
        Path recordedAudio = stopRecording(actionService, context, config);
        ITranscript transcript = speechToText(actionService, config, recordedAudio);
        uploadAudio(actionService, context, config, recordedAudio);
        uploadTranscript(actionService, context, config, transcript);
        // TODO: Delete temp file
        //this.tempFileManager.markForCleaning(recordedAudio);
    }

    private Path stopRecording(
            IActionService actionService,
            IAssistanceContext context,
            MinuteTakingInfo.Configuration config) {
        final AudioRecordingStopCallable.ActionArgs audioRecordingStopArgs = AudioRecordingStopCallable.ActionArgs.of(
                context.getLocation().map(ILocation::getId).orElseThrow(InsufficientContextException::new),
                config.getMicrophoneId());
        return this.audioRecordingStop.submitExecution(actionService, audioRecordingStopArgs);
    }

    private ITranscript speechToText(
            IActionService actionService,
            MinuteTakingInfo.Configuration config,
            Path recordedAudio) {
        final SpeechToTextCallable.ActionArgs speechToTextArgs = SpeechToTextCallable.ActionArgs.of(
                recordedAudio,
                config.getSpokenLanguage());
        return this.speechToText.submitExecution(actionService, speechToTextArgs);
    }

    private void uploadAudio(
            IActionService actionService,
            IAssistanceContext context,
            MinuteTakingInfo.Configuration config,
            Path recordedAudio) {
        // TODO: String literals
        String uploadMessage = "Upload minutes (audio) taken by Smart-Lab";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String fileName = LocalDateTime.now().format(formatter) + "_audio.wav";
        final DataUploadCallable.ActionArgs dataUploadArgs;
        try {
            dataUploadArgs = DataUploadCallable.ActionArgs.of(
                    context.getWorkgroup().orElseThrow(InsufficientContextException::new).getProjectBaseInfo(),
                    config.getUploadDir(),
                    fileName,
                    uploadMessage,
                    readAllBytes(recordedAudio));
        } catch (IOException e) {
            String errorMessage = format("Could not read recorded audio file %s", recordedAudio);
            log.error(errorMessage, e);
            throw new AssistanceFailedException(errorMessage, e);
        }
        this.dataUpload.submitExecution(actionService, dataUploadArgs);
    }

    private void uploadTranscript(
            IActionService actionService,
            IAssistanceContext context,
            MinuteTakingInfo.Configuration config,
            ITranscript transcript) {
        // TODO: String literals
        String uploadMessage = "Upload minutes (transcript) taken by Smart-Lab";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String fileName = LocalDateTime.now().format(formatter) + "_transcript.txt";
        final DataUploadCallable.ActionArgs dataUploadArgs = DataUploadCallable.ActionArgs.of(
                context.getWorkgroup().orElseThrow(InsufficientContextException::new).getProjectBaseInfo(),
                config.getUploadDir(),
                fileName,
                uploadMessage,
                transcript.toHumanReadable().getBytes());
        this.dataUpload.submitExecution(actionService, dataUploadArgs);
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
