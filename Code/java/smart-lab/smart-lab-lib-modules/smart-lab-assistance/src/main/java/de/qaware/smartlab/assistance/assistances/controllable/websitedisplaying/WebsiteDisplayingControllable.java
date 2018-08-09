package de.qaware.smartlab.assistance.assistances.controllable.websitedisplaying;

import de.qaware.smartlab.action.actions.callable.generic.IActionCallable;
import de.qaware.smartlab.action.actions.callable.webbrowser.closing.WebBrowserClosingCallable;
import de.qaware.smartlab.action.actions.callable.webbrowser.opening.WebBrowserOpeningCallable;
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

    private final IActionCallable<WebBrowserOpeningCallable.ActionArgs, UUID> webBrowserOpening;
    private final IActionCallable<WebBrowserClosingCallable.ActionArgs, Void> webBrowserClosing;
    private UUID webBrowserInstanceId;

    private WebsiteDisplayingControllable(
            IAssistanceInfo websiteDisplayingInfo,
            IActionCallable<WebBrowserOpeningCallable.ActionArgs, UUID> webBrowserOpening,
            IActionCallable<WebBrowserClosingCallable.ActionArgs, Void> webBrowserClosing) {
        super(websiteDisplayingInfo);
        this.webBrowserOpening = webBrowserOpening;
        this.webBrowserClosing = webBrowserClosing;
    }

    @Override
    public void begin(IActionService actionService, IAssistanceContext context) {
        WebsiteDisplayingInfo.Configuration config = toSpecificConfigType(
                WebsiteDisplayingInfo.Configuration.class,
                context.getAssistanceConfiguration());
        final WebBrowserOpeningCallable.ActionArgs webBrowserOpeningArgs = WebBrowserOpeningCallable.ActionArgs.of(
                config.getWebBrowserId(),
                config.getDisplayId(),
                asList(config.getUrl()));
        this.webBrowserInstanceId = this.webBrowserOpening.call(actionService, webBrowserOpeningArgs);
    }

    @Override
    public void end(IActionService actionService, IAssistanceContext context) {
        WebsiteDisplayingInfo.Configuration config = toSpecificConfigType(
                WebsiteDisplayingInfo.Configuration.class,
                context.getAssistanceConfiguration());
        final WebBrowserClosingCallable.ActionArgs webBrowserClosingArgs = WebBrowserClosingCallable.ActionArgs.of(
                config.getWebBrowserId(),
                this.webBrowserInstanceId);
        this.webBrowserClosing.call(actionService, webBrowserClosingArgs);
    }

    @Component
    @Slf4j
    public static class Factory extends AbstractAssistanceControllableFactory {

        private final IActionCallable<WebBrowserOpeningCallable.ActionArgs, UUID> webBrowserOpening;
        private final IActionCallable<WebBrowserClosingCallable.ActionArgs, Void> webBrowserClosing;

        public Factory(
                IAssistanceInfo websiteDisplayingInfo,
                IActionCallable<WebBrowserOpeningCallable.ActionArgs, UUID> webBrowserOpening,
                IActionCallable<WebBrowserClosingCallable.ActionArgs, Void> webBrowserClosing) {
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
