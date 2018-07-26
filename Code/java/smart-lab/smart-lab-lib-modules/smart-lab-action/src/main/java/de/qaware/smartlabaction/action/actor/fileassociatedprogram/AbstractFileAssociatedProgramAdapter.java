package de.qaware.smartlabaction.action.actor.fileassociatedprogram;

import de.qaware.smartlabaction.action.actor.generic.AbstractDeviceAdapter;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.exception.LocalDeviceException;
import de.qaware.smartlabcore.windowhandling.IWindowHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.lang.String.format;
import static java.util.Objects.isNull;

@Slf4j
public abstract class AbstractFileAssociatedProgramAdapter extends AbstractDeviceAdapter implements IFileAssociatedProgramAdapter {

    protected final Map<UUID, IFileAssociatedProgramInstance> programInstancesByID;
    protected final IWindowHandler windowHandler;

    public AbstractFileAssociatedProgramAdapter(
            String programType,
            boolean hasLocalApi,
            IWindowHandler windowHandler) {
        super(programType, hasLocalApi);
        this.programInstancesByID = new HashMap<>();
        this.windowHandler = windowHandler;
    }

    protected IFileAssociatedProgramInstance resolveProgramInstance(UUID programInstanceId) {
        IFileAssociatedProgramInstance pogramInstance = this.programInstancesByID.get(programInstanceId);
        if(isNull(pogramInstance)) throw new LocalDeviceException(format(
                "The specified program instance with the ID %s does not exist", programInstanceId));
        return pogramInstance;
    }

    @Override
    public void maximizeOnDisplay(UUID programInstanceId, DeviceId displayId) {
        IFileAssociatedProgramInstance programInstance = resolveProgramInstance(programInstanceId);
        programInstance.getWindow().maximizeOnDisplay(displayId);
    }
}
