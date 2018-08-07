package de.qaware.smartlab.action.info.webbrowser.opening;

import de.qaware.smartlab.action.info.generic.AbstractActionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WebBrowserOpeningInfo extends AbstractActionInfo {

    public static final String ACTION_ID = "web browser opening";

    public WebBrowserOpeningInfo() {
        super(ACTION_ID);
    }
}
