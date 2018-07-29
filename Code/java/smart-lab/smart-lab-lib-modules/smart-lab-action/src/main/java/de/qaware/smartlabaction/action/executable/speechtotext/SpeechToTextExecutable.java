package de.qaware.smartlabaction.action.executable.speechtotext;

import de.qaware.smartlabaction.action.executable.generic.AbstractActionExecutable;
import de.qaware.smartlabaction.action.result.TranscriptActionResult;
import de.qaware.smartlabaction.action.result.VoidActionResult;
import de.qaware.smartlabaction.action.info.speechtotext.SpeechToTextInfo;
import de.qaware.smartlabaction.action.submittable.speechtotext.SpeechToTextSubmittable;
import de.qaware.smartlabapi.service.connector.delegate.IDelegateService;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.data.action.speechtotext.ISpeechToTextService;
import de.qaware.smartlabcore.data.action.speechtotext.ITranscript;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpeechToTextExecutable extends AbstractActionExecutable {

    private final ISpeechToTextService speechToTextService;

    public SpeechToTextExecutable(
            SpeechToTextInfo speechToTextInfo,
            ISpeechToTextService speechToTextService) {
        super(speechToTextInfo);
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
        ITranscript transcript = this.speechToTextService.speechToText(
                actionArgs.getAudioFile(),
                actionArgs.getSpokenLanguage());
        return TranscriptActionResult.of(transcript);
    }
}
