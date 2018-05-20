package de.qaware.smartlabcommons.data.assistance;

import de.qaware.smartlabcommons.data.action.IAction;
import de.qaware.smartlabcommons.data.action.IActionExecution;
import de.qaware.smartlabcommons.data.action.microphone.ActivateMicrophone;
import de.qaware.smartlabcommons.data.action.microphone.DeactivateMicrophone;
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

    public MinuteTaking(
            IResolver<String, IAction> actionResolver,
            ActivateMicrophone activateMicrophone,
            DeactivateMicrophone deactivateMicrophone) {
        super(ASSISTANCE_ID, ASSISTANCE_ALIASES, actionResolver);
        this.activateMicrophone = activateMicrophone;
        this.deactivateMicrophone = deactivateMicrophone;
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
            IActionExecution<Void> microphoneActivation = activateMicrophone.execution(microphoneActivationArgs);
            microphoneActivation.execute(actionService);
        };
    }

    @Override
    public IAssistanceStageExecution executionOfEndStage(IContext context) {
        // TODO: Implementation
        return (actionService) -> {
            final DeactivateMicrophone.ActionArgs microphoneDeactivationArgs = DeactivateMicrophone.ActionArgs.of(
                    context.getRoom().map(IRoom::getId).orElseThrow(InsufficientContextException::new),
                    context.getAssistanceConfiguration().map(IAssistanceConfiguration::getDeviceId).orElseThrow(InsufficientContextException::new));
            IActionExecution<Path> microphoneDeactivation = deactivateMicrophone.execution(microphoneDeactivationArgs);
            Path recordedAudio = microphoneDeactivation.execute(actionService);

            /*
            IActionArgs actionArgs2 = SpeechToText.ActionArgs.of(
                    recordedAudio,
                    context.getRoom().map(IRoom::getId).orElseThrow(InsufficientContextException::new),
                    // Get webservice Name (or get from applicaiton properties?)
                    context.getAssistanceConfiguration().map(IAssistanceConfiguration::getDeviceIdServiceName).orElseThrow(InsufficientContextException::new));
            IActionResult<String> convertedAudio = actionService.executeAction(SpeechToText.ACTION_ID, actionArgs2);

            IActionArgs actionArgs3 = UploadFile.ActionArgs.of(
                    convertedAudio,
                    context.getAssistanceConfiguration().map(IAssistanceConfiguration::getFileName).orElseThrow(InsufficientContextException::new),
                    // Get webservice Name (or get from applicaiton properties?)
                    context.getAssistanceConfiguration().map(IAssistanceConfiguration::getDeviceIdServiceName).orElseThrow(InsufficientContextException::new));
            IActionResult<Void> result = actionService.executeAction(UploadFile.ACTION_ID, actionArgs3);*/

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
