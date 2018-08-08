package de.qaware.smartlab.core.data.action.dataupload;

import de.qaware.smartlab.core.data.actuator.IDeviceAdapter;
import de.qaware.smartlab.core.data.workgroup.IProjectBaseInfo;
import de.qaware.smartlab.core.exception.ServiceFailedException;

public interface IDataUploadService extends IDeviceAdapter {

    void upload(
            IProjectBaseInfo projectBaseInfo,
            String uploadMessage,
            String dir,
            String fileName,
            String dataToUpload) throws ServiceFailedException;
}
