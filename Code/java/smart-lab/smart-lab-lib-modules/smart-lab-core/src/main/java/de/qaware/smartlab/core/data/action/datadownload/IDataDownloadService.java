package de.qaware.smartlab.core.data.action.datadownload;

import de.qaware.smartlab.core.data.actuator.IDeviceAdapter;
import de.qaware.smartlab.core.data.workgroup.IProjectBaseInfo;
import de.qaware.smartlab.core.exception.ServiceFailedException;

import java.nio.file.Path;

public interface IDataDownloadService extends IDeviceAdapter {

    Path download(
            IProjectBaseInfo projectBaseInfo,
            String filePath) throws ServiceFailedException;
}
