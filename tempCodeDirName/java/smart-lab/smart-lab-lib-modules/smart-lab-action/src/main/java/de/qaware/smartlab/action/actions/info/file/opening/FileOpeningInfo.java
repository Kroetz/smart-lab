package de.qaware.smartlab.action.actions.info.file.opening;

import de.qaware.smartlab.action.actions.info.generic.AbstractActionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FileOpeningInfo extends AbstractActionInfo {

    public static final String ACTION_ID = "file opening";

    public FileOpeningInfo() {
        super(ACTION_ID);
    }
}
