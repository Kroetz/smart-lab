package de.qaware.smartlab.actuator.adapter.adapters.fileassociatedprogram;

import de.qaware.smartlab.core.data.actuator.IActuatorAdapter;
import de.qaware.smartlab.core.data.actuator.ActuatorId;

import java.nio.file.Path;
import java.util.UUID;

public interface IFileAssociatedProgramAdapter extends IActuatorAdapter {

    UUID openFile(Path fileToOpen);
    Path close(UUID programInstanceId);
    void maximizeOnDisplay(UUID programInstanceId, ActuatorId displayId);
}
