package de.qaware.smartlab.actuator.adapter.adapters.fileassociatedprogram;

import de.qaware.smartlab.actuator.adapter.adapters.generic.IDeviceAdapter;
import de.qaware.smartlab.core.data.device.DeviceId;

import java.nio.file.Path;
import java.util.UUID;

public interface IFileAssociatedProgramAdapter extends IDeviceAdapter {

    UUID openFile(Path fileToOpen);
    Path close(UUID programInstanceId);
    void maximizeOnDisplay(UUID programInstanceId, DeviceId displayId);
}
