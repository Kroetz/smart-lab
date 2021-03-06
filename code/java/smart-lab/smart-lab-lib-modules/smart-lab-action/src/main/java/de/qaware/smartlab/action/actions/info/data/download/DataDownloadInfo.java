package de.qaware.smartlab.action.actions.info.data.download;

import de.qaware.smartlab.action.actions.info.generic.AbstractActionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataDownloadInfo extends AbstractActionInfo {

    public static final String ACTION_ID = "data download";

    public DataDownloadInfo() {
        super(ACTION_ID);
    }
}
