package de.qaware.smartlabaction.action.submittable.website.opening;

import de.qaware.smartlabaction.action.info.website.opening.WebsiteOpeningInfo;
import de.qaware.smartlabaction.action.submittable.generic.AbstractActionSubmittable;
import de.qaware.smartlabapi.service.action.IActionService;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class WebsiteOpeningSubmittable extends AbstractActionSubmittable<WebsiteOpeningSubmittable.ActionArgs, Void> {

    public WebsiteOpeningSubmittable(WebsiteOpeningInfo websiteOpeningInfo) {
        super(websiteOpeningInfo);
    }

    public Void submitExecution(IActionService actionService, ActionArgs actionArgs) {
        IActionResult actionResult = actionService.executeAction(this.actionInfo.getActionId(), actionArgs);
        return actionResult.getVoidValue();
    }

    @Data
    @RequiredArgsConstructor(staticName = "of")     // TODO: Eliminate string literal
    @NoArgsConstructor // TODO: Really necessary for objects being able to serialize/deserialize?
    public static class ActionArgs implements IActionArgs {

        @NonNull
        private UUID webBrowserId;
    }
}
