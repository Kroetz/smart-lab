package de.qaware.smartlabaction.action.info.file.closing;

import de.qaware.smartlabaction.action.info.generic.AbstractActionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FileClosingInfo extends AbstractActionInfo {

    public static final String ACTION_ID = "file closing";

    public FileClosingInfo() {
        super(ACTION_ID);
    }
}
