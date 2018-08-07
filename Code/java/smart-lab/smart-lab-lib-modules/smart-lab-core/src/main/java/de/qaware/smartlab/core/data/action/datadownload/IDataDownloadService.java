package de.qaware.smartlab.core.data.action.datadownload;

import de.qaware.smartlab.core.data.workgroup.IProjectBaseInfo;
import de.qaware.smartlab.core.exception.ServiceFailedException;

import java.nio.file.Path;

public interface IDataDownloadService {

    String getServiceId();
    Path download(
            IProjectBaseInfo projectBaseInfo,
            String filePath) throws ServiceFailedException;
}
