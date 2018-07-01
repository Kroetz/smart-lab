package de.qaware.smartlabaction.action.info.website.closing;

import de.qaware.smartlabaction.action.info.generic.AbstractActionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WebsiteClosingInfo extends AbstractActionInfo {

    public static final String ACTION_ID = "website closing";

    public WebsiteClosingInfo() {
        super(ACTION_ID);
    }
}
