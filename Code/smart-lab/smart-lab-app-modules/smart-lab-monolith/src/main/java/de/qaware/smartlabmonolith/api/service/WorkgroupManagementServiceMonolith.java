package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabapi.service.workgroup.IWorkgroupManagementService;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabworkgroup.controller.WorkgroupManagementController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class WorkgroupManagementServiceMonolith extends AbstractEntityManagementServiceMonolith<IWorkgroup, WorkgroupId> implements IWorkgroupManagementService {

    private final WorkgroupManagementController workgroupManagementController;

    public WorkgroupManagementServiceMonolith(WorkgroupManagementController workgroupManagementController) {
        super(workgroupManagementController);
        this.workgroupManagementController = workgroupManagementController;
    }

    @Override
    public Set<IMeeting> getMeetingsOfWorkgroup(WorkgroupId workgroupId) {
        return this.workgroupManagementController.getMeetingsOfWorkgroup(workgroupId.getIdValue()).getBody();
    }

    @Override
    public IMeeting getCurrentMeeting(WorkgroupId workgroupId) {
        return this.workgroupManagementController.getCurrentMeeting(workgroupId.getIdValue()).getBody();
    }

    @Override
    public void extendCurrentMeeting(WorkgroupId workgroupId, Duration extension) {
        this.workgroupManagementController.extendCurrentMeeting(workgroupId.getIdValue(), extension.toMinutes());
    }
}
