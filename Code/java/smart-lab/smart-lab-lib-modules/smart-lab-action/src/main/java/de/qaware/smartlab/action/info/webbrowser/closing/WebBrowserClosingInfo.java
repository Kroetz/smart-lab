package de.qaware.smartlab.action.info.webbrowser.closing;

import de.qaware.smartlab.action.info.generic.AbstractActionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WebBrowserClosingInfo extends AbstractActionInfo {

    public static final String ACTION_ID = "web browser closing";

    public WebBrowserClosingInfo() {
        super(ACTION_ID);
    }
}
