package de.qaware.smartlab.action.actions.executable.webbrowser.closing;

import de.qaware.smartlab.actuator.adapter.adapters.webbrowser.IWebBrowserAdapter;
import de.qaware.smartlab.action.actions.executable.generic.AbstractActionExecutable;
import de.qaware.smartlab.action.actions.info.webbrowser.closing.WebBrowserClosingInfo;
import de.qaware.smartlab.action.result.VoidActionResult;
import de.qaware.smartlab.action.actions.submittable.webbrowser.closing.WebBrowserClosingSubmittable;
import de.qaware.smartlab.api.service.connector.delegate.IDelegateService;
import de.qaware.smartlab.api.service.connector.actuator.IActuatorManagementService;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.actuator.IActuator;
import de.qaware.smartlab.core.data.generic.IResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WebBrowserClosingExecutable extends AbstractActionExecutable {

    private final IActuatorManagementService actuatorManagementService;
    private final IResolver<String, IWebBrowserAdapter> webBrowserAdapterResolver;

    public WebBrowserClosingExecutable(
            WebBrowserClosingInfo webBrowserClosingInfo,
            IActuatorManagementService actuatorManagementService,
            IResolver<String, IWebBrowserAdapter> webBrowserAdapterResolver) {
        super(webBrowserClosingInfo);
        this.actuatorManagementService = actuatorManagementService;
        this.webBrowserAdapterResolver = webBrowserAdapterResolver;
    }

    // TODO: too much code duplicates
    public IActionResult execute(String actuatorType, IActionArgs genericActionArgs) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        WebBrowserClosingSubmittable.ActionArgs actionArgs = toSpecificArgsType(
                WebBrowserClosingSubmittable.ActionArgs.class,
                genericActionArgs);
        IWebBrowserAdapter webBrowserAdapter = this.webBrowserAdapterResolver.resolve(actuatorType);
        if(!webBrowserAdapter.hasLocalApi()) throw new IllegalStateException();     // TODO: Better exception
        webBrowserAdapter.closeUnchangedAutoOpenedTabs(actionArgs.getWebBrowserInstanceId());
        return VoidActionResult.newInstance();
    }

    public IActionResult execute(IActionArgs genericActionArgs, IDelegateService delegateService) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        WebBrowserClosingSubmittable.ActionArgs actionArgs = toSpecificArgsType(
                WebBrowserClosingSubmittable.ActionArgs.class,
                genericActionArgs);
        IActuator webBrowser = this.actuatorManagementService.findOne(actionArgs.getWebBrowserId());
        String actuatorType = webBrowser.getType();
        IWebBrowserAdapter webBrowserAdapter = this.webBrowserAdapterResolver.resolve(actuatorType);
        if(webBrowserAdapter.hasLocalApi()) return delegateService.executeAction(
                webBrowser.getResponsibleDelegate(),
                this.actionInfo.getActionId(),
                actuatorType,
                actionArgs);
        webBrowserAdapter.closeUnchangedAutoOpenedTabs(actionArgs.getWebBrowserInstanceId());
        return VoidActionResult.newInstance();
    }
}
