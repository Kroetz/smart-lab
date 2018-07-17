package de.qaware.smartlabaction.action.executable.deviceadapter.fileassociatedprogram;

import de.qaware.smartlabcore.data.device.IDeviceAdapter;

import java.nio.file.Path;
import java.util.UUID;

public interface IFileAssociatedProgramAdapter extends IDeviceAdapter {

    UUID openFile(Path fileToOpen);
    Path close(UUID programInstanceId);
}
