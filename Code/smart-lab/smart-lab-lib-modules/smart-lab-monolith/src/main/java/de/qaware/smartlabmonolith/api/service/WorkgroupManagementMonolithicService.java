package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabapi.service.connector.workgroup.IWorkgroupManagementService;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.exception.EntityConflictException;
import de.qaware.smartlabcore.exception.EntityNotFoundException;
import de.qaware.smartlabcore.exception.MaximalDurationReachedException;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabcore.url.AbstractMonolithicBaseUrlGetter;
import de.qaware.smartlabworkgroup.service.controller.WorkgroupManagementController;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class WorkgroupManagementMonolithicService extends AbstractBasicEntityManagementMonolithicService<IWorkgroup, WorkgroupId> implements IWorkgroupManagementService {

    private final WorkgroupManagementController workgroupManagementController;

    public WorkgroupManagementMonolithicService(WorkgroupManagementController workgroupManagementController) {
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

    @Component
    // TODO: String literal
    @Qualifier("workgroupManagementServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MONOLITH)
    public static class BaseUrlGetter extends AbstractMonolithicBaseUrlGetter {

        public BaseUrlGetter(WorkgroupManagementController.BaseUrlController baseUrlController) {
            super(baseUrlController);
        }
    }
}
