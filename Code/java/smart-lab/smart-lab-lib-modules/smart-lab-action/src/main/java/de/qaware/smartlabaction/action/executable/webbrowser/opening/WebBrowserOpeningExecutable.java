package de.qaware.smartlabaction.action.executable.webbrowser.opening;

import de.qaware.smartlabactuatoradapter.actuator.webbrowser.IWebBrowserAdapter;
import de.qaware.smartlabaction.action.executable.generic.AbstractActionExecutable;
import de.qaware.smartlabaction.action.info.webbrowser.opening.WebBrowserOpeningInfo;
import de.qaware.smartlabaction.action.result.UuidActionResult;
import de.qaware.smartlabaction.action.submittable.webbrowser.opening.WebBrowserOpeningSubmittable;
import de.qaware.smartlabapi.service.connector.delegate.IDelegateService;
import de.qaware.smartlabapi.service.connector.device.IDeviceManagementService;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.generic.IResolver;
import de.qaware.smartlabcore.exception.UnknownDeviceAdapterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class WebBrowserOpeningExecutable extends AbstractActionExecutable {

    private final IDeviceManagementService deviceManagementService;
    private final IResolver<String, IWebBrowserAdapter> webBrowserAdapterResolver;

    public WebBrowserOpeningExecutable(
            WebBrowserOpeningInfo webBrowserOpeningInfo,
            IDeviceManagementService deviceManagementService,
            IResolver<String, IWebBrowserAdapter> webBrowserAdapterResolver) {
        super(webBrowserOpeningInfo);
        this.deviceManagementService = deviceManagementService;
        this.webBrowserAdapterResolver = webBrowserAdapterResolver;
    }

    // TODO: too much code duplicates
    public IActionResult execute(String webBrowserType, IActionArgs genericActionArgs) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        WebBrowserOpeningSubmittable.ActionArgs actionArgs = convertToSpecificActionArgs(
                WebBrowserOpeningSubmittable.ActionArgs.class,
                genericActionArgs);
        IWebBrowserAdapter webBrowserAdapter = this.webBrowserAdapterResolver
                .resolve(webBrowserType)
                .orElseThrow(UnknownDeviceAdapterException::new);
        if(!webBrowserAdapter.hasLocalApi()) throw new IllegalStateException();     // TODO: Better exception
        UUID webBrowserInstanceId = webBrowserAdapter.newWebBrowserInstance(actionArgs.getUrlsToOpen());
        webBrowserAdapter.maximizeOnDisplay(webBrowserInstanceId, actionArgs.getDisplayId());
        return UuidActionResult.of(webBrowserInstanceId);
    }

    public IActionResult execute(IActionArgs genericActionArgs, IDelegateService delegateService) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        WebBrowserOpeningSubmittable.ActionArgs actionArgs = convertToSpecificActionArgs(
                WebBrowserOpeningSubmittable.ActionArgs.class,
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
        UUID webBrowserInstanceId = webBrowserAdapter.newWebBrowserInstance(actionArgs.getUrlsToOpen());
        return UuidActionResult.of(webBrowserInstanceId);
    }
}
