package de.qaware.smartlab.assistance.assistances.controllable.websitedisplaying;

import de.qaware.smartlab.action.actions.submittable.generic.IActionSubmittable;
import de.qaware.smartlab.action.actions.submittable.webbrowser.closing.WebBrowserClosingSubmittable;
import de.qaware.smartlab.action.actions.submittable.webbrowser.opening.WebBrowserOpeningSubmittable;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.assistance.assistances.controllable.generic.IAssistanceControllable;
import de.qaware.smartlab.assistance.assistances.controllable.generic.AbstractAssistanceControllable;
import de.qaware.smartlab.assistance.assistances.controllable.miscellaneous.factory.AbstractAssistanceControllableFactory;
import de.qaware.smartlab.assistance.assistances.info.websitedisplaying.WebsiteDisplayingInfo;
import de.qaware.smartlab.assistance.assistances.info.generic.IAssistanceInfo;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static java.util.Arrays.asList;

@Slf4j
public class WebsiteDisplayingControllable extends AbstractAssistanceControllable {

    private final IActionSubmittable<WebBrowserOpeningSubmittable.ActionArgs, UUID> webBrowserOpening;
    private final IActionSubmittable<WebBrowserClosingSubmittable.ActionArgs, Void> webBrowserClosing;
    private UUID webBrowserInstanceId;

    private WebsiteDisplayingControllable(
            IAssistanceInfo websiteDisplayingInfo,
            IActionSubmittable<WebBrowserOpeningSubmittable.ActionArgs, UUID> webBrowserOpening,
            IActionSubmittable<WebBrowserClosingSubmittable.ActionArgs, Void> webBrowserClosing) {
        super(websiteDisplayingInfo);
        this.webBrowserOpening = webBrowserOpening;
        this.webBrowserClosing = webBrowserClosing;
    }

    @Override
    public void begin(IActionService actionService, IAssistanceContext context) {
        // TODO: casting smells
        // TODO: Check for casting exception and throw illegalstateexception
        WebsiteDisplayingInfo.Configuration config = (WebsiteDisplayingInfo.Configuration) context.getAssistanceConfiguration();
        final WebBrowserOpeningSubmittable.ActionArgs webBrowserOpeningArgs = WebBrowserOpeningSubmittable.ActionArgs.of(
                config.getWebBrowserId(),
                config.getDisplayId(),
                asList(config.getUrl()));
        this.webBrowserInstanceId = this.webBrowserOpening.submitExecution(actionService, webBrowserOpeningArgs);
    }

    @Override
    public void end(IActionService actionService, IAssistanceContext context) {
        // TODO: casting smells
        // TODO: Check for casting exception and throw illegalstateexception
        WebsiteDisplayingInfo.Configuration config = (WebsiteDisplayingInfo.Configuration) context.getAssistanceConfiguration();
        final WebBrowserClosingSubmittable.ActionArgs webBrowserClosingArgs = WebBrowserClosingSubmittable.ActionArgs.of(
                config.getWebBrowserId(),
                this.webBrowserInstanceId);
        this.webBrowserClosing.submitExecution(actionService, webBrowserClosingArgs);
    }

    @Component
    @Slf4j
    public static class Factory extends AbstractAssistanceControllableFactory {

        private final IActionSubmittable<WebBrowserOpeningSubmittable.ActionArgs, UUID> webBrowserOpening;
        private final IActionSubmittable<WebBrowserClosingSubmittable.ActionArgs, Void> webBrowserClosing;

        public Factory(
                IAssistanceInfo websiteDisplayingInfo,
                IActionSubmittable<WebBrowserOpeningSubmittable.ActionArgs, UUID> webBrowserOpening,
                IActionSubmittable<WebBrowserClosingSubmittable.ActionArgs, Void> webBrowserClosing) {
            super(websiteDisplayingInfo);
            this.webBrowserOpening = webBrowserOpening;
            this.webBrowserClosing = webBrowserClosing;
        }

        @Override
        public IAssistanceControllable newInstance() {
            return new WebsiteDisplayingControllable(
                    this.assistanceInfo,
                    this.webBrowserOpening,
                    this.webBrowserClosing);
        }
    }
}
