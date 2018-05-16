package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabcommons.api.service.workgroup.IWorkgroupManagementService;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcommons.miscellaneous.Constants;
import de.qaware.smartlabcommons.miscellaneous.ProfileNames;
import de.qaware.smartlabworkgroup.controller.WorkgroupManagementController;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

@Component
@Profile(ProfileNames.MONOLITH)
public class WorkgroupManagementServiceMonolith extends AbstractEntityManagementServiceMonolith<IWorkgroup> implements IWorkgroupManagementService {

    private final WorkgroupManagementController workgroupManagementController;

    public WorkgroupManagementServiceMonolith(WorkgroupManagementController workgroupManagementController) {
        super(workgroupManagementController);
        this.workgroupManagementController = workgroupManagementController;
    }

    @Override
    public Set<IMeeting> getMeetingsOfWorkgroup(String workgroupId) {
        return this.workgroupManagementController.getMeetingsOfWorkgroup(workgroupId).getBody();
    }

    @Override
    public IMeeting getCurrentMeeting(String workgroupId) {
        return this.workgroupManagementController.getCurrentMeeting(workgroupId).getBody();
    }

    @Override
    public void extendCurrentMeeting(String workgroupId, Duration extension) {
        this.workgroupManagementController.extendCurrentMeeting(workgroupId, extension.toMinutes());
    }
}
