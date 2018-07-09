package de.qaware.smartlabassistance.assistance.controllable;

import de.qaware.smartlabaction.action.submittable.generic.IActionSubmittable;
import de.qaware.smartlabaction.action.submittable.webbrowser.closing.WebBrowserClosingSubmittable;
import de.qaware.smartlabaction.action.submittable.webbrowser.opening.WebBrowserOpeningSubmittable;
import de.qaware.smartlabapi.GuiApiConstants;
import de.qaware.smartlabapi.service.IServiceBaseUrlGetter;
import de.qaware.smartlabapi.service.action.IActionService;
import de.qaware.smartlabassistance.assistance.controllable.factory.AbstractAssistanceControllableFactory;
import de.qaware.smartlabassistance.assistance.info.AgendaShowingInfo;
import de.qaware.smartlabcore.data.assistance.IAssistanceInfo;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import de.qaware.smartlabcore.exception.InsufficientContextException;
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
    private final IServiceBaseUrlGetter guiBaseUrlGetter;
    private UUID webBrowserInstanceId;

    private AgendaShowingControllable(
            IAssistanceInfo agendaShowingInfo,
            IActionSubmittable<WebBrowserOpeningSubmittable.ActionArgs, UUID> webBrowserOpening,
            IActionSubmittable<WebBrowserClosingSubmittable.ActionArgs, Void> webBrowserClosing,
            IServiceBaseUrlGetter guiBaseUrlGetter) {
        super(agendaShowingInfo);
        this.webBrowserOpening = webBrowserOpening;
        this.webBrowserClosing = webBrowserClosing;
        this.guiBaseUrlGetter = guiBaseUrlGetter;
    }

    @Override
    public void begin(IActionService actionService, IAssistanceContext context) {
        // TODO: casting smells
        // TODO: Check for casting exception and throw illegalstateexception
        AgendaShowingInfo.Configuration config = (AgendaShowingInfo.Configuration) context.getAssistanceConfiguration();
        // TODO: Exception message
        URL guiBaseUrl = this.guiBaseUrlGetter.getBaseUrl();
        URL meetingAgendaUrl;
        try {
            meetingAgendaUrl = new URL(
                    guiBaseUrl.getProtocol(),
                    guiBaseUrl.getHost(),
                    guiBaseUrl.getPort(),
                    format(GuiApiConstants.URL_TEMPLATE_GET_CURRENT_MEETING_AGENDA_PAGE, context.getRoom().map(room -> room.getId().getIdValue()).orElseThrow(InsufficientContextException::new)));
        } catch (MalformedURLException e) {
            // TODO: Logging and appropriate exception
            throw new RuntimeException(e);
        }
        final WebBrowserOpeningSubmittable.ActionArgs webBrowserOpeningArgs = WebBrowserOpeningSubmittable.ActionArgs.of(
                config.getWebBrowserId(),
                asList(meetingAgendaUrl));
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
    public void update(IActionService actionService, IAssistanceContext context) {
        // TODO: Implementation
    }

    @Component
    @Slf4j
    public static class Factory extends AbstractAssistanceControllableFactory {

        private final IActionSubmittable<WebBrowserOpeningSubmittable.ActionArgs, UUID> webBrowserOpening;
        private final IActionSubmittable<WebBrowserClosingSubmittable.ActionArgs, Void> webBrowserClosing;
        private final IServiceBaseUrlGetter guiBaseUrlGetter;

        public Factory(
                AgendaShowingInfo agendaShowingInfo,
                IActionSubmittable<WebBrowserOpeningSubmittable.ActionArgs, UUID> webBrowserOpening,
                IActionSubmittable<WebBrowserClosingSubmittable.ActionArgs, Void> webBrowserClosing,
                // TODO: String literal
                @Qualifier("guiBaseUrlGetter") IServiceBaseUrlGetter guiBaseUrlGetter) {
            super(agendaShowingInfo);
            this.webBrowserOpening = webBrowserOpening;
            this.webBrowserClosing = webBrowserClosing;
            this.guiBaseUrlGetter = guiBaseUrlGetter;
        }

        @Override
        public IAssistanceControllable newInstance() {
            return new AgendaShowingControllable(
                    this.assistanceInfo,
                    this.webBrowserOpening,
                    this.webBrowserClosing,
                    this.guiBaseUrlGetter);
        }
    }
}
