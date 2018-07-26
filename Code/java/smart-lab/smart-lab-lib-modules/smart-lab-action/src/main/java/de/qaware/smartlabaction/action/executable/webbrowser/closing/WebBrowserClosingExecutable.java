package de.qaware.smartlabaction.action.executable.webbrowser.closing;

import de.qaware.smartlabaction.action.actor.webbrowser.IWebBrowserAdapter;
import de.qaware.smartlabaction.action.executable.generic.AbstractActionExecutable;
import de.qaware.smartlabaction.action.info.webbrowser.closing.WebBrowserClosingInfo;
import de.qaware.smartlabaction.action.result.VoidActionResult;
import de.qaware.smartlabaction.action.submittable.webbrowser.closing.WebBrowserClosingSubmittable;
import de.qaware.smartlabapi.service.connector.delegate.IDelegateService;
import de.qaware.smartlabapi.service.connector.device.IDeviceManagementService;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.generic.IResolver;
import de.qaware.smartlabcore.exception.UnknownDeviceAdapterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WebBrowserClosingExecutable extends AbstractActionExecutable {

    private final IDeviceManagementService deviceManagementService;
    private final IResolver<String, IWebBrowserAdapter> webBrowserAdapterResolver;

    public WebBrowserClosingExecutable(
            WebBrowserClosingInfo webBrowserClosingInfo,
            IDeviceManagementService deviceManagementService,
            IResolver<String, IWebBrowserAdapter> webBrowserAdapterResolver) {
        super(webBrowserClosingInfo);
        this.deviceManagementService = deviceManagementService;
        this.webBrowserAdapterResolver = webBrowserAdapterResolver;
    }

    // TODO: too much code duplicates
    public IActionResult execute(String webBrowserType, IActionArgs genericActionArgs) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        WebBrowserClosingSubmittable.ActionArgs actionArgs = convertToSpecificActionArgs(
                WebBrowserClosingSubmittable.ActionArgs.class,
                genericActionArgs);
        IWebBrowserAdapter webBrowserAdapter = this.webBrowserAdapterResolver
                .resolve(webBrowserType)
                .orElseThrow(UnknownDeviceAdapterException::new);
        if(!webBrowserAdapter.hasLocalApi()) throw new IllegalStateException();     // TODO: Better exception
        webBrowserAdapter.closeUnchangedAutoOpenedTabs(actionArgs.getWebBrowserInstanceId());
        return VoidActionResult.instance();
    }

    public IActionResult execute(IActionArgs genericActionArgs, IDelegateService delegateService) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        WebBrowserClosingSubmittable.ActionArgs actionArgs = convertToSpecificActionArgs(
                WebBrowserClosingSubmittable.ActionArgs.class,
                genericActionArgs);
        IDevice webBrowser = this.deviceManagementService.findOne(actionArgs.getWebBrowserId());
        String webBrowserType = webBrowser.getType();
        IWebBrowserAdapter webBrowserAdapter = this.webBrowserAdapterResolver
                .resolve(webBrowserType)
                .orElseThrow(UnknownDeviceAdapterException::new);
        if(webBrowserAdapter.hasLocalApi()) return delegateService.executeAction(
                webBrowser.getResponsibleDelegate(),
                this.actionInfo.getActionId(),
                webBrowserType,
                actionArgs);
        webBrowserAdapter.closeUnchangedAutoOpenedTabs(actionArgs.getWebBrowserInstanceId());
        return VoidActionResult.instance();
    }
}