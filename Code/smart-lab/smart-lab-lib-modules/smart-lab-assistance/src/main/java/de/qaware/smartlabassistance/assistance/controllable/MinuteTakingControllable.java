package de.qaware.smartlabassistance.assistance.controllable;

import de.qaware.smartlabaction.action.generic.IActionExecutable;
import de.qaware.smartlabaction.action.generic.IActionSubmittable;
import de.qaware.smartlabaction.action.microphone.ActivateMicrophone;
import de.qaware.smartlabaction.action.microphone.DeactivateMicrophone;
import de.qaware.smartlabaction.action.speechtotext.SpeechToText;
import de.qaware.smartlabaction.action.uploaddata.UploadData;
import de.qaware.smartlabapi.service.action.IActionService;
import de.qaware.smartlabassistance.assistance.info.MinuteTakingInfo;
import de.qaware.smartlabcore.data.action.speechtotext.ITextPassagesBuilder;
import de.qaware.smartlabcore.data.action.speechtotext.ITranscript;
import de.qaware.smartlabcore.data.action.speechtotext.ITranscriptTextBuilder;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.context.IContext;
import de.qaware.smartlabcore.data.generic.IResolver;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.exception.InsufficientContextException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@Slf4j
public class MinuteTakingControllable extends AbstractAssistanceControllable {

    private final IActionSubmittable<ActivateMicrophone.ActionArgs, Void> activateMicrophone;
    private final IActionSubmittable<DeactivateMicrophone.ActionArgs, Path> deactivateMicrophone;
    private final IActionSubmittable<SpeechToText.ActionArgs, ITranscript> speechToText;
    private final IActionSubmittable<UploadData.ActionArgs, Void> uploadData;
    private final ITranscriptTextBuilder transcriptTextBuilder;
    private final ITextPassagesBuilder textPassagesBuilder;

    public MinuteTakingControllable(
            MinuteTakingInfo minuteTakingInfo,
            IResolver<String, IActionExecutable> actionResolver,
            IActionSubmittable<ActivateMicrophone.ActionArgs, Void> activateMicrophone,
            IActionSubmittable<DeactivateMicrophone.ActionArgs, Path> deactivateMicrophone,
            IActionSubmittable<SpeechToText.ActionArgs, ITranscript> speechToText,
            IActionSubmittable<UploadData.ActionArgs, Void> uploadData,
            ITranscriptTextBuilder transcriptTextBuilder,
            ITextPassagesBuilder textPassagesBuilder) {
        super(minuteTakingInfo, actionResolver);
        this.activateMicrophone = activateMicrophone;
        this.deactivateMicrophone = deactivateMicrophone;
        this.speechToText = speechToText;
        this.uploadData = uploadData;
        this.transcriptTextBuilder = transcriptTextBuilder;
        this.textPassagesBuilder = textPassagesBuilder;
    }

    @Override
    public void begin(IActionService actionService, IContext context) {
        final ActivateMicrophone.ActionArgs microphoneActivationArgs = ActivateMicrophone.ActionArgs.of(
                context.getRoom().map(IRoom::getId).orElseThrow(InsufficientContextException::new),
                context.getAssistanceConfiguration().map(IAssistanceConfiguration::getDeviceId).orElseThrow(InsufficientContextException::new));
        this.activateMicrophone.submitExecution(actionService, microphoneActivationArgs);
    }

    @Override
    public void end(IActionService actionService, IContext context) {
        final DeactivateMicrophone.ActionArgs microphoneDeactivationArgs = DeactivateMicrophone.ActionArgs.of(
                context.getRoom().map(IRoom::getId).orElseThrow(InsufficientContextException::new),
                context.getAssistanceConfiguration().map(IAssistanceConfiguration::getDeviceId).orElseThrow(InsufficientContextException::new));
        Path recordedAudio = this.deactivateMicrophone.submitExecution(actionService, microphoneDeactivationArgs);

        final SpeechToText.ActionArgs speechToTextArgs = SpeechToText.ActionArgs.of(recordedAudio);
        ITranscript transcript = this.speechToText.submitExecution(actionService, speechToTextArgs);

        final UploadData.ActionArgs uploadDataArgs = UploadData.ActionArgs.of(
                context.getWorkgroup().orElseThrow(InsufficientContextException::new).getKnowledgeBaseInfo(),
                transcript.toHumanReadable(this.transcriptTextBuilder, this.textPassagesBuilder));
        this.uploadData.submitExecution(actionService, uploadDataArgs);

        // TODO: Delete temp file
        //Files.deleteIfExists(recordedAudio);
    }

    @Override
    public void update(IActionService actionService, IContext context) {
        // TODO: Implementation
    }
}
