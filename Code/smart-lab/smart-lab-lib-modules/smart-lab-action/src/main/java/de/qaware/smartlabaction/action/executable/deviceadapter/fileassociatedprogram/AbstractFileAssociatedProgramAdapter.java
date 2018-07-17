package de.qaware.smartlabaction.action.executable.deviceadapter.fileassociatedprogram;

import de.qaware.smartlabcore.data.device.AbstractDeviceAdapter;
import de.qaware.smartlabcore.exception.LocalDeviceException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.lang.String.format;
import static java.util.Objects.isNull;

@Slf4j
public abstract class AbstractFileAssociatedProgramAdapter extends AbstractDeviceAdapter implements IFileAssociatedProgramAdapter {

    protected final Map<UUID, Process> programInstancesByID;

    public AbstractFileAssociatedProgramAdapter(String programType, boolean hasLocalApi) {
        super(programType, hasLocalApi);
        this.programInstancesByID = new HashMap<>();
    }

    protected Process resolveProgramInstance(UUID programInstanceId) {
        Process pogramInstance = this.programInstancesByID.get(programInstanceId);
        if(isNull(pogramInstance)) throw new LocalDeviceException(format(
                "The specified program instance with the ID %s does not exist", programInstanceId));
        return pogramInstance;
    }
}
