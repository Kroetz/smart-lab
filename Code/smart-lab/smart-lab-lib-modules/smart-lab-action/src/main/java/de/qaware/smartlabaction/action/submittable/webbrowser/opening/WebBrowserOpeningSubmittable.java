package de.qaware.smartlabaction.action.submittable.webbrowser.opening;

import de.qaware.smartlabaction.action.info.webbrowser.opening.WebBrowserOpeningInfo;
import de.qaware.smartlabaction.action.submittable.generic.AbstractActionSubmittable;
import de.qaware.smartlabapi.service.connector.action.IActionService;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.exception.InvalidActionResultException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class WebBrowserOpeningSubmittable extends AbstractActionSubmittable<WebBrowserOpeningSubmittable.ActionArgs, UUID> {

    public WebBrowserOpeningSubmittable(WebBrowserOpeningInfo webBrowserOpeningInfo) {
        super(webBrowserOpeningInfo);
    }

    public UUID submitExecution(IActionService actionService, ActionArgs actionArgs) {
        IActionResult actionResult = actionService.executeAction(this.actionInfo.getActionId(), actionArgs);
        return actionResult.getUuidValue().orElseThrow(InvalidActionResultException::new);
    }

    @Data
    @RequiredArgsConstructor(staticName = "of")     // TODO: Eliminate string literal
    @NoArgsConstructor // TODO: Really necessary for objects being able to serialize/deserialize?
    public static class ActionArgs implements IActionArgs {

        @NonNull
        private DeviceId webBrowserId;

        @NonNull
        private List<URL> urlsToOpen;
    }
}
