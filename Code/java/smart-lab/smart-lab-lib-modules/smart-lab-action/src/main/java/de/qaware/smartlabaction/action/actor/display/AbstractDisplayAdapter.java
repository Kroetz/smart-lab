package de.qaware.smartlabaction.action.actor.display;

import de.qaware.smartlabaction.action.actor.generic.AbstractDeviceAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractDisplayAdapter extends AbstractDeviceAdapter implements IDisplayAdapter {

    public AbstractDisplayAdapter(String deviceType, boolean hasLocalApi) {
        super(deviceType, hasLocalApi);
    }
}
