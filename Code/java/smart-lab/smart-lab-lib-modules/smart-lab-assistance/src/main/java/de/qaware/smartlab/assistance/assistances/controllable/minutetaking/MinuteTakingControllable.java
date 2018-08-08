package de.qaware.smartlab.assistance.assistances.controllable.minutetaking;

import de.qaware.smartlab.action.actions.submittable.data.upload.DataUploadSubmittable;
import de.qaware.smartlab.action.actions.submittable.generic.IActionSubmittable;
import de.qaware.smartlab.action.actions.submittable.microphone.activation.MicrophoneActivationSubmittable;
import de.qaware.smartlab.action.actions.submittable.microphone.deactivation.MicrophoneDeactivationSubmittable;
import de.qaware.smartlab.action.actions.submittable.speechtotext.SpeechToTextSubmittable;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.assistance.assistances.controllable.generic.AbstractAssistanceControllable;
import de.qaware.smartlab.assistance.assistances.controllable.generic.IAssistanceControllable;
import de.qaware.smartlab.assistance.assistances.controllable.miscellaneous.factory.AbstractAssistanceControllableFactory;
import de.qaware.smartlab.assistance.assistances.info.generic.IAssistanceInfo;
import de.qaware.smartlab.assistance.assistances.info.minutetaking.MinuteTakingInfo;
import de.qaware.smartlab.core.data.action.speechtotext.ITextPassagesBuilder;
import de.qaware.smartlab.core.data.action.speechtotext.ITranscript;
import de.qaware.smartlab.core.data.action.speechtotext.ITranscriptTextBuilder;
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

    private final IActionSubmittable<MicrophoneActivationSubmittable.ActionArgs, Void> microphoneActivation;
    private final IActionSubmittable<MicrophoneDeactivationSubmittable.ActionArgs, Path> microphoneDeactivation;
    private final IActionSubmittable<SpeechToTextSubmittable.ActionArgs, ITranscript> speechToText;
    private final IActionSubmittable<DataUploadSubmittable.ActionArgs, Void> dataUpload;
    private final ITranscriptTextBuilder transcriptTextBuilder;
    private final ITextPassagesBuilder textPassagesBuilder;
    private final ITempFileManager tempFileManager;

    private MinuteTakingControllable(
            IAssistanceInfo minuteTakingInfo,
            IActionSubmittable<MicrophoneActivationSubmittable.ActionArgs, Void> microphoneActivation,
            IActionSubmittable<MicrophoneDeactivationSubmittable.ActionArgs, Path> microphoneDeactivation,
            IActionSubmittable<SpeechToTextSubmittable.ActionArgs, ITranscript> speechToText,
            IActionSubmittable<DataUploadSubmittable.ActionArgs, Void> dataUpload,
            ITranscriptTextBuilder transcriptTextBuilder,
            ITextPassagesBuilder textPassagesBuilder,
            ITempFileManager tempFileManager) {
        super(minuteTakingInfo);
        this.microphoneActivation = microphoneActivation;
        this.microphoneDeactivation = microphoneDeactivation;
        this.speechToText = speechToText;
        this.dataUpload = dataUpload;
        this.transcriptTextBuilder = transcriptTextBuilder;
        this.textPassagesBuilder = textPassagesBuilder;
        this.tempFileManager = tempFileManager;
    }

    @Override
    public void begin(IActionService actionService, IAssistanceContext context) {
        MinuteTakingInfo.Configuration config = toSpecificConfigType(
                MinuteTakingInfo.Configuration.class,
                context.getAssistanceConfiguration());
        final MicrophoneActivationSubmittable.ActionArgs microphoneActivationArgs = MicrophoneActivationSubmittable.ActionArgs.of(
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
        final MicrophoneDeactivationSubmittable.ActionArgs microphoneDeactivationArgs = MicrophoneDeactivationSubmittable.ActionArgs.of(
                context.getLocation().map(ILocation::getId).orElseThrow(InsufficientContextException::new),
                config.getMicrophoneId());
        return this.microphoneDeactivation.submitExecution(actionService, microphoneDeactivationArgs);
    }

    private ITranscript speechToText(
            IActionService actionService,
            MinuteTakingInfo.Configuration config,
            Path recordedAudio) {
        final SpeechToTextSubmittable.ActionArgs speechToTextArgs = SpeechToTextSubmittable.ActionArgs.of(
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
        final DataUploadSubmittable.ActionArgs dataUploadArgs = DataUploadSubmittable.ActionArgs.of(
                context.getWorkgroup().orElseThrow(InsufficientContextException::new).getProjectBaseInfo(),
                config.getUploadDir(),
                fileName,
                uploadMessage,
                transcript.toHumanReadable(this.transcriptTextBuilder, this.textPassagesBuilder));
        this.dataUpload.submitExecution(actionService, dataUploadArgs);
    }

    @Component
    @Slf4j
    public static class Factory extends AbstractAssistanceControllableFactory {

        private final IActionSubmittable<MicrophoneActivationSubmittable.ActionArgs, Void> microphoneActivation;
        private final IActionSubmittable<MicrophoneDeactivationSubmittable.ActionArgs, Path> microphoneDeactivation;
        private final IActionSubmittable<SpeechToTextSubmittable.ActionArgs, ITranscript> speechToText;
        private final IActionSubmittable<DataUploadSubmittable.ActionArgs, Void> dataUpload;
        private final ITranscriptTextBuilder transcriptTextBuilder;
        private final ITextPassagesBuilder textPassagesBuilder;
        private final ITempFileManager tempFileManager;

        public Factory(
                IAssistanceInfo minuteTakingInfo,
                IActionSubmittable<MicrophoneActivationSubmittable.ActionArgs, Void> microphoneActivation,
                IActionSubmittable<MicrophoneDeactivationSubmittable.ActionArgs, Path> microphoneDeactivation,
                IActionSubmittable<SpeechToTextSubmittable.ActionArgs, ITranscript> speechToText,
                IActionSubmittable<DataUploadSubmittable.ActionArgs, Void> dataUpload,
                ITranscriptTextBuilder transcriptTextBuilder,
                ITextPassagesBuilder textPassagesBuilder,
                ITempFileManager tempFileManager) {
            super(minuteTakingInfo);
            this.microphoneActivation = microphoneActivation;
            this.microphoneDeactivation = microphoneDeactivation;
            this.speechToText = speechToText;
            this.dataUpload = dataUpload;
            this.transcriptTextBuilder = transcriptTextBuilder;
            this.textPassagesBuilder = textPassagesBuilder;
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
                    this.transcriptTextBuilder,
                    this.textPassagesBuilder,
                    this.tempFileManager);
        }
    }
}
