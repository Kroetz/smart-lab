package de.qaware.smartlabaction.action.actor.beamer;

import de.qaware.smartlabaction.action.actor.generic.AbstractDeviceAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractBeamerAdapter extends AbstractDeviceAdapter implements IBeamerAdapter {

    public AbstractBeamerAdapter(String deviceType, boolean hasLocalApi) {
        super(deviceType, hasLocalApi);
    }
}
