package de.qaware.smartlabcommons.data.device.microphone;

import de.qaware.smartlabcommons.api.service.delegate.IDelegateService;
import de.qaware.smartlabcommons.data.action.IActionArgs;
import de.qaware.smartlabcommons.data.action.microphone.ActivateMicrophone;
import de.qaware.smartlabcommons.data.device.entity.IDevice;
import de.qaware.smartlabcommons.data.room.IRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Component
@Slf4j
public class ThinkpadP50InternalMicrophoneAdapter extends AbstractMicrophoneAdapter {

    public static final String DEVICE_TYPE = "thinkpad p50 internal microphone";
    private final IDelegateService delegateService;

    public ThinkpadP50InternalMicrophoneAdapter(IDelegateService delegateService) {
        super(DEVICE_TYPE);
        this.delegateService = delegateService;
    }

    @Override
    public void activate(IRoom room, IDevice device, boolean executeLocally) {
        if(executeLocally) {
            // TODO: Implementation
        }
        else {
            String responsibleDelegate = device.getResponsibleDelegate();
            // TODO: This class should not be responsible for deciding whether an action shall be executed locally or remote.
            IActionArgs actionArgs = ActivateMicrophone.ActionArgs.of(room.getId(), device.getId(), true);
            // TODO: At this stage it should not be handled with actionIDs. DelegateService should mirror another layer BEHIND actions (e.g. device-function) and accept an ID of that layer. Therefore introduce a new layer, API (should also consist only of execute and take a device function as param) and service that executes device functions
            this.delegateService.executeAction(responsibleDelegate, ActivateMicrophone.ACTION_ID, actionArgs);
        }
    }

    @Override
    public void deactivate(IRoom room, IDevice device, boolean executeLocally) {
        throw new NotImplementedException();
    }
}
