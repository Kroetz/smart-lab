package de.qaware.smartlabassistance.assistance.controllable;

import de.qaware.smartlabaction.action.submittable.generic.IActionSubmittable;
import de.qaware.smartlabaction.action.submittable.webbrowser.closing.WebBrowserClosingSubmittable;
import de.qaware.smartlabaction.action.submittable.webbrowser.opening.WebBrowserOpeningSubmittable;
import de.qaware.smartlabapi.service.action.IActionService;
import de.qaware.smartlabassistance.assistance.controllable.factory.AbstractAssistanceControllableFactory;
import de.qaware.smartlabassistance.assistance.info.DisplayWebsiteInfo;
import de.qaware.smartlabcore.data.assistance.IAssistanceInfo;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static java.util.Arrays.asList;

@Slf4j
public class DisplayWebsiteControllable extends AbstractAssistanceControllable {

    private final IActionSubmittable<WebBrowserOpeningSubmittable.ActionArgs, UUID> webBrowserOpening;
    private final IActionSubmittable<WebBrowserClosingSubmittable.ActionArgs, Void> webBrowserClosing;
    private UUID webBrowserInstanceId;

    private DisplayWebsiteControllable(
            IAssistanceInfo displayWebsiteInfo,
            IActionSubmittable<WebBrowserOpeningSubmittable.ActionArgs, UUID> webBrowserOpening,
            IActionSubmittable<WebBrowserClosingSubmittable.ActionArgs, Void> webBrowserClosing) {
        super(displayWebsiteInfo);
        this.webBrowserOpening = webBrowserOpening;
        this.webBrowserClosing = webBrowserClosing;
    }

    @Override
    public void begin(IActionService actionService, IAssistanceContext context) {
        // TODO: casting smells
        // TODO: Check for casting exception and throw illegalstateexception
        DisplayWebsiteInfo.Configuration config = (DisplayWebsiteInfo.Configuration) context.getAssistanceConfiguration();
        final WebBrowserOpeningSubmittable.ActionArgs webBrowserOpeningArgs = WebBrowserOpeningSubmittable.ActionArgs.of(
                config.getWebBrowserId(),
                asList(config.getUrl()));
        this.webBrowserInstanceId = this.webBrowserOpening.submitExecution(actionService, webBrowserOpeningArgs);
    }

    @Override
    public void end(IActionService actionService, IAssistanceContext context) {
        // TODO: casting smells
        // TODO: Check for casting exception and throw illegalstateexception
        DisplayWebsiteInfo.Configuration config = (DisplayWebsiteInfo.Configuration) context.getAssistanceConfiguration();
        final WebBrowserClosingSubmittable.ActionArgs webBrowserClosingArgs = WebBrowserClosingSubmittable.ActionArgs.of(
                config.getWebBrowserId(),
                this.webBrowserInstanceId);
        this.webBrowserClosing.submitExecution(actionService, webBrowserClosingArgs);
    }

    @Override
    public void update(IActionService actionService, IAssistanceContext context) {
        // TODO: Implementation
    }

    @Component
    @Slf4j
    public static class Factory extends AbstractAssistanceControllableFactory {

        private final IActionSubmittable<WebBrowserOpeningSubmittable.ActionArgs, UUID> webBrowserOpening;
        private final IActionSubmittable<WebBrowserClosingSubmittable.ActionArgs, Void> webBrowserClosing;

        public Factory(
                IAssistanceInfo displayWebsiteInfo,
                IActionSubmittable<WebBrowserOpeningSubmittable.ActionArgs, UUID> webBrowserOpening,
                IActionSubmittable<WebBrowserClosingSubmittable.ActionArgs, Void> webBrowserClosing) {
            super(displayWebsiteInfo);
            this.webBrowserOpening = webBrowserOpening;
            this.webBrowserClosing = webBrowserClosing;
        }

        @Override
        public IAssistanceControllable newInstance() {
            return new DisplayWebsiteControllable(
                    this.assistanceInfo,
                    this.webBrowserOpening,
                    this.webBrowserClosing);
        }
    }
}
