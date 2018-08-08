package de.qaware.smartlab.assistance.assistances.controllable.agendashowing;

import de.qaware.smartlab.action.actions.submittable.generic.IActionSubmittable;
import de.qaware.smartlab.action.actions.submittable.webbrowser.closing.WebBrowserClosingSubmittable;
import de.qaware.smartlab.action.actions.submittable.webbrowser.opening.WebBrowserOpeningSubmittable;
import de.qaware.smartlab.api.service.constant.gui.GuiApiConstants;
import de.qaware.smartlab.assistance.assistances.controllable.generic.IAssistanceControllable;
import de.qaware.smartlab.assistance.assistances.controllable.generic.AbstractAssistanceControllable;
import de.qaware.smartlab.core.service.url.IServiceBaseUrlGetter;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.assistance.assistances.controllable.miscellaneous.factory.AbstractAssistanceControllableFactory;
import de.qaware.smartlab.assistance.assistances.info.agendashowing.AgendaShowingInfo;
import de.qaware.smartlab.assistance.assistances.info.generic.IAssistanceInfo;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import de.qaware.smartlab.core.exception.InsufficientContextException;
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

    private final IActionSubmittable<WebBrowserOpeningSubmittable.ActionArgs, UUID> webBrowserOpening;
    private final IActionSubmittable<WebBrowserClosingSubmittable.ActionArgs, Void> webBrowserClosing;
    private final IServiceBaseUrlGetter guiServiceBaseUrlGetter;
    private UUID webBrowserInstanceId;

    private AgendaShowingControllable(
            IAssistanceInfo agendaShowingInfo,
            IActionSubmittable<WebBrowserOpeningSubmittable.ActionArgs, UUID> webBrowserOpening,
            IActionSubmittable<WebBrowserClosingSubmittable.ActionArgs, Void> webBrowserClosing,
            IServiceBaseUrlGetter guiServiceBaseUrlGetter) {
        super(agendaShowingInfo);
        this.webBrowserOpening = webBrowserOpening;
        this.webBrowserClosing = webBrowserClosing;
        this.guiServiceBaseUrlGetter = guiServiceBaseUrlGetter;
    }

    @Override
    public void begin(IActionService actionService, IAssistanceContext context) {
        // TODO: casting smells
        // TODO: Check for casting exception and throw illegalstateexception
        AgendaShowingInfo.Configuration config = (AgendaShowingInfo.Configuration) context.getAssistanceConfiguration();
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
        final WebBrowserOpeningSubmittable.ActionArgs webBrowserOpeningArgs = WebBrowserOpeningSubmittable.ActionArgs.of(
                config.getWebBrowserId(),
                config.getDisplayId(),
                asList(eventAgendaUrl));
        this.webBrowserInstanceId = this.webBrowserOpening.submitExecution(actionService, webBrowserOpeningArgs);
    }

    @Override
    public void end(IActionService actionService, IAssistanceContext context) {
        // TODO: casting smells
        // TODO: Check for casting exception and throw illegalstateexception
        AgendaShowingInfo.Configuration config = (AgendaShowingInfo.Configuration) context.getAssistanceConfiguration();
        final WebBrowserClosingSubmittable.ActionArgs webBrowserClosingArgs = WebBrowserClosingSubmittable.ActionArgs.of(
                config.getWebBrowserId(),
                this.webBrowserInstanceId);
        this.webBrowserClosing.submitExecution(actionService, webBrowserClosingArgs);
    }

    @Override
    public void during(IActionService actionService, IAssistanceContext context) {
        // TODO: Implementation
    }

    @Component
    @Slf4j
    public static class Factory extends AbstractAssistanceControllableFactory {

        private final IActionSubmittable<WebBrowserOpeningSubmittable.ActionArgs, UUID> webBrowserOpening;
        private final IActionSubmittable<WebBrowserClosingSubmittable.ActionArgs, Void> webBrowserClosing;
        private final IServiceBaseUrlGetter guiServiceBaseUrlGetter;

        public Factory(
                AgendaShowingInfo agendaShowingInfo,
                IActionSubmittable<WebBrowserOpeningSubmittable.ActionArgs, UUID> webBrowserOpening,
                IActionSubmittable<WebBrowserClosingSubmittable.ActionArgs, Void> webBrowserClosing,
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
