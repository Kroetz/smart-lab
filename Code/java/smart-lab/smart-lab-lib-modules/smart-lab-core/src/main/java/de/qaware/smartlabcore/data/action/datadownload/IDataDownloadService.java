package de.qaware.smartlabcore.data.action.datadownload;

import de.qaware.smartlabcore.data.workgroup.IProjectBaseInfo;
import de.qaware.smartlabcore.exception.ServiceFailedException;

import java.nio.file.Path;

public interface IDataDownloadService {

    String getServiceId();
    Path download(
            IProjectBaseInfo projectBaseInfo,
            String filePath) throws ServiceFailedException;
}
