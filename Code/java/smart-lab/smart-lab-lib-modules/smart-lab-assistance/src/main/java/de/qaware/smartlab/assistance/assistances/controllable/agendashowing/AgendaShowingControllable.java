package de.qaware.smartlab.assistance.assistances.controllable.agendashowing;

import de.qaware.smartlab.action.actions.callable.generic.IActionCallable;
import de.qaware.smartlab.action.actions.callable.webbrowser.closing.WebBrowserClosingCallable;
import de.qaware.smartlab.action.actions.callable.webbrowser.opening.WebBrowserOpeningCallable;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.api.service.constant.gui.GuiApiConstants;
import de.qaware.smartlab.assistance.assistances.controllable.generic.AbstractAssistanceControllable;
import de.qaware.smartlab.assistance.assistances.controllable.generic.IAssistanceControllable;
import de.qaware.smartlab.assistance.assistances.controllable.miscellaneous.factory.AbstractAssistanceControllableFactory;
import de.qaware.smartlab.assistance.assistances.info.agendashowing.AgendaShowingInfo;
import de.qaware.smartlab.assistance.assistances.info.generic.IAssistanceInfo;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import de.qaware.smartlab.core.exception.InsufficientContextException;
import de.qaware.smartlab.core.service.url.IServiceBaseUrlGetter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import static java.lang.String.format;
import static java.util.Arrays.asList;

@Slf4j
public class AgendaShowingControllable extends AbstractAssistanceControllable {

    private final IActionCallable<WebBrowserOpeningCallable.ActionArgs, UUID> webBrowserOpening;
    private final IActionCallable<WebBrowserClosingCallable.ActionArgs, Void> webBrowserClosing;
    private final IServiceBaseUrlGetter guiServiceBaseUrlGetter;
    private UUID webBrowserInstanceId;

    private AgendaShowingControllable(
            IAssistanceInfo agendaShowingInfo,
            IActionCallable<WebBrowserOpeningCallable.ActionArgs, UUID> webBrowserOpening,
            IActionCallable<WebBrowserClosingCallable.ActionArgs, Void> webBrowserClosing,
            IServiceBaseUrlGetter guiServiceBaseUrlGetter) {
        super(agendaShowingInfo);
        this.webBrowserOpening = webBrowserOpening;
        this.webBrowserClosing = webBrowserClosing;
        this.guiServiceBaseUrlGetter = guiServiceBaseUrlGetter;
    }

    @Override
    public void begin(IActionService actionService, IAssistanceContext context) {
        AgendaShowingInfo.Configuration config = toSpecificConfigType(
                AgendaShowingInfo.Configuration.class,
                context.getAssistanceConfiguration());
        // TODO: Exception message
        URL guiServiceBaseUrl = this.guiServiceBaseUrlGetter.getBaseUrl();
        URL eventAgendaUrl;
        try {
            eventAgendaUrl = new URL(
                    guiServiceBaseUrl.getProtocol(),
                    guiServiceBaseUrl.getHost(),
                    guiServiceBaseUrl.getPort(),
                    format(GuiApiConstants.URL_TEMPLATE_GET_CURRENT_EVENT_AGENDA_PAGE, context.getLocation().map(location -> location.getId().getIdValue()).orElseThrow(InsufficientContextException::new)));
        } catch (MalformedURLException e) {
            // TODO: Logging and appropriate exception
            throw new RuntimeException(e);
        }
        final WebBrowserOpeningCallable.ActionArgs webBrowserOpeningArgs = WebBrowserOpeningCallable.ActionArgs.of(
                config.getWebBrowserId(),
                config.getDisplayId(),
                asList(eventAgendaUrl));
        this.webBrowserInstanceId = this.webBrowserOpening.call(actionService, webBrowserOpeningArgs);
    }

    @Override
    public void end(IActionService actionService, IAssistanceContext context) {
        AgendaShowingInfo.Configuration config = toSpecificConfigType(
                AgendaShowingInfo.Configuration.class,
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
        private final IServiceBaseUrlGetter guiServiceBaseUrlGetter;

        public Factory(
                AgendaShowingInfo agendaShowingInfo,
                IActionCallable<WebBrowserOpeningCallable.ActionArgs, UUID> webBrowserOpening,
                IActionCallable<WebBrowserClosingCallable.ActionArgs, Void> webBrowserClosing,
                // TODO: String literal
                @Qualifier("guiServiceBaseUrlGetter") IServiceBaseUrlGetter guiServiceBaseUrlGetter) {
            super(agendaShowingInfo);
            this.webBrowserOpening = webBrowserOpening;
            this.webBrowserClosing = webBrowserClosing;
            this.guiServiceBaseUrlGetter = guiServiceBaseUrlGetter;
        }

        @Override
        public IAssistanceControllable newInstance() {
            return new AgendaShowingControllable(
                    this.assistanceInfo,
                    this.webBrowserOpening,
                    this.webBrowserClosing,
                    this.guiServiceBaseUrlGetter);
        }
    }
}
