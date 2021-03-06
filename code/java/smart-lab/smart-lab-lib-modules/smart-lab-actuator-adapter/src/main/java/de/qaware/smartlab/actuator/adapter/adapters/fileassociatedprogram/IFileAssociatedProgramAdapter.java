package de.qaware.smartlab.actuator.adapter.adapters.fileassociatedprogram;

import de.qaware.smartlab.core.data.actuator.IActuatorAdapter;
import de.qaware.smartlab.core.data.actuator.ActuatorId;
import de.qaware.smartlab.core.exception.actuator.ActuatorException;

import java.nio.file.Path;
import java.util.UUID;

public interface IFileAssociatedProgramAdapter extends IActuatorAdapter {

    UUID openFile(Path fileToOpen) throws ActuatorException;
    Path close(UUID programInstanceId) throws ActuatorException;
    void maximizeOnDisplay(UUID programInstanceId, ActuatorId displayId) throws ActuatorException;
}
