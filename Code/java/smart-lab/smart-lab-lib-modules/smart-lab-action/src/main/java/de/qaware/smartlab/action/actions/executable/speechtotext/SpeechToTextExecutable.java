package de.qaware.smartlab.action.actions.executable.speechtotext;

import de.qaware.smartlab.action.actions.executable.generic.AbstractActionExecutable;
import de.qaware.smartlab.action.actions.info.speechtotext.SpeechToTextInfo;
import de.qaware.smartlab.action.result.TranscriptActionResult;
import de.qaware.smartlab.action.result.VoidActionResult;
import de.qaware.smartlab.action.actions.submittable.speechtotext.SpeechToTextSubmittable;
import de.qaware.smartlab.api.service.connector.delegate.IDelegateService;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.data.action.speechtotext.ISpeechToTextService;
import de.qaware.smartlabcore.data.action.speechtotext.ITranscript;
import de.qaware.smartlabcore.data.generic.IResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpeechToTextExecutable extends AbstractActionExecutable {

    private final IResolver<String, ISpeechToTextService> speechToTextServiceResolver;
    private final String speechToTextService;

    public SpeechToTextExecutable(
            SpeechToTextInfo speechToTextInfo,
            IResolver<String, ISpeechToTextService> speechToTextServiceResolver,
            // TODO: String literal
            @Qualifier("speechToTextServiceName") String speechToTextService) {
        super(speechToTextInfo);
        this.speechToTextServiceResolver = speechToTextServiceResolver;
        this.speechToTextService = speechToTextService;
    }

    @Override
    public IActionResult execute(String deviceType, IActionArgs genericActionArgs) {
        // TODO: Is never needed because there is no local execution for a web service.
        return VoidActionResult.newInstance();
    }

    @Override
    public IActionResult execute(IActionArgs genericActionArgs, IDelegateService delegateService) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        SpeechToTextSubmittable.ActionArgs actionArgs = convertToSpecificActionArgs(
                SpeechToTextSubmittable.ActionArgs.class,
                genericActionArgs);
        ISpeechToTextService speechToTextService = this.speechToTextServiceResolver.resolve(this.speechToTextService);
        ITranscript transcript = speechToTextService.speechToText(
                actionArgs.getAudioFile(),
                actionArgs.getSpokenLanguage());
        return TranscriptActionResult.of(transcript);
    }
}
