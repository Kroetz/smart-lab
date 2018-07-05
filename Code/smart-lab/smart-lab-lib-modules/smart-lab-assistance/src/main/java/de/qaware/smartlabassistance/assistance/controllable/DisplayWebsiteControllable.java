package de.qaware.smartlabassistance.assistance.controllable;

import de.qaware.smartlabaction.action.submittable.generic.IActionSubmittable;
import de.qaware.smartlabaction.action.submittable.webbrowser.closing.WebBrowserClosingSubmittable;
import de.qaware.smartlabaction.action.submittable.webbrowser.opening.WebBrowserOpeningSubmittable;
import de.qaware.smartlabapi.service.action.IActionService;
import de.qaware.smartlabassistance.assistance.info.DisplayWebsiteInfo;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import de.qaware.smartlabcore.exception.InsufficientContextException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class DisplayWebsiteControllable extends AbstractAssistanceControllable {

    private final IActionSubmittable<WebBrowserOpeningSubmittable.ActionArgs, Void> webBrowserOpening;
    private final IActionSubmittable<WebBrowserClosingSubmittable.ActionArgs, Void> webBrowserClosing;

    public DisplayWebsiteControllable(
            DisplayWebsiteInfo displayWebsiteInfo,
            IActionSubmittable<WebBrowserOpeningSubmittable.ActionArgs, Void> webBrowserOpening,
            IActionSubmittable<WebBrowserClosingSubmittable.ActionArgs, Void> webBrowserClosing) {
        super(displayWebsiteInfo);
        this.webBrowserOpening = webBrowserOpening;
        this.webBrowserClosing = webBrowserClosing;
    }

    @Override
    public void begin(IActionService actionService, IAssistanceContext context) {
        // TODO: casting smells
        // TODO: Check for casting exception and throw illegalstateexception
        DisplayWebsiteInfo.Configuration config = (DisplayWebsiteInfo.Configuration) context.getAssistanceConfiguration().orElseThrow(InsufficientContextException::new);
        final WebBrowserOpeningSubmittable.ActionArgs webBrowserOpeningArgs = WebBrowserOpeningSubmittable.ActionArgs.of(
                config.getWebBrowserId(),
                Arrays.asList(config.getUrl()));
        this.webBrowserOpening.submitExecution(actionService, webBrowserOpeningArgs);
    }

    @Override
    public void end(IActionService actionService, IAssistanceContext context) {
        // TODO: casting smells
        // TODO: Check for casting exception and throw illegalstateexception
        DisplayWebsiteInfo.Configuration config = (DisplayWebsiteInfo.Configuration) context.getAssistanceConfiguration().orElseThrow(InsufficientContextException::new);
        final WebBrowserClosingSubmittable.ActionArgs webBrowserClosingArgs = WebBrowserClosingSubmittable.ActionArgs.of(
                config.getWebBrowserId());
        this.webBrowserClosing.submitExecution(actionService, webBrowserClosingArgs);
    }

    @Override
    public void update(IActionService actionService, IAssistanceContext context) {
        // TODO: Implementation
    }
}
