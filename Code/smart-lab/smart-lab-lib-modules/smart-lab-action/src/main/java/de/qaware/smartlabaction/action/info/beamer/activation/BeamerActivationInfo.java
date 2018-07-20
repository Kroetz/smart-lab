package de.qaware.smartlabaction.action.info.beamer.activation;

import de.qaware.smartlabaction.action.info.generic.AbstractActionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BeamerActivationInfo extends AbstractActionInfo {

    public static final String ACTION_ID = "beamer activation";

    public BeamerActivationInfo() {
        super(ACTION_ID);
    }
}
