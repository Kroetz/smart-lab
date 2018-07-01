package de.qaware.smartlabaction.action.info.website.opening;

import de.qaware.smartlabaction.action.info.generic.AbstractActionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WebsiteOpeningInfo extends AbstractActionInfo {

    public static final String ACTION_ID = "website opening";

    public WebsiteOpeningInfo() {
        super(ACTION_ID);
    }
}
