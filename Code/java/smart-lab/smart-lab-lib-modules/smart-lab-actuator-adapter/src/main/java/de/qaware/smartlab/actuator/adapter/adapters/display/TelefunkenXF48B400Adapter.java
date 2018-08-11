package de.qaware.smartlab.actuator.adapter.adapters.display;

import de.qaware.smartlab.actuator.adapter.adapters.remotecontrol.IInfraredRemoteControl;
import de.qaware.smartlab.core.exception.actuator.ActuatorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TelefunkenXF48B400Adapter extends AbstractDisplayAdapter {

    public static final String ACTUATOR_TYPE = "telefunken_XF48B400";
    private static final boolean HAS_LOCAL_API = true;

    private final IInfraredRemoteControl infraredRemoteControl;

    public TelefunkenXF48B400Adapter(IInfraredRemoteControl infraredRemoteControl) {
        super(ACTUATOR_TYPE, HAS_LOCAL_API);
        this.infraredRemoteControl = infraredRemoteControl;
    }

    @Override
    public void activate() throws ActuatorException {
        this.infraredRemoteControl.on(ACTUATOR_TYPE);
    }

    @Override
    public void deactivate() throws ActuatorException {
        this.infraredRemoteControl.off(ACTUATOR_TYPE);
    }
}
