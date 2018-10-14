package de.qaware.smartlab.action.actions.executable.webbrowser.closing;

import de.qaware.smartlab.action.actions.callable.webbrowser.closing.WebBrowserClosingCallable;
import de.qaware.smartlab.action.actions.executable.generic.AbstractActionExecutable;
import de.qaware.smartlab.action.actions.info.webbrowser.closing.WebBrowserClosingInfo;
import de.qaware.smartlab.action.result.VoidActionResult;
import de.qaware.smartlab.actuator.adapter.adapters.webbrowser.IWebBrowserAdapter;
import de.qaware.smartlab.api.service.connector.actuator.IActuatorManagementService;
import de.qaware.smartlab.core.action.generic.IActionResult;
import de.qaware.smartlab.core.resolver.IResolver;
import de.qaware.smartlab.core.exception.action.ActionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class WebBrowserClosingExecutable extends AbstractActionExecutable<WebBrowserClosingCallable.ActionArgs, IWebBrowserAdapter> {

    public WebBrowserClosingExecutable(
            WebBrowserClosingInfo webBrowserClosingInfo,
            IActuatorManagementService actuatorManagementService,
            IResolver<String, IWebBrowserAdapter> webBrowserAdapterResolver) {
        super(
                webBrowserClosingInfo,
                webBrowserAdapterResolver,
                actionArgs -> actuatorManagementService.findOne(actionArgs.getWebBrowserId()).getType(),
                actionArgs -> Optional.of(actuatorManagementService.findOne(actionArgs.getWebBrowserId()).getResponsibleDelegate()));
    }

    @Override
    protected IActionResult execute(IWebBrowserAdapter webBrowserAdapter, WebBrowserClosingCallable.ActionArgs actionArgs) throws ActionException {
        webBrowserAdapter.closeUnchangedAutoOpenedTabs(actionArgs.getWebBrowserInstanceId());
        return VoidActionResult.newInstance();
    }
}
