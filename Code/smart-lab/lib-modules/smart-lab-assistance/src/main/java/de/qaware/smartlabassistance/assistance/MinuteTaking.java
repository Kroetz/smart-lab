package de.qaware.smartlabassistance.assistance;

import de.qaware.smartlabaction.action.microphone.ActivateMicrophone;
import de.qaware.smartlabaction.action.microphone.DeactivateMicrophone;
import de.qaware.smartlabaction.action.speechtotext.SpeechToText;
import de.qaware.smartlabaction.action.uploaddata.UploadData;
import de.qaware.smartlabcommons.data.action.generic.IAction;
import de.qaware.smartlabcommons.data.action.generic.IActionExecution;
import de.qaware.smartlabcommons.data.action.speechtotext.ITextPassagesBuilder;
import de.qaware.smartlabcommons.data.action.speechtotext.ITranscript;
import de.qaware.smartlabcommons.data.action.speechtotext.ITranscriptTextBuilder;
import de.qaware.smartlabcommons.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcommons.data.assistance.IAssistanceStageExecution;
import de.qaware.smartlabcommons.data.assistance.ITriggerReaction;
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
    private final ActivateMicrophone activateMicrophone;
    private final DeactivateMicrophone deactivateMicrophone;
    private final SpeechToText speechToText;
    private final UploadData uploadData;
    private final ITranscriptTextBuilder transcriptTextBuilder;
    private final ITextPassagesBuilder textPassagesBuilder;

    public MinuteTaking(
            IResolver<String, IAction> actionResolver,
            ActivateMicrophone activateMicrophone,
            DeactivateMicrophone deactivateMicrophone,
            SpeechToText speechToText,
            UploadData uploadData,
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
    public ITriggerReaction reactionOnTriggerStartMeeting(final IContext context) {
        log.info("Reaction on start-meeting trigger on assistance \"{}\" of meeting with ID \"{}\" is to begin the assistance",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        return (assistanceService) -> assistanceService.beginAssistance(this.assistanceId, context);
    }

    @Override
    public ITriggerReaction reactionOnTriggerStopMeeting(final IContext context) {
        log.info("Reaction on stop-meeting trigger on assistance \"{}\" of meeting with ID \"{}\" is to end the assistance",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        return (assistanceService) -> assistanceService.endAssistance(this.assistanceId, context);
    }

    @Override
    public IAssistanceStageExecution executionOfBeginStage(IContext context) {
        final ActivateMicrophone.ActionArgs microphoneActivationArgs = ActivateMicrophone.ActionArgs.of(
                context.getRoom().map(IRoom::getId).orElseThrow(InsufficientContextException::new),
                context.getAssistanceConfiguration().map(IAssistanceConfiguration::getDeviceId).orElseThrow(InsufficientContextException::new));
        return (actionService) -> {
            IActionExecution<Void> microphoneActivation = this.activateMicrophone.execution(microphoneActivationArgs);
            microphoneActivation.execute(actionService);
        };
    }

    @Override
    public IAssistanceStageExecution executionOfEndStage(IContext context) {
        return (actionService) -> {
            final DeactivateMicrophone.ActionArgs microphoneDeactivationArgs = DeactivateMicrophone.ActionArgs.of(
                    context.getRoom().map(IRoom::getId).orElseThrow(InsufficientContextException::new),
                    context.getAssistanceConfiguration().map(IAssistanceConfiguration::getDeviceId).orElseThrow(InsufficientContextException::new));
            IActionExecution<Path> microphoneDeactivation = this.deactivateMicrophone.execution(microphoneDeactivationArgs);
            Path recordedAudio = microphoneDeactivation.execute(actionService);

            final SpeechToText.ActionArgs speechToTextArgs = SpeechToText.ActionArgs.of(recordedAudio);
            IActionExecution<ITranscript> speechToTextConversion = this.speechToText.execution(speechToTextArgs);
            ITranscript transcript = speechToTextConversion.execute(actionService);

            final UploadData.ActionArgs uploadDataArgs = UploadData.ActionArgs.of(
                    context.getWorkgroup().orElseThrow(InsufficientContextException::new).getKnowledgeBaseInfo(),
                    transcript.toHumanReadable(this.transcriptTextBuilder, this.textPassagesBuilder));
            IActionExecution<Void> dataUploading = this.uploadData.execution(uploadDataArgs);
            dataUploading.execute(actionService);

            // TODO: Delete temp file
            //Files.deleteIfExists(recordedAudio);
        };
    }

    @Override
    public IAssistanceStageExecution executionOfUpdateStage(IContext context) {
        // TODO: Implementation
        return (actionService) -> {};
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
