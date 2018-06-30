package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabapi.service.workgroup.IWorkgroupManagementService;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.exception.EntityConflictException;
import de.qaware.smartlabcore.exception.EntityNotFoundException;
import de.qaware.smartlabcore.exception.MaximalDurationReachedException;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabworkgroup.controller.WorkgroupManagementController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class WorkgroupManagementServiceMonolith extends AbstractBasicEntityManagementServiceMonolith<IWorkgroup, WorkgroupId> implements IWorkgroupManagementService {

    private final WorkgroupManagementController workgroupManagementController;

    public WorkgroupManagementServiceMonolith(WorkgroupManagementController workgroupManagementController) {
        super(workgroupManagementController);
        this.workgroupManagementController = workgroupManagementController;
    }

    @Override
    public Set<IMeeting> getMeetingsOfWorkgroup(WorkgroupId workgroupId) {
        ResponseEntity<Set<IMeeting>> response = this.workgroupManagementController.getMeetingsOfWorkgroup(workgroupId.getIdValue());
        if(response.getStatusCode() == HttpStatus.OK) return response.getBody();
        // TODO: Meaningful exception message
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        throw new UnknownErrorException();
    }

    @Override
    public IMeeting getCurrentMeeting(WorkgroupId workgroupId) {
        ResponseEntity<IMeeting> response = this.workgroupManagementController.getCurrentMeeting(workgroupId.getIdValue());
        if(response.getStatusCode() == HttpStatus.OK) return response.getBody();
        // TODO: Meaningful exception message
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        throw new UnknownErrorException();
    }

    @Override
    public void extendCurrentMeeting(WorkgroupId workgroupId, Duration extension) {
        ResponseEntity<Void> response = this.workgroupManagementController.extendCurrentMeeting(workgroupId.getIdValue(), extension.toMinutes());
        if(response.getStatusCode() == HttpStatus.OK) return;
        // TODO: Meaningful exception messages
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        if(response.getStatusCode() == HttpStatus.CONFLICT) throw new EntityConflictException();
        if(response.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) throw new MaximalDurationReachedException();
        throw new UnknownErrorException();
    }
}
