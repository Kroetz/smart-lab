package de.qaware.smartlabaction.action.actor.beamer;

import de.qaware.smartlabcore.data.device.IDeviceAdapter;

public interface IBeamerAdapter extends IDeviceAdapter {

    void activate();
    void deactivate();
}
