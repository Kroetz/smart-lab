package de.qaware.smartlab.action.actions.callable.webbrowser.opening;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.qaware.smartlab.action.actions.callable.generic.AbstractActionCallable;
import de.qaware.smartlab.action.actions.info.webbrowser.opening.WebBrowserOpeningInfo;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.actuator.ActuatorId;
import de.qaware.smartlab.core.exception.InvalidActionResultException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class WebBrowserOpeningCallable extends AbstractActionCallable<WebBrowserOpeningCallable.ActionArgs, UUID> {

    public WebBrowserOpeningCallable(WebBrowserOpeningInfo webBrowserOpeningInfo) {
        super(webBrowserOpeningInfo);
    }

    public UUID call(IActionService actionService, ActionArgs actionArgs) {
        IActionResult actionResult = actionService.executeAction(this.actionInfo.getActionId(), actionArgs);
        return actionResult.getUuidValue().orElseThrow(InvalidActionResultException::new);
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class ActionArgs implements IActionArgs {

        private static final String FIELD_NAME_WEB_BROWSER_ID = "webBrowserId";
        private static final String FIELD_NAME_DISPLAY_ID = "displayId";
        private static final String FIELD_NAME_URLS_TO_OPEN = "urlsToOpen";

        @NonNull
        private final ActuatorId webBrowserId;

        @NonNull
        private final ActuatorId displayId;

        @NonNull
        private final List<URL> urlsToOpen;

        @JsonCreator
        public static ActionArgs of(
                @JsonProperty(FIELD_NAME_WEB_BROWSER_ID) ActuatorId webBrowserId,
                @JsonProperty(FIELD_NAME_DISPLAY_ID) ActuatorId displayId,
                @JsonProperty(FIELD_NAME_URLS_TO_OPEN) List<URL> urlsToOpen) {
            return new ActionArgs(webBrowserId, displayId, urlsToOpen);
        }
    }
}
