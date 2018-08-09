package de.qaware.smartlab.assistance.assistances.controllable.minutetaking;

import de.qaware.smartlab.action.actions.callable.data.upload.DataUploadCallable;
import de.qaware.smartlab.action.actions.callable.generic.IActionCallable;
import de.qaware.smartlab.action.actions.callable.microphone.activation.MicrophoneActivationCallable;
import de.qaware.smartlab.action.actions.callable.microphone.deactivation.MicrophoneDeactivationCallable;
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
import de.qaware.smartlab.core.exception.InsufficientContextException;
import de.qaware.smartlab.core.filesystem.ITempFileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class MinuteTakingControllable extends AbstractAssistanceControllable {

    private final IActionCallable<MicrophoneActivationCallable.ActionArgs, Void> microphoneActivation;
    private final IActionCallable<MicrophoneDeactivationCallable.ActionArgs, Path> microphoneDeactivation;
    private final IActionCallable<SpeechToTextCallable.ActionArgs, ITranscript> speechToText;
    private final IActionCallable<DataUploadCallable.ActionArgs, Void> dataUpload;
    private final ITempFileManager tempFileManager;

    private MinuteTakingControllable(
            IAssistanceInfo minuteTakingInfo,
            IActionCallable<MicrophoneActivationCallable.ActionArgs, Void> microphoneActivation,
            IActionCallable<MicrophoneDeactivationCallable.ActionArgs, Path> microphoneDeactivation,
            IActionCallable<SpeechToTextCallable.ActionArgs, ITranscript> speechToText,
            IActionCallable<DataUploadCallable.ActionArgs, Void> dataUpload,
            ITempFileManager tempFileManager) {
        super(minuteTakingInfo);
        this.microphoneActivation = microphoneActivation;
        this.microphoneDeactivation = microphoneDeactivation;
        this.speechToText = speechToText;
        this.dataUpload = dataUpload;
        this.tempFileManager = tempFileManager;
    }

    @Override
    public void begin(IActionService actionService, IAssistanceContext context) {
        MinuteTakingInfo.Configuration config = toSpecificConfigType(
                MinuteTakingInfo.Configuration.class,
                context.getAssistanceConfiguration());
        final MicrophoneActivationCallable.ActionArgs microphoneActivationArgs = MicrophoneActivationCallable.ActionArgs.of(
                context.getLocation().map(ILocation::getId).orElseThrow(InsufficientContextException::new),
                config.getMicrophoneId());
        this.microphoneActivation.submitExecution(actionService, microphoneActivationArgs);
    }

    @Override
    public void end(IActionService actionService, IAssistanceContext context) {
        MinuteTakingInfo.Configuration config = toSpecificConfigType(
                MinuteTakingInfo.Configuration.class,
                context.getAssistanceConfiguration());
        Path recordedAudio = stopRecording(actionService, context, config);
        ITranscript transcript = speechToText(actionService, config, recordedAudio);
        uploadMinutes(actionService, context, config, transcript);
        // TODO: Delete temp file
        //this.tempFileManager.markForCleaning(recordedAudio);
    }

    private Path stopRecording(
            IActionService actionService,
            IAssistanceContext context,
            MinuteTakingInfo.Configuration config) {
        final MicrophoneDeactivationCallable.ActionArgs microphoneDeactivationArgs = MicrophoneDeactivationCallable.ActionArgs.of(
                context.getLocation().map(ILocation::getId).orElseThrow(InsufficientContextException::new),
                config.getMicrophoneId());
        return this.microphoneDeactivation.submitExecution(actionService, microphoneDeactivationArgs);
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

    private void uploadMinutes(
            IActionService actionService,
            IAssistanceContext context,
            MinuteTakingInfo.Configuration config,
            ITranscript transcript) {
        // TODO: String literals
        String uploadMessage = "Upload minutes taken by Smart-Lab";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String fileName = LocalDateTime.now().format(formatter) + ".txt";
        final DataUploadCallable.ActionArgs dataUploadArgs = DataUploadCallable.ActionArgs.of(
                context.getWorkgroup().orElseThrow(InsufficientContextException::new).getProjectBaseInfo(),
                config.getUploadDir(),
                fileName,
                uploadMessage,
                transcript.toHumanReadable());
        this.dataUpload.submitExecution(actionService, dataUploadArgs);
    }

    @Component
    @Slf4j
    public static class Factory extends AbstractAssistanceControllableFactory {

        private final IActionCallable<MicrophoneActivationCallable.ActionArgs, Void> microphoneActivation;
        private final IActionCallable<MicrophoneDeactivationCallable.ActionArgs, Path> microphoneDeactivation;
        private final IActionCallable<SpeechToTextCallable.ActionArgs, ITranscript> speechToText;
        private final IActionCallable<DataUploadCallable.ActionArgs, Void> dataUpload;
        private final ITempFileManager tempFileManager;

        public Factory(
                IAssistanceInfo minuteTakingInfo,
                IActionCallable<MicrophoneActivationCallable.ActionArgs, Void> microphoneActivation,
                IActionCallable<MicrophoneDeactivationCallable.ActionArgs, Path> microphoneDeactivation,
                IActionCallable<SpeechToTextCallable.ActionArgs, ITranscript> speechToText,
                IActionCallable<DataUploadCallable.ActionArgs, Void> dataUpload,
                ITempFileManager tempFileManager) {
            super(minuteTakingInfo);
            this.microphoneActivation = microphoneActivation;
            this.microphoneDeactivation = microphoneDeactivation;
            this.speechToText = speechToText;
            this.dataUpload = dataUpload;
            this.tempFileManager = tempFileManager;
        }

        @Override
        public IAssistanceControllable newInstance() {
            return new MinuteTakingControllable(
                    this.assistanceInfo,
                    this.microphoneActivation,
                    this.microphoneDeactivation,
                    this.speechToText,
                    this.dataUpload,
                    this.tempFileManager);
        }
    }
}
