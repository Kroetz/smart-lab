package de.qaware.smartlab.actuator.adapter.adapters.fileassociatedprogram;

import de.qaware.smartlab.actuator.adapter.adapters.generic.AbstractActuatorAdapter;
import de.qaware.smartlab.core.data.actuator.ActuatorId;
import de.qaware.smartlab.actuator.adapter.windowhandling.windowhandler.IWindowHandler;
import de.qaware.smartlab.core.exception.actuator.ActuatorException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.lang.String.format;
import static java.util.Objects.isNull;

@Slf4j
public abstract class AbstractFileAssociatedProgramAdapter extends AbstractActuatorAdapter implements IFileAssociatedProgramAdapter {

    protected final Map<UUID, IFileAssociatedProgramInstance> programInstancesByID;
    protected final IWindowHandler windowHandler;

    protected AbstractFileAssociatedProgramAdapter(
            String actuatorType,
            boolean hasLocalApi,
            IWindowHandler windowHandler) {
        super(actuatorType, hasLocalApi);
        this.programInstancesByID = new HashMap<>();
        this.windowHandler = windowHandler;
    }

    protected IFileAssociatedProgramInstance resolveProgramInstance(UUID programInstanceId) {
        IFileAssociatedProgramInstance pogramInstance = this.programInstancesByID.get(programInstanceId);
        if(isNull(pogramInstance)) {
            String errorMessage = format("A program instance with the ID %s does not exist", programInstanceId);
            log.error(errorMessage);
            throw new ActuatorException(errorMessage);
        }
        return pogramInstance;
    }

    @Override
    public void maximizeOnDisplay(UUID programInstanceId, ActuatorId displayId) throws ActuatorException {
        IFileAssociatedProgramInstance programInstance = resolveProgramInstance(programInstanceId);
        programInstance.getWindow().maximizeOnDisplay(displayId);
    }
}
