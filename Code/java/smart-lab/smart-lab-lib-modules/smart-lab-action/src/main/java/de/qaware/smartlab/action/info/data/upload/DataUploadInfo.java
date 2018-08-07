package de.qaware.smartlab.action.info.data.upload;

import de.qaware.smartlab.action.info.generic.AbstractActionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataUploadInfo extends AbstractActionInfo {

    public static final String ACTION_ID = "data upload";

    public DataUploadInfo() {
        super(ACTION_ID);
    }
}
