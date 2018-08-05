package de.qaware.smartlabactuatoradapter.actuator.display;

import de.qaware.smartlabactuatoradapter.actuator.remotecontrol.IInfraredRemoteControl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TelefunkenXF48B400Adapter extends AbstractDisplayAdapter {

    public static final String DEVICE_TYPE = "telefunken_XF48B400";
    private static final boolean HAS_LOCAL_API = true;

    private final IInfraredRemoteControl infraredRemoteControl;

    public TelefunkenXF48B400Adapter(IInfraredRemoteControl infraredRemoteControl) {
        super(DEVICE_TYPE, HAS_LOCAL_API);
        this.infraredRemoteControl = infraredRemoteControl;
    }

    @Override
    public void activate() {
        this.infraredRemoteControl.on(DEVICE_TYPE);
    }

    @Override
    public void deactivate() {
        this.infraredRemoteControl.off(DEVICE_TYPE);
    }
}
