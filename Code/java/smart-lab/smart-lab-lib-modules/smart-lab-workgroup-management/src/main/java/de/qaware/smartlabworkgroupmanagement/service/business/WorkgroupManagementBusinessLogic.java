package de.qaware.smartlabworkgroupmanagement.service.business;

import de.qaware.smartlab.api.service.connector.meeting.IMeetingManagementService;
import de.qaware.smartlab.core.data.meeting.IMeeting;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.result.ExtensionResult;
import de.qaware.smartlab.core.service.business.AbstractBasicEntityManagementBusinessLogic;
import de.qaware.smartlabworkgroupmanagement.service.repository.IWorkgroupManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class WorkgroupManagementBusinessLogic extends AbstractBasicEntityManagementBusinessLogic<IWorkgroup, WorkgroupId> implements IWorkgroupManagementBusinessLogic {

    private final IWorkgroupManagementRepository workgroupManagementRepository;
    private final IMeetingManagementService meetingManagementService;

    public WorkgroupManagementBusinessLogic(
            IWorkgroupManagementRepository workgroupManagementRepository,
            IMeetingManagementService meetingManagementService) {
        super(workgroupManagementRepository);
        this.workgroupManagementRepository = workgroupManagementRepository;
        this.meetingManagementService = meetingManagementService;
    }

    @Override
    public Optional<Set<IMeeting>> getMeetingsOfWorkgroup(WorkgroupId workgroupId) {
        return this.workgroupManagementRepository.findOne(workgroupId)
                .map(workgroup -> Optional.of(this.meetingManagementService.findAll(workgroup.getId())))
                .orElse(Optional.empty());
    }

    @Override
    public Optional<IMeeting> getCurrentMeeting(WorkgroupId workgroupId) {
        return this.workgroupManagementRepository.findOne(workgroupId)
                .map(workgroup -> Optional.of(this.meetingManagementService.findCurrent(workgroup.getId())))
                .orElse(Optional.empty());
    }

    @Override
    public ExtensionResult extendCurrentMeeting(WorkgroupId workgroupId, Duration extension) {
        return this.workgroupManagementRepository.findOne(workgroupId)
                .map(workgroup -> {
                    try {
                        return getCurrentMeeting(workgroup.getId())
                                .map(meeting -> {
                                    this.meetingManagementService.extendMeeting(meeting.getId(), extension);
                                    return ExtensionResult.SUCCESS;})
                                .orElse(ExtensionResult.NOT_FOUND);
                    }
                    catch(Exception e) {
                        return ExtensionResult.fromException(e);
                    }
                })
                .orElse(ExtensionResult.NOT_FOUND);
    }
}
