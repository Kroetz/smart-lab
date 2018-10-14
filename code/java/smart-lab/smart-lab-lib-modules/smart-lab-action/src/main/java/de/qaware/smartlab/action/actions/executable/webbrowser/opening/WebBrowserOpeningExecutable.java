package de.qaware.smartlab.action.actions.executable.webbrowser.opening;

import de.qaware.smartlab.action.actions.callable.webbrowser.opening.WebBrowserOpeningCallable;
import de.qaware.smartlab.action.actions.executable.generic.AbstractActionExecutable;
import de.qaware.smartlab.action.actions.info.webbrowser.opening.WebBrowserOpeningInfo;
import de.qaware.smartlab.action.result.UuidActionResult;
import de.qaware.smartlab.actuator.adapter.adapters.webbrowser.IWebBrowserAdapter;
import de.qaware.smartlab.api.service.connector.actuator.IActuatorManagementService;
import de.qaware.smartlab.core.action.generic.IActionResult;
import de.qaware.smartlab.core.resolver.IResolver;
import de.qaware.smartlab.core.exception.action.ActionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class WebBrowserOpeningExecutable extends AbstractActionExecutable<WebBrowserOpeningCallable.ActionArgs, IWebBrowserAdapter> {

    public WebBrowserOpeningExecutable(
            WebBrowserOpeningInfo webBrowserOpeningInfo,
            IActuatorManagementService actuatorManagementService,
            IResolver<String, IWebBrowserAdapter> webBrowserAdapterResolver) {
        super(
                webBrowserOpeningInfo,
                webBrowserAdapterResolver,
                actionArgs -> actuatorManagementService.findOne(actionArgs.getWebBrowserId()).getType(),
                actionArgs -> Optional.of(actuatorManagementService.findOne(actionArgs.getWebBrowserId()).getResponsibleDelegate()));
    }

    @Override
    protected IActionResult execute(IWebBrowserAdapter webBrowserAdapter, WebBrowserOpeningCallable.ActionArgs actionArgs) throws ActionException {
        UUID webBrowserInstanceId = webBrowserAdapter.newWebBrowserInstance(actionArgs.getUrlsToOpen());
        webBrowserAdapter.maximizeOnDisplay(webBrowserInstanceId, actionArgs.getDisplayId());
        return UuidActionResult.of(webBrowserInstanceId);
    }
}
