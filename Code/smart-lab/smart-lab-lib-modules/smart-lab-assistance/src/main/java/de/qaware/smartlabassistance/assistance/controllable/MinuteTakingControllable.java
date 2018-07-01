package de.qaware.smartlabassistance.assistance.controllable;

import de.qaware.smartlabaction.action.submittable.dataupload.DataUploadSubmittable;
import de.qaware.smartlabaction.action.submittable.generic.IActionSubmittable;
import de.qaware.smartlabaction.action.submittable.microphone.activation.MicrophoneActivationSubmittable;
import de.qaware.smartlabaction.action.submittable.microphone.deactivation.MicrophoneDeactivationSubmittable;
import de.qaware.smartlabaction.action.submittable.speechtotext.SpeechToTextSubmittable;
import de.qaware.smartlabapi.service.action.IActionService;
import de.qaware.smartlabassistance.assistance.info.MinuteTakingInfo;
import de.qaware.smartlabcore.data.action.speechtotext.ITextPassagesBuilder;
import de.qaware.smartlabcore.data.action.speechtotext.ITranscript;
import de.qaware.smartlabcore.data.action.speechtotext.ITranscriptTextBuilder;
import de.qaware.smartlabcore.data.context.IContext;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.exception.InsufficientContextException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class MinuteTakingControllable extends AbstractAssistanceControllable {

    private final IActionSubmittable<MicrophoneActivationSubmittable.ActionArgs, Void> microphoneActivation;
    private final IActionSubmittable<MicrophoneDeactivationSubmittable.ActionArgs, Path> microphoneDeactivation;
    private final IActionSubmittable<SpeechToTextSubmittable.ActionArgs, ITranscript> speechToText;
    private final IActionSubmittable<DataUploadSubmittable.ActionArgs, Void> dataUpload;
    private final ITranscriptTextBuilder transcriptTextBuilder;
    private final ITextPassagesBuilder textPassagesBuilder;

    public MinuteTakingControllable(
            MinuteTakingInfo minuteTakingInfo,
            IActionSubmittable<MicrophoneActivationSubmittable.ActionArgs, Void> microphoneActivation,
            IActionSubmittable<MicrophoneDeactivationSubmittable.ActionArgs, Path> microphoneDeactivation,
            IActionSubmittable<SpeechToTextSubmittable.ActionArgs, ITranscript> speechToText,
            IActionSubmittable<DataUploadSubmittable.ActionArgs, Void> dataUpload,
            ITranscriptTextBuilder transcriptTextBuilder,
            ITextPassagesBuilder textPassagesBuilder) {
        super(minuteTakingInfo);
        this.microphoneActivation = microphoneActivation;
        this.microphoneDeactivation = microphoneDeactivation;
        this.speechToText = speechToText;
        this.dataUpload = dataUpload;
        this.transcriptTextBuilder = transcriptTextBuilder;
        this.textPassagesBuilder = textPassagesBuilder;
    }

    @Override
    public void begin(IActionService actionService, IContext context) {
        // TODO: casting smells
        // TODO: Check for casting exception and throw illegalstateexception
        MinuteTakingInfo.Configuration config = (MinuteTakingInfo.Configuration) context.getAssistanceConfiguration().orElseThrow(InsufficientContextException::new);
        final MicrophoneActivationSubmittable.ActionArgs microphoneActivationArgs = MicrophoneActivationSubmittable.ActionArgs.of(
                context.getRoom().map(IRoom::getId).orElseThrow(InsufficientContextException::new),
                config.getMicrophoneId());
        this.microphoneActivation.submitExecution(actionService, microphoneActivationArgs);
    }

    @Override
    public void end(IActionService actionService, IContext context) {
        // TODO: casting smells
        // TODO: Check for casting exception and throw illegalstateexception
        MinuteTakingInfo.Configuration config = (MinuteTakingInfo.Configuration) context.getAssistanceConfiguration().orElseThrow(InsufficientContextException::new);
        final MicrophoneDeactivationSubmittable.ActionArgs microphoneDeactivationArgs = MicrophoneDeactivationSubmittable.ActionArgs.of(
                context.getRoom().map(IRoom::getId).orElseThrow(InsufficientContextException::new),
                config.getMicrophoneId());
        Path recordedAudio = this.microphoneDeactivation.submitExecution(actionService, microphoneDeactivationArgs);

        final SpeechToTextSubmittable.ActionArgs speechToTextArgs = SpeechToTextSubmittable.ActionArgs.of(
                recordedAudio,
                config.getSpokenLanguage());
        ITranscript transcript = this.speechToText.submitExecution(actionService, speechToTextArgs);

        // TODO: String literals
        String uploadMessage = "Upload minutes taken by Smart-Lab";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String fileName = LocalDateTime.now().format(formatter) + ".txt";
        final DataUploadSubmittable.ActionArgs dataUploadArgs = DataUploadSubmittable.ActionArgs.of(
                context.getWorkgroup().orElseThrow(InsufficientContextException::new).getKnowledgeBaseInfo(),
                config.getUploadDir(),
                fileName,
                uploadMessage,
                transcript.toHumanReadable(this.transcriptTextBuilder, this.textPassagesBuilder));
        this.dataUpload.submitExecution(actionService, dataUploadArgs);

        // TODO: Delete temp file
        //Files.deleteIfExists(recordedAudio);
    }

    @Override
    public void update(IActionService actionService, IContext context) {
        // TODO: Implementation
    }
}
