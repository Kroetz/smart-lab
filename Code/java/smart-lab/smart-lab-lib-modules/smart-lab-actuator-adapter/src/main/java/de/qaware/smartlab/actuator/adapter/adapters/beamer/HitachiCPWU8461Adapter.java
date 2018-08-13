package de.qaware.smartlab.actuator.adapter.adapters.beamer;

import de.qaware.smartlab.actuator.adapter.adapters.remotecontrol.IInfraredRemoteControl;
import de.qaware.smartlab.core.exception.actuator.ActuatorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HitachiCPWU8461Adapter extends AbstractBeamerAdapter {

    public static final String ACTUATOR_TYPE = "hitachi_CP_WU8461";
    private static final boolean HAS_LOCAL_API = true;

    private final IInfraredRemoteControl infraredRemoteControl;

    public HitachiCPWU8461Adapter(IInfraredRemoteControl infraredRemoteControl) {
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
