package de.qaware.smartlabaction.action.executable.deviceadapter.fileassociatedprogram;

import de.qaware.smartlabcore.data.device.IDeviceAdapter;

import java.util.UUID;

public interface IFileAssociatedProgramAdapter extends IDeviceAdapter {

    UUID openFile(String path);
    void close(UUID programInstance);
}
