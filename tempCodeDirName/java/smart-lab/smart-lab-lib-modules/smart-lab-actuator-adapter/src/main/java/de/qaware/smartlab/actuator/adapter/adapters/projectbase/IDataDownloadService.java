package de.qaware.smartlab.actuator.adapter.adapters.projectbase;

import de.qaware.smartlab.core.data.actuator.IActuatorAdapter;
import de.qaware.smartlab.core.data.workgroup.IProjectBaseInfo;
import de.qaware.smartlab.core.exception.actuator.ActuatorException;

import java.nio.file.Path;

public interface IDataDownloadService extends IActuatorAdapter {

    Path download(
            IProjectBaseInfo projectBaseInfo,
            String filePath) throws ActuatorException;
}
