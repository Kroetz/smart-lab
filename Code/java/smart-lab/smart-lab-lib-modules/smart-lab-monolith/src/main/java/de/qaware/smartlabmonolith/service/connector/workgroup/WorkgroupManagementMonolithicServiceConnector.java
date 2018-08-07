package de.qaware.smartlabmonolith.service.connector.workgroup;

import de.qaware.smartlab.api.service.connector.workgroup.IWorkgroupManagementService;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.data.meeting.IMeeting;
import de.qaware.smartlab.core.data.meeting.MeetingDto;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.data.workgroup.WorkgroupDto;
import de.qaware.smartlab.core.exception.EntityConflictException;
import de.qaware.smartlab.core.exception.EntityNotFoundException;
import de.qaware.smartlab.core.exception.MaximalDurationReachedException;
import de.qaware.smartlab.core.exception.UnknownErrorException;
import de.qaware.smartlab.core.miscellaneous.Property;
import de.qaware.smartlab.core.service.url.AbstractMonolithicBaseUrlGetter;
import de.qaware.smartlabmonolith.service.connector.generic.AbstractBasicEntityManagementMonolithicServiceConnector;
import de.qaware.smartlabworkgroupmanagement.service.controller.WorkgroupManagementController;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class WorkgroupManagementMonolithicServiceConnector extends AbstractBasicEntityManagementMonolithicServiceConnector<IWorkgroup, WorkgroupId, WorkgroupDto> implements IWorkgroupManagementService {

    private final WorkgroupManagementController workgroupManagementController;
    private final IDtoConverter<IMeeting, MeetingDto> meetingConverter;

    public WorkgroupManagementMonolithicServiceConnector(
            WorkgroupManagementController workgroupManagementController,
            IDtoConverter<IWorkgroup, WorkgroupDto> workgroupConverter,
            IDtoConverter<IMeeting, MeetingDto> meetingConverter) {
        super(workgroupManagementController, workgroupConverter);
        this.workgroupManagementController = workgroupManagementController;
        this.meetingConverter = meetingConverter;
    }

    @Override
    public Set<IMeeting> getMeetingsOfWorkgroup(WorkgroupId workgroupId) {
        ResponseEntity<Set<MeetingDto>> response = this.workgroupManagementController.getMeetingsOfWorkgroup(workgroupId.getIdValue());
        if(response.getStatusCode() == HttpStatus.OK) return requireNonNull(response.getBody()).stream().map(this.meetingConverter::toEntity).collect(toSet());
        // TODO: Meaningful exception message
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        throw new UnknownErrorException();
    }

    @Override
    public IMeeting getCurrentMeeting(WorkgroupId workgroupId) {
        ResponseEntity<MeetingDto> response = this.workgroupManagementController.getCurrentMeeting(workgroupId.getIdValue());
        if(response.getStatusCode() == HttpStatus.OK) return this.meetingConverter.toEntity(requireNonNull(response.getBody()));
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
