package de.qaware.smartlab.action.actions.executable.webbrowser.opening;

import de.qaware.smartlab.actuator.adapter.adapters.webbrowser.IWebBrowserAdapter;
import de.qaware.smartlab.action.actions.executable.generic.AbstractActionExecutable;
import de.qaware.smartlab.action.actions.info.webbrowser.opening.WebBrowserOpeningInfo;
import de.qaware.smartlab.action.result.UuidActionResult;
import de.qaware.smartlab.action.actions.callable.webbrowser.opening.WebBrowserOpeningCallable;
import de.qaware.smartlab.api.service.connector.delegate.IDelegateService;
import de.qaware.smartlab.api.service.connector.actuator.IActuatorManagementService;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.actuator.IActuator;
import de.qaware.smartlab.core.data.generic.IResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static java.lang.String.format;

@Component
@Slf4j
public class WebBrowserOpeningExecutable extends AbstractActionExecutable {

    private final IActuatorManagementService actuatorManagementService;
    private final IResolver<String, IWebBrowserAdapter> webBrowserAdapterResolver;

    public WebBrowserOpeningExecutable(
            WebBrowserOpeningInfo webBrowserOpeningInfo,
            IActuatorManagementService actuatorManagementService,
            IResolver<String, IWebBrowserAdapter> webBrowserAdapterResolver) {
        super(webBrowserOpeningInfo);
        this.actuatorManagementService = actuatorManagementService;
        this.webBrowserAdapterResolver = webBrowserAdapterResolver;
    }

    // TODO: too much code duplicates
    public IActionResult execute(String actuatorType, IActionArgs genericActionArgs) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        WebBrowserOpeningCallable.ActionArgs actionArgs = toSpecificArgsType(
                WebBrowserOpeningCallable.ActionArgs.class,
                genericActionArgs);
        IWebBrowserAdapter webBrowserAdapter = this.webBrowserAdapterResolver.resolve(actuatorType);
        if(!webBrowserAdapter.hasLocalApi()) throw new IllegalStateException();     // TODO: Better exception
        UUID webBrowserInstanceId = webBrowserAdapter.newWebBrowserInstance(actionArgs.getUrlsToOpen());
        webBrowserAdapter.maximizeOnDisplay(webBrowserInstanceId, actionArgs.getDisplayId());
        return UuidActionResult.of(webBrowserInstanceId);
    }

    public IActionResult execute(IActionArgs genericActionArgs, IDelegateService delegateService) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        WebBrowserOpeningCallable.ActionArgs actionArgs = toSpecificArgsType(
                WebBrowserOpeningCallable.ActionArgs.class,
                genericActionArgs);
        IActuator webBrowser = this.actuatorManagementService.findOne(actionArgs.getWebBrowserId());
        String actuatorType = webBrowser.getType();
        IWebBrowserAdapter webBrowserAdapter = this.webBrowserAdapterResolver.resolve(actuatorType);
        if(webBrowserAdapter.hasLocalApi()) return delegateService.executeAction(
                webBrowser.getResponsibleDelegate(),
                this.actionInfo.getActionId(),
                actuatorType,
                actionArgs);
        UUID webBrowserInstanceId = webBrowserAdapter.newWebBrowserInstance(actionArgs.getUrlsToOpen());
        return UuidActionResult.of(webBrowserInstanceId);
    }
}
