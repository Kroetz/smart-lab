package de.qaware.smartlabaction.action.info.beamer.deactivation;

import de.qaware.smartlabaction.action.info.generic.AbstractActionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BeamerDeactivationInfo extends AbstractActionInfo {

    public static final String ACTION_ID = "beamer deactivation";

    public BeamerDeactivationInfo() {
        super(ACTION_ID);
    }
}
