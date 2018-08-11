package de.qaware.smartlab.action.actions.callable.webbrowser.closing;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.qaware.smartlab.action.actions.callable.generic.AbstractActionCallable;
import de.qaware.smartlab.action.actions.info.webbrowser.closing.WebBrowserClosingInfo;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.actuator.ActuatorId;
import de.qaware.smartlab.core.exception.action.ActionException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class WebBrowserClosingCallable extends AbstractActionCallable<WebBrowserClosingCallable.ActionArgs, Void> {

    public WebBrowserClosingCallable(WebBrowserClosingInfo webBrowserClosingInfo) {
        super(webBrowserClosingInfo);
    }

    public Void call(IActionService actionService, ActionArgs actionArgs) throws ActionException {
        IActionResult actionResult = actionService.executeAction(this.actionInfo.getActionId(), actionArgs);
        return actionResult.getVoidValue();
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class ActionArgs implements IActionArgs {

        private static final String FIELD_NAME_WEB_BROWSER_ID = "webBrowserId";
        private static final String FIELD_NAME_WEB_BROWSER_INSTANCE_ID = "webBrowserInstanceId";

        @NonNull
        private final ActuatorId webBrowserId;

        @NonNull
        private final UUID webBrowserInstanceId;

        @JsonCreator
        public static ActionArgs of(
                @JsonProperty(FIELD_NAME_WEB_BROWSER_ID) ActuatorId webBrowserId,
                @JsonProperty(FIELD_NAME_WEB_BROWSER_INSTANCE_ID) UUID webBrowserInstanceId) {
            return new ActionArgs(webBrowserId, webBrowserInstanceId);
        }
    }
}
