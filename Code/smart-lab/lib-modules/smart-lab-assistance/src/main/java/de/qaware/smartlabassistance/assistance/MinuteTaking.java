package de.qaware.smartlabassistance.assistance;

import de.qaware.smartlabaction.action.microphone.ActivateMicrophone;
import de.qaware.smartlabaction.action.microphone.DeactivateMicrophone;
import de.qaware.smartlabaction.action.speechtotext.SpeechToText;
import de.qaware.smartlabaction.action.uploaddata.UploadData;
import de.qaware.smartlabapi.service.action.IActionService;
import de.qaware.smartlabapi.service.assistance.IAssistanceService;
import de.qaware.smartlabaction.action.generic.IActionExecutable;
import de.qaware.smartlabaction.action.generic.IActionSubmittable;
import de.qaware.smartlabcommons.data.action.speechtotext.ITextPassagesBuilder;
import de.qaware.smartlabcommons.data.action.speechtotext.ITranscript;
import de.qaware.smartlabcommons.data.action.speechtotext.ITranscriptTextBuilder;
import de.qaware.smartlabcommons.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcommons.data.context.IContext;
import de.qaware.smartlabcommons.data.generic.IResolver;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.exception.InsufficientContextException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class MinuteTaking extends AbstractAssistance {

    public static final String ASSISTANCE_ID = "minute taking";
    // TODO: Simpler with Java 9 (see https://stackoverflow.com/questions/2041778/how-to-initialize-hashset-values-by-construction)
    public static final Set<String> ASSISTANCE_ALIASES = Stream.of(
            "minute-taking",
            "minuteTaking").collect(Collectors.toSet());
    private final IActionSubmittable<ActivateMicrophone.ActionArgs, Void> activateMicrophone;
    private final IActionSubmittable<DeactivateMicrophone.ActionArgs, Path> deactivateMicrophone;
    private final IActionSubmittable<SpeechToText.ActionArgs, ITranscript> speechToText;
    private final IActionSubmittable<UploadData.ActionArgs, Void> uploadData;
    private final ITranscriptTextBuilder transcriptTextBuilder;
    private final ITextPassagesBuilder textPassagesBuilder;

    public MinuteTaking(
            IResolver<String, IActionExecutable> actionResolver,
            IActionSubmittable<ActivateMicrophone.ActionArgs, Void> activateMicrophone,
            IActionSubmittable<DeactivateMicrophone.ActionArgs, Path> deactivateMicrophone,
            IActionSubmittable<SpeechToText.ActionArgs, ITranscript> speechToText,
            IActionSubmittable<UploadData.ActionArgs, Void> uploadData,
            ITranscriptTextBuilder transcriptTextBuilder,
            ITextPassagesBuilder textPassagesBuilder) {
        super(ASSISTANCE_ID, ASSISTANCE_ALIASES, actionResolver);
        this.activateMicrophone = activateMicrophone;
        this.deactivateMicrophone = deactivateMicrophone;
        this.speechToText = speechToText;
        this.uploadData = uploadData;
        this.transcriptTextBuilder = transcriptTextBuilder;
        this.textPassagesBuilder = textPassagesBuilder;
    }

    @Override
    public void reactOnTriggerStartMeeting(IAssistanceService assistanceService, final IContext context) {
        log.info("Reaction on start-meeting trigger on assistance \"{}\" of meeting with ID \"{}\" is to begin the assistance",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        assistanceService.beginAssistance(this.assistanceId, context);
    }

    @Override
    public void reactOnTriggerStopMeeting(IAssistanceService assistanceService, final IContext context) {
        log.info("Reaction on stop-meeting trigger on assistance \"{}\" of meeting with ID \"{}\" is to end the assistance",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        assistanceService.endAssistance(this.assistanceId, context);
    }

    @Override
    public void begin(IActionService actionService, IContext context) {
        final ActivateMicrophone.ActionArgs microphoneActivationArgs = ActivateMicrophone.ActionArgs.of(
                context.getRoom().map(IRoom::getId).orElseThrow(InsufficientContextException::new),
                context.getAssistanceConfiguration().map(IAssistanceConfiguration::getDeviceId).orElseThrow(InsufficientContextException::new));
        this.activateMicrophone.submitCall(actionService, microphoneActivationArgs);
    }

    @Override
    public void end(IActionService actionService, IContext context) {
        final DeactivateMicrophone.ActionArgs microphoneDeactivationArgs = DeactivateMicrophone.ActionArgs.of(
                context.getRoom().map(IRoom::getId).orElseThrow(InsufficientContextException::new),
                context.getAssistanceConfiguration().map(IAssistanceConfiguration::getDeviceId).orElseThrow(InsufficientContextException::new));
        Path recordedAudio = this.deactivateMicrophone.submitCall(actionService, microphoneDeactivationArgs);

        final SpeechToText.ActionArgs speechToTextArgs = SpeechToText.ActionArgs.of(recordedAudio);
        ITranscript transcript = this.speechToText.submitCall(actionService, speechToTextArgs);

        final UploadData.ActionArgs uploadDataArgs = UploadData.ActionArgs.of(
                context.getWorkgroup().orElseThrow(InsufficientContextException::new).getKnowledgeBaseInfo(),
                transcript.toHumanReadable(this.transcriptTextBuilder, this.textPassagesBuilder));
        this.uploadData.submitCall(actionService, uploadDataArgs);

        // TODO: Delete temp file
        //Files.deleteIfExists(recordedAudio);
    }

    @Override
    public void update(IActionService actionService, IContext context) {
        // TODO: Implementation
    }

    // TODO: Which annotation can be removed?
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Slf4j
    public static class Configuration implements IAssistanceConfiguration {

        private String deviceId;
    }
}
