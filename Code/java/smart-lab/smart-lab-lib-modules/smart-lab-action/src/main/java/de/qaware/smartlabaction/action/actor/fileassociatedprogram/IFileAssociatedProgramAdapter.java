package de.qaware.smartlabaction.action.actor.fileassociatedprogram;

import de.qaware.smartlabaction.action.actor.generic.IDeviceAdapter;
import de.qaware.smartlabcore.data.device.entity.DeviceId;

import java.nio.file.Path;
import java.util.UUID;

public interface IFileAssociatedProgramAdapter extends IDeviceAdapter {

    UUID openFile(Path fileToOpen);
    Path close(UUID programInstanceId);
    void maximizeOnDisplay(UUID programInstanceId, DeviceId displayId);
}
