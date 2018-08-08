package de.qaware.smartlab.actuator.adapter.adapters.fileassociatedprogram;

import de.qaware.smartlab.actuator.adapter.adapters.generic.AbstractDeviceAdapter;
import de.qaware.smartlab.core.data.device.DeviceId;
import de.qaware.smartlab.core.exception.LocalDeviceException;
import de.qaware.smartlab.actuator.adapter.windowhandling.windowhandler.IWindowHandler;
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
            String deviceType,
            boolean hasLocalApi,
            IWindowHandler windowHandler) {
        super(deviceType, hasLocalApi);
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
