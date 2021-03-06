package de.qaware.smartlab.actuator.adapter.adapters.projectbase;

import de.qaware.smartlab.core.data.actuator.IActuatorAdapter;
import de.qaware.smartlab.core.data.workgroup.IProjectBaseInfo;
import de.qaware.smartlab.core.exception.actuator.ActuatorException;

public interface IDataUploadService extends IActuatorAdapter {

    void upload(
            IProjectBaseInfo projectBaseInfo,
            String uploadMessage,
            String dir,
            String fileName,
            byte[] dataToUpload) throws ActuatorException;
}
