package de.qaware.smartlabassistance.assistance.controllable;

import de.qaware.smartlabaction.action.submittable.generic.IActionSubmittable;
import de.qaware.smartlabaction.action.submittable.webbrowser.closing.WebBrowserClosingSubmittable;
import de.qaware.smartlabaction.action.submittable.webbrowser.opening.WebBrowserOpeningSubmittable;
import de.qaware.smartlabapi.GuiApiConstants;
import de.qaware.smartlabapi.service.IServiceBaseUrlGetter;
import de.qaware.smartlabapi.service.action.IActionService;
import de.qaware.smartlabassistance.assistance.info.AgendaShowingInfo;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import de.qaware.smartlabcore.exception.InsufficientContextException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import static java.lang.String.format;

@Component
@Slf4j
public class AgendaShowingControllable extends AbstractAssistanceControllable {

    private final IActionSubmittable<WebBrowserOpeningSubmittable.ActionArgs, Void> webBrowserOpening;
    private final IActionSubmittable<WebBrowserClosingSubmittable.ActionArgs, Void> webBrowserClosing;
    private final IServiceBaseUrlGetter guiBaseUrlGetter;

    public AgendaShowingControllable(
            AgendaShowingInfo agendaShowingInfo,
            IActionSubmittable<WebBrowserOpeningSubmittable.ActionArgs, Void> webBrowserOpening,
            IActionSubmittable<WebBrowserClosingSubmittable.ActionArgs, Void> webBrowserClosing,
            // TODO: String literal
            @Qualifier("guiBaseUrlGetter") IServiceBaseUrlGetter guiBaseUrlGetter) {
        super(agendaShowingInfo);
        this.webBrowserOpening = webBrowserOpening;
        this.webBrowserClosing = webBrowserClosing;
        this.guiBaseUrlGetter = guiBaseUrlGetter;
    }

    @Override
    public void begin(IActionService actionService, IAssistanceContext context) {
        // TODO: casting smells
        // TODO: Check for casting exception and throw illegalstateexception
        AgendaShowingInfo.Configuration config = (AgendaShowingInfo.Configuration) context.getAssistanceConfiguration().orElseThrow(InsufficientContextException::new);
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
                Arrays.asList(meetingAgendaUrl));
        this.webBrowserOpening.submitExecution(actionService, webBrowserOpeningArgs);
    }

    @Override
    public void end(IActionService actionService, IAssistanceContext context) {
        // TODO: casting smells
        // TODO: Check for casting exception and throw illegalstateexception
        AgendaShowingInfo.Configuration config = (AgendaShowingInfo.Configuration) context.getAssistanceConfiguration().orElseThrow(InsufficientContextException::new);
        final WebBrowserClosingSubmittable.ActionArgs webBrowserClosingArgs = WebBrowserClosingSubmittable.ActionArgs.of(
                config.getWebBrowserId());
        this.webBrowserClosing.submitExecution(actionService, webBrowserClosingArgs);
    }

    @Override
    public void update(IActionService actionService, IAssistanceContext context) {
        // TODO: Implementation
    }
}
